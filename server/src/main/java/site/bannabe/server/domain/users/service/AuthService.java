package site.bannabe.server.domain.users.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.bannabe.server.domain.users.controller.request.AuthResetPasswordRequest;
import site.bannabe.server.domain.users.controller.request.UserRegisterRequest;
import site.bannabe.server.domain.users.entity.Users;
import site.bannabe.server.domain.users.repository.UserRepository;
import site.bannabe.server.global.exceptions.BannabeServiceException;
import site.bannabe.server.global.exceptions.ErrorCode;
import site.bannabe.server.global.exceptions.auth.BannabeAuthenticationException;
import site.bannabe.server.global.exceptions.auth.ExpiredTokenException;
import site.bannabe.server.global.exceptions.auth.InvalidTokenException;
import site.bannabe.server.global.jwt.GenerateToken;
import site.bannabe.server.global.jwt.JwtService;
import site.bannabe.server.global.mail.MailService;
import site.bannabe.server.global.type.AuthCode;
import site.bannabe.server.global.type.TokenResponse;
import site.bannabe.server.global.utils.RandomCodeGenerator;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;
  private final JwtService jwtService;
  private final MailService mailService;
  private final AuthCodeService authCodeService;
  private final PasswordService passwordService;

  @Value("${bannabe.default-profile-image}")
  private String defaultProfileImage;

  @Transactional
  public void registerUser(UserRegisterRequest registerRequest) {
    Boolean isDuplicateEmail = userRepository.existsByEmail(registerRequest.email());
    if (isDuplicateEmail) {
      throw new IllegalArgumentException("이미 사용중인 이메일입니다.");
    }

    String encodedPassword = passwordService.encodePassword(registerRequest.password());

    Users user = Users.createUser(registerRequest.email(), encodedPassword, defaultProfileImage);

    userRepository.save(user);
  }

  public TokenResponse refreshToken(String refreshToken) {
    try {
      jwtService.validateToken(refreshToken);
    } catch (ExpiredTokenException e) {
      throw new BannabeAuthenticationException(ErrorCode.TOKEN_EXPIRED);
    } catch (InvalidTokenException e) {
      throw new BannabeAuthenticationException(ErrorCode.INVALID_TOKEN);
    }
    GenerateToken generateToken = jwtService.refreshJWT(refreshToken);
    return TokenResponse.create(generateToken);
  }

  @Transactional(readOnly = true)
  public void sendAuthCode(String email) {
    boolean isExistEmail = userRepository.existsByEmail(email);
    if (!isExistEmail) {
      throw new BannabeServiceException(ErrorCode.USER_NOT_FOUND);
    }
    String authCode = RandomCodeGenerator.generateAuthCode();

    authCodeService.saveAuthCode(email, authCode);

    mailService.sendAuthCodeMail(email, authCode);
  }

  public void verifyAuthCode(String email, String authCode) {
    AuthCode savedAuthCode = authCodeService.findAuthCode(email);

    if (savedAuthCode.isVerified()) {
      throw new BannabeServiceException(ErrorCode.ALREADY_VERIFIED);
    }

    validateAuthCode(authCode, savedAuthCode.getAuthCode());

    authCodeService.markAuthCodeAsVerified(email);
  }

  @Transactional
  public void resetPassword(AuthResetPasswordRequest resetPasswordRequest) {
    String email = resetPasswordRequest.email();
    String newPassword = resetPasswordRequest.newPassword();
    String newPasswordConfirm = resetPasswordRequest.newPasswordConfirm();

    AuthCode savedAuthCode = authCodeService.findAuthCode(email);
    validateAuthCode(resetPasswordRequest.authCode(), savedAuthCode.getAuthCode());

    passwordService.validateNewPassword(newPassword, newPasswordConfirm);

    Users findUser = userRepository.findByEmail(email).orElseThrow(() -> new BannabeServiceException(ErrorCode.USER_NOT_FOUND));

    passwordService.validateReusedPassword(newPassword, findUser.getPassword());

    String encodePassword = passwordService.encodePassword(newPassword);
    findUser.changePassword(encodePassword);
    authCodeService.removeAuthCode(email);
  }

  private void validateAuthCode(String authCode, String savedAuthCode) {
    if (!authCode.equals(savedAuthCode)) {
      throw new BannabeServiceException(ErrorCode.INVALID_AUTH_CODE);
    }
  }

}