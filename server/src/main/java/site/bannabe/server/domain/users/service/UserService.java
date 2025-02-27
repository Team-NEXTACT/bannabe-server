package site.bannabe.server.domain.users.service;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.bannabe.server.domain.users.controller.request.UserChangeNicknameRequest;
import site.bannabe.server.domain.users.controller.request.UserChangePasswordRequest;
import site.bannabe.server.domain.users.controller.request.UserChangeProfileImageRequest;
import site.bannabe.server.domain.users.controller.response.S3PreSignedUrlResponse;
import site.bannabe.server.domain.users.entity.Users;
import site.bannabe.server.domain.users.repository.UserRepository;
import site.bannabe.server.global.aws.S3Service;
import site.bannabe.server.global.exceptions.BannabeServiceException;
import site.bannabe.server.global.exceptions.ErrorCode;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final S3Service s3Service;
  private final PasswordService passwordService;

  @Value("${bannabe.default-profile-image}")
  private String defaultProfileImage;

  @Transactional
  public void changePassword(String email, UserChangePasswordRequest passwordRequest) {
    String newPassword = passwordRequest.newPassword();
    String newPasswordConfirm = passwordRequest.newPasswordConfirm();
    String currentPassword = passwordRequest.currentPassword();

    passwordService.validateNewPassword(newPassword, newPasswordConfirm);

    Users findUser = userRepository.findByEmail(email).orElseThrow(() -> new BannabeServiceException(ErrorCode.USER_NOT_FOUND));

    passwordService.validateCurrentPassword(currentPassword, findUser.getPassword());
    passwordService.validateReusedPassword(newPassword, findUser.getPassword());

    String encodedPassword = passwordService.encodePassword(passwordRequest.newPassword());
    findUser.changePassword(encodedPassword);
    // 비밀번호 변경 시 강제 로그아웃을 시킬까? 이에 대한 논의 필요.
  }

  @Transactional
  public void changeNickname(String email, UserChangeNicknameRequest nicknameRequest) {
    if (userRepository.existsByNickname(nicknameRequest.nickname())) {
      throw new BannabeServiceException(ErrorCode.DUPLICATE_NICKNAME);
    }

    userRepository.findByEmail(email).ifPresentOrElse(
        user -> user.changeNickname(nicknameRequest.nickname()),
        () -> {
          throw new BannabeServiceException(ErrorCode.USER_NOT_FOUND);
        }
    );
  }

  @Transactional
  public void changeProfileImage(String email, UserChangeProfileImageRequest changeProfileImageRequest) {
    Users user = userRepository.findByEmail(email).orElseThrow(() -> new BannabeServiceException(ErrorCode.USER_NOT_FOUND));
    String currentProfileImage = user.getProfileImage();

    String newProfileImage = changeProfileImageRequest.imageUrl();
    if (isNotDefaultProfileImage(currentProfileImage)) {
      s3Service.removeProfileImage(currentProfileImage);
    }

    user.changeProfileImage(newProfileImage);
  }

  public S3PreSignedUrlResponse getPreSignedUrl(String extension) {
    String uuid = UUID.randomUUID().toString();
    String objectKey = uuid + "." + extension;
    String preSignedUrl = s3Service.getPreSignedUrl(objectKey);
    return new S3PreSignedUrlResponse(preSignedUrl);
  }

  private boolean isNotDefaultProfileImage(String profileImage) {
    return !profileImage.equals(defaultProfileImage);
  }

}