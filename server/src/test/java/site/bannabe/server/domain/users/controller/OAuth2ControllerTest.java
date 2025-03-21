package site.bannabe.server.domain.users.controller;

import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.BDDMockito.given;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import site.bannabe.server.config.AbstractIntegrationTest;
import site.bannabe.server.domain.users.controller.request.OAuth2AuthorizationRequest;
import site.bannabe.server.domain.users.entity.ProviderType;
import site.bannabe.server.domain.users.repository.UserRepository;
import site.bannabe.server.global.api.OAuth2ApiClient;
import site.bannabe.server.global.security.auth.OAuth2ProviderRegistry;
import site.bannabe.server.global.security.auth.OAuth2ProviderRegistry.OAuth2ProviderType;
import site.bannabe.server.global.security.auth.OAuth2UserInfo;

class OAuth2ControllerTest extends AbstractIntegrationTest {

  @MockitoBean
  private OAuth2ApiClient oAuth2ApiClient;

  @Autowired
  private UserRepository userRepository;


  @AfterEach
  void tearDown() {
    userRepository.deleteAllInBatch();
  }

  @Test
  @DisplayName("소셜로그인 성공시 JWT를 응답한다.")
  void kakaoLoginSuccess() {
    //given
    String provider = "kakao";
    OAuth2ProviderType providerType = OAuth2ProviderRegistry.getType(provider);
    OAuth2AuthorizationRequest request = new OAuth2AuthorizationRequest("accessToken", "deviceToken");

    OAuth2UserInfo oAuth2UserInfo = new OAuth2UserInfo(ProviderType.KAKAO, "test@test.com", "test-image.png");
    given(oAuth2ApiClient.getOAuth2UserInfo(providerType, request.accessToken())).willReturn(oAuth2UserInfo);

    //when then
    RestAssured.given().log().all()
               .contentType(ContentType.JSON)
               .body(request)
               .when()
               .post("/v1/oauth2/login/{provider}", provider)
               .then().log().all()
               .statusCode(HttpStatus.OK.value())
               .body("data.accessToken", notNullValue())
               .body("data.refreshToken", notNullValue());
  }

}