package site.bannabe.server.domain.users.service;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.bannabe.server.domain.users.controller.request.UserChangeNicknameRequest;
import site.bannabe.server.domain.users.controller.request.UserChangePasswordRequest;
import site.bannabe.server.domain.users.controller.response.S3PreSignedUrlResponse;
import site.bannabe.server.domain.users.entity.Users;
import site.bannabe.server.domain.users.repository.UserRepository;
import site.bannabe.server.global.aws.S3Service;
import site.bannabe.server.global.exceptions.BannabeServiceException;
import site.bannabe.server.global.exceptions.ErrorCode;
import site.bannabe.server.global.utils.EncryptUtils;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final S3Service s3Service;
  private final EncryptUtils encryptUtils;

  @Transactional
  public void changePassword(String email, UserChangePasswordRequest passwordRequest) {
    if (isNotEqualsNewPassword(passwordRequest.newPassword(), passwordRequest.newPasswordConfirm())) {
      throw new BannabeServiceException(ErrorCode.NEW_PASSWORD_NOT_EQUALS);
    }

    Users findUser = userRepository.findByEmail(email).orElseThrow(() -> new BannabeServiceException(ErrorCode.USER_NOT_FOUND));

    if (isNotMatchPassword(passwordRequest.currentPassword(), findUser.getPassword())) {
      throw new BannabeServiceException(ErrorCode.PASSWORD_NOT_MATCH);
    }

    if (isReusedPassword(passwordRequest.newPassword(), findUser.getPassword())) {
      throw new BannabeServiceException(ErrorCode.REUSED_PASSWORD);
    }

    String newPassword = encryptUtils.encodePassword(passwordRequest.newPassword());
    findUser.changePassword(newPassword);
    // 비밀번호 변경 시 강제 로그아웃을 시킬까? 이에 대한 논의 필요.
  }

  @Transactional
  public void changeNickname(String email, UserChangeNicknameRequest nicknameRequest) {
    if (userRepository.existsByNickname(nicknameRequest.nickname())) {
      throw new BannabeServiceException(ErrorCode.NICKNAME_EXISTS);
    }

    userRepository.findByEmail(email).ifPresentOrElse(
        user -> user.changeNickname(nicknameRequest.nickname()),
        () -> {
          throw new BannabeServiceException(ErrorCode.USER_NOT_FOUND);
        }
    );
  }

  public S3PreSignedUrlResponse getPreSignedUrl(String extension) {
    String uuid = UUID.randomUUID().toString();
    String objectKey = uuid + "." + extension;
    String preSignedUrl = s3Service.getPreSignedUrl(objectKey);
    return new S3PreSignedUrlResponse(preSignedUrl);
  }

  private boolean isNotEqualsNewPassword(String newPassword, String newPasswordConfirm) {
    return !newPassword.equals(newPasswordConfirm);
  }

  private boolean isReusedPassword(String newPassword, String encodedPassword) {
    return encryptUtils.isMatchPassword(newPassword, encodedPassword);
  }

  private boolean isNotMatchPassword(String rawPassword, String encodedPassword) {
    return !encryptUtils.isMatchPassword(rawPassword, encodedPassword);
  }

}