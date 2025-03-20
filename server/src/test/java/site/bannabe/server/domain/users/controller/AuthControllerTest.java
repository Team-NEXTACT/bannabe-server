package site.bannabe.server.domain.users.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;

import io.restassured.http.ContentType;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import site.bannabe.server.config.AbstractIntegrationTest;
import site.bannabe.server.domain.users.controller.request.AuthResetPasswordRequest;
import site.bannabe.server.domain.users.controller.request.AuthSendCodeRequest;
import site.bannabe.server.domain.users.controller.request.AuthVerifyCodeRequest;
import site.bannabe.server.domain.users.controller.request.TokenRefreshRequest;
import site.bannabe.server.domain.users.controller.request.UserRegisterRequest;
import site.bannabe.server.domain.users.entity.ProviderType;
import site.bannabe.server.domain.users.entity.Role;
import site.bannabe.server.domain.users.entity.Users;
import site.bannabe.server.domain.users.repository.UserRepository;
import site.bannabe.server.domain.users.service.AuthCodeService;
import site.bannabe.server.global.exceptions.ErrorCode;
import site.bannabe.server.global.jwt.GenerateToken;
import site.bannabe.server.global.jwt.JwtService;
import site.bannabe.server.global.security.filter.JSONUsernamePasswordAuthenticationFilter.LoginRequest;
import site.bannabe.server.global.utils.RandomCodeGenerator;

class AuthControllerTest extends AbstractIntegrationTest {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private RedisTemplate<String, ?> redisTemplate;
  @Autowired
  private JwtService jwtService;
  @Autowired
  private AuthCodeService authCodeService;
  @Autowired
  private BCryptPasswordEncoder passwordEncoder;
  @MockitoSpyBean
  private JavaMailSender mailSender;

  private String email;
  private Users user;

  @BeforeEach
  void setup() {
    email = "test@test.com";
    String password = "1234";
    String encodedPassword = passwordEncoder.encode(password);
    user = Users.builder().email(email).providerType(ProviderType.LOCAL).role(Role.USER).password(encodedPassword).build();
    userRepository.saveAndFlush(user);
    BDDMockito.willDoNothing().given(mailSender).send(any(MimeMessage.class));
  }

  @AfterEach
  void tearDown() {
    userRepository.deleteAllInBatch();
    redisTemplate.getConnectionFactory().getConnection().serverCommands().flushAll();
  }

  @Test
  @DisplayName("회원가입 성공시 200 응답")
  void registerUser() {
    //given
    String email = "newUser@test.com";
    String password = "1234";
    UserRegisterRequest request = new UserRegisterRequest(email, password);

    //when then
    given().log().all()
           .contentType(ContentType.JSON)
           .body(request)
           .when()
           .post("/v1/auth/register")
           .then().log().all()
           .assertThat()
           .statusCode(HttpStatus.OK.value());
  }

  @Test
  @DisplayName("회원가입 이메일 중복시 409 응답")
  void duplicateEmail() {
    //given
    String email = "test@test.com";
    String password = "1234";
    UserRegisterRequest request = new UserRegisterRequest(email, password);

    //when then
    given().log().all()
           .contentType(ContentType.JSON)
           .body(request)
           .when()
           .post("/v1/auth/register")
           .then().log().all()
           .assertThat()
           .statusCode(HttpStatus.CONFLICT.value());
  }

  @Test
  @DisplayName("로그인 성공시 JWT 발급")
  void loginSuccess() {
    //given
    LoginRequest request = new LoginRequest(email, "1234", "deviceToken");

    //when then
    given().log().all()
           .contentType(ContentType.JSON)
           .body(request)
           .when()
           .post("/v1/auth/login")
           .then().log().all()
           .assertThat()
           .statusCode(HttpStatus.OK.value())
           .body("data.accessToken", notNullValue())
           .body("data.refreshToken", notNullValue());
  }

  @Test
  @DisplayName("로그인 실패 시 400 응답")
  void loginFailure() {
    //given
    LoginRequest request = new LoginRequest(email, "5678", "deviceToken");

    //when then
    given().log().all()
           .contentType(ContentType.JSON)
           .body(request)
           .when()
           .post("/v1/auth/login")
           .then().log().all()
           .assertThat()
           .statusCode(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  @DisplayName("AccessToken 검증 실패시 401 응답")
  void invalidAccessToken() {
    //given
    String accessToken = "invalidAccessToken";

    //when then
    given().log().all()
           .contentType(ContentType.JSON)
           .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
           .when()
           .get("/v1/users/me")
           .then().log().all()
           .assertThat()
           .statusCode(HttpStatus.UNAUTHORIZED.value())
           .body("data.status", equalTo(ErrorCode.INVALID_TOKEN.name()));
  }

  @Test
  @DisplayName("JWT 재발급 성공 시 상태코드 200와 JWT 응답")
  void refreshJWT() {
    //given
    GenerateToken generateToken = jwtService.createJWT(user.getToken(), user.getRole().getRoleKey(), "deviceToken");
    TokenRefreshRequest request = new TokenRefreshRequest(generateToken.refreshToken());

    //when then
    given().log().all()
           .contentType(ContentType.JSON)
           .body(request)
           .when()
           .post("/v1/auth/token/refresh")
           .then().log().all()
           .assertThat()
           .statusCode(HttpStatus.OK.value());
  }

  @Test
  @DisplayName("RefreshToken 불일치 시 401 응답")
  void mismatchRefreshToken() {
    //given
    TokenRefreshRequest request = new TokenRefreshRequest(null);

    //when then
    given().log().all()
           .contentType(ContentType.JSON)
           .body(request)
           .when()
           .post("/v1/auth/token/refresh")
           .then()
           .assertThat()
           .statusCode(HttpStatus.UNAUTHORIZED.value());
  }

  @Test
  @DisplayName("인증코드 발송 성공시 200 응답")
  void sendAuthCode() {
    //given
    AuthSendCodeRequest request = new AuthSendCodeRequest(email);

    //when then
    given().log().all()
           .contentType(ContentType.JSON)
           .body(request)
           .when()
           .post("/v1/auth/send-code")
           .then().log().all()
           .assertThat()
           .statusCode(HttpStatus.OK.value());
  }

  @Test
  @DisplayName("인증코드 발송 전 회원 정보 미 존재시 404 응답")
  void notFoundUser() {
    //given
    String email = "wrong@test.com";
    AuthSendCodeRequest request = new AuthSendCodeRequest(email);

    //when
    given().log().all()
           .contentType(ContentType.JSON)
           .body(request)
           .when()
           .post("/v1/auth/send-code")
           .then().log().all()
           .assertThat()
           .statusCode(HttpStatus.NOT_FOUND.value());
  }

  @Test
  @DisplayName("인증코드 검증 성공 시 200 응답")
  void verifyAuthCode() {
    //given
    String authCode = RandomCodeGenerator.generateAuthCode();
    authCodeService.saveAuthCode(email, authCode);
    AuthVerifyCodeRequest request = new AuthVerifyCodeRequest(email, authCode);

    //when then
    given().log().all()
           .contentType(ContentType.JSON)
           .body(request)
           .when()
           .post("/v1/auth/verify-code")
           .then().log().all()
           .assertThat()
           .statusCode(HttpStatus.OK.value());
  }

  @Test
  @DisplayName("인증코드 검증 실패 시 409 응답")
  void invalidAuthCode() {
    //given
    String authCode = "wrongAuthCode";
    authCodeService.saveAuthCode(email, RandomCodeGenerator.generateAuthCode());
    AuthVerifyCodeRequest request = new AuthVerifyCodeRequest(email, authCode);

    //when then
    given().log().all()
           .contentType(ContentType.JSON)
           .body(request)
           .when()
           .post("/v1/auth/verify-code")
           .then().log().all()
           .assertThat()
           .statusCode(HttpStatus.CONFLICT.value());
  }

  @Test
  @DisplayName("비밀번호 재설정 성공 시 200 응답")
  void restPassword() {
    //given
    String newPassword = "5678";
    String newPasswordConfirm = "5678";
    String authCode = RandomCodeGenerator.generateAuthCode();
    authCodeService.saveAuthCode(email, authCode);
    authCodeService.markAuthCodeAsVerified(email);
    AuthResetPasswordRequest request = new AuthResetPasswordRequest(authCode, email, newPassword, newPasswordConfirm);

    //when then
    given().log().all()
           .contentType(ContentType.JSON)
           .body(request)
           .when()
           .put("/v1/auth/reset-password")
           .then().log().all()
           .assertThat()
           .statusCode(HttpStatus.OK.value());
  }

  @Test
  @DisplayName("비밀번호 설정 중 미인증 인증코드일 시 409 응답")
  void notVerifiedAuthCode() {
    //given
    String newPassword = "1234";
    String newPasswordConfirm = "1234";
    String authCode = RandomCodeGenerator.generateAuthCode();
    authCodeService.saveAuthCode(email, authCode);
    AuthResetPasswordRequest request = new AuthResetPasswordRequest(authCode, email, newPassword, newPasswordConfirm);

    //when then
    given().log().all()
           .contentType(ContentType.JSON)
           .body(request)
           .when()
           .put("/v1/auth/reset-password")
           .then().log().all()
           .assertThat()
           .statusCode(HttpStatus.CONFLICT.value());
  }

  @Test
  @DisplayName("비밀번호 검증 실패시 409 응답")
  void invalidPassword() {
    //given
    String newPassword = "1234";
    String newPasswordConfirm = "1234";
    String authCode = RandomCodeGenerator.generateAuthCode();
    authCodeService.saveAuthCode(email, authCode);
    authCodeService.markAuthCodeAsVerified(email);
    AuthResetPasswordRequest request = new AuthResetPasswordRequest(authCode, email, newPassword, newPasswordConfirm);

    //when then
    given().log().all()
           .contentType(ContentType.JSON)
           .body(request)
           .when()
           .put("/v1/auth/reset-password")
           .then().log().all()
           .assertThat()
           .statusCode(HttpStatus.CONFLICT.value());
  }

  @Test
  @DisplayName("로그아웃 성공시 200 응답")
  void logoutSuccess() {
    //given
    GenerateToken generateToken = jwtService.createJWT(user.getToken(), user.getRole().getRoleKey(), "deviceToken");

    TokenRefreshRequest request = new TokenRefreshRequest(generateToken.refreshToken());

    //when then
    given().log().all()
           .contentType(ContentType.JSON)
           .header(HttpHeaders.AUTHORIZATION, "Bearer " + generateToken.accessToken())
           .body(request)
           .when()
           .post("/v1/auth/logout")
           .then().log().all()
           .assertThat()
           .statusCode(HttpStatus.OK.value());
  }

  @Test
  @DisplayName("로그아웃 실패시 에러 발생")
  void logoutFailure() {
    //given
    TokenRefreshRequest request = new TokenRefreshRequest(null);

    //when then
    given().log().all()
           .contentType(ContentType.JSON)
           .body(request)
           .when()
           .post("/v1/auth/logout")
           .then().log().all()
           .assertThat()
           .statusCode(HttpStatus.UNAUTHORIZED.value())
           .body("data.status", equalTo(ErrorCode.TOKEN_NOT_FOUND.name()));
  }

}