package site.bannabe.server.domain.users.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.bannabe.server.domain.users.controller.request.UserRegisterRequest;
import site.bannabe.server.domain.users.entity.Users;
import site.bannabe.server.domain.users.repository.UserRepository;
import site.bannabe.server.global.exceptions.ErrorCode;
import site.bannabe.server.global.exceptions.auth.BannabeAuthenticationException;
import site.bannabe.server.global.exceptions.auth.ExpiredTokenException;
import site.bannabe.server.global.exceptions.auth.InvalidTokenException;
import site.bannabe.server.global.jwt.GenerateToken;
import site.bannabe.server.global.jwt.JwtService;
import site.bannabe.server.global.type.TokenResponse;
import site.bannabe.server.global.utils.EncryptUtils;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;

  private final EncryptUtils encryptUtils;

  private final JwtService jwtService;

  @Value("${bannabe.default-profile-image}")
  private String defaultProfileImage;

  @Transactional
  public void registerUser(UserRegisterRequest registerRequest) {
    Boolean isDuplicateEmail = userRepository.existsByEmail(registerRequest.email());
    if (isDuplicateEmail) {
      throw new IllegalArgumentException("이미 사용중인 이메일입니다.");
    }

    String encodedPassword = encryptUtils.encodePassword(registerRequest.password());

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

}