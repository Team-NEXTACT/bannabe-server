package site.bannabe.server.domain.users.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.bannabe.server.domain.users.controller.request.UserChangePasswordRequest;
import site.bannabe.server.domain.users.entity.Users;
import site.bannabe.server.domain.users.repository.UserRepository;
import site.bannabe.server.global.exceptions.BannabeServiceException;
import site.bannabe.server.global.exceptions.ErrorCode;
import site.bannabe.server.global.utils.EncryptUtils;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
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

    String newPassword = encryptUtils.encodePassword(passwordRequest.newPassword());
    findUser.changePassword(newPassword);
    userRepository.save(findUser);
  }

  private boolean isNotEqualsNewPassword(String newPassword, String newPasswordConfirm) {
    return !newPassword.equals(newPasswordConfirm);
  }

  private boolean isNotMatchPassword(String rawPassword, String encodedPassword) {
    return !encryptUtils.isMatchPassword(rawPassword, encodedPassword);
  }

}