package site.bannabe.server.domain.users.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.bannabe.server.domain.rentals.entity.RentalHistory;
import site.bannabe.server.domain.rentals.repository.RentalHistoryRepository;
import site.bannabe.server.domain.users.controller.request.UserChangeNicknameRequest;
import site.bannabe.server.domain.users.controller.request.UserChangePasswordRequest;
import site.bannabe.server.domain.users.controller.request.UserChangeProfileImageRequest;
import site.bannabe.server.domain.users.controller.response.S3PreSignedUrlResponse;
import site.bannabe.server.domain.users.controller.response.UserBookmarkStationsResponse;
import site.bannabe.server.domain.users.controller.response.UserBookmarkStationsResponse.BookmarkStationResponse;
import site.bannabe.server.domain.users.controller.response.UserGetActiveRentalResponse.RentalHistoryResponse;
import site.bannabe.server.domain.users.entity.Users;
import site.bannabe.server.domain.users.repository.BookmarkStationRepository;
import site.bannabe.server.domain.users.repository.UserRepository;
import site.bannabe.server.global.aws.S3Service;
import site.bannabe.server.global.exceptions.BannabeServiceException;
import site.bannabe.server.global.exceptions.ErrorCode;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final RentalHistoryRepository rentalHistoryRepository;
  private final BookmarkStationRepository bookmarkStationRepository;
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
    if (user.isNotDefaultProfileImage(defaultProfileImage)) {
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

  @Transactional
  public List<RentalHistoryResponse> getActiveRentalHistory(String email) {
    List<RentalHistory> rentalHistories = rentalHistoryRepository.findActiveRentalsBy(email);
    LocalDateTime now = LocalDateTime.now();
    rentalHistories.forEach(rentalHistory -> rentalHistory.validateOverdue(now));
    return rentalHistories.stream().map(RentalHistoryResponse::of).toList();
  }

  @Transactional
  public Page<RentalHistoryResponse> getRentalHistory(String email, Pageable pageable) {
    Page<RentalHistory> rentalHistories = rentalHistoryRepository.findAllRentalsBy(email, pageable);
    LocalDateTime now = LocalDateTime.now();
    rentalHistories.forEach(rentalHistory -> rentalHistory.validateOverdue(now));
    return rentalHistories.map(RentalHistoryResponse::of);
  }

  @Transactional(readOnly = true)
  public UserBookmarkStationsResponse getBookmarkStations(String email) {
    List<BookmarkStationResponse> bookmarkStations = bookmarkStationRepository.findBookmarkStationsBy(email);
    return new UserBookmarkStationsResponse(bookmarkStations);
  }

  @Transactional
  public void removeBookmarkStation(String email, Long bookmarkId) {
    boolean isUserBookmark = bookmarkStationRepository.existsBookmarkByEmail(email, bookmarkId);
    if (!isUserBookmark) {
      throw new BannabeServiceException(ErrorCode.BOOKMARK_NOT_EXIST);
    }
    bookmarkStationRepository.deleteBookmarkById(bookmarkId);
  }

}