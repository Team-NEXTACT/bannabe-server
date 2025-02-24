package site.bannabe.server.global.security.filter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import site.bannabe.server.global.security.handler.LoginFailureHandler;
import site.bannabe.server.global.security.handler.LoginSuccessHandler;
import site.bannabe.server.global.utils.JsonUtils;

@Component
public class JSONUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

  private static final String LOGIN_URL = "/auth/login";

  private static final AntPathRequestMatcher LOGIN_REQUEST_MATCHER = new AntPathRequestMatcher(LOGIN_URL, HttpMethod.POST.name());

  private final JsonUtils jsonUtils;

  protected JSONUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager, JsonUtils jsonUtils,
      LoginSuccessHandler loginSuccessHandler, LoginFailureHandler loginFailureHandler) {
    super(LOGIN_REQUEST_MATCHER, authenticationManager);
    setAuthenticationSuccessHandler(loginSuccessHandler);
    setAuthenticationFailureHandler(loginFailureHandler);
    this.jsonUtils = jsonUtils;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
      throws AuthenticationException, IOException {

    String contentType = request.getContentType();
    validateContentType(contentType);

    LoginRequest loginRequest = jsonUtils.deserializedJsonToObject(request.getInputStream(), LoginRequest.class);
    validateLoginRequest(loginRequest);

    Authentication authentication = createAuthentication(loginRequest);

    return super.getAuthenticationManager().authenticate(authentication);
  }

  private void validateContentType(final String requestContentType) {
    if (Objects.isNull(requestContentType) || !requestContentType.contains(MediaType.APPLICATION_JSON_VALUE)) {
      // 추후 Exception Custom 예정
      throw new RuntimeException("지원하지 않는 컨텐츠 타입입니다.");
    }
  }

  private void validateLoginRequest(LoginRequest loginRequest) {
    if (!StringUtils.hasText(loginRequest.email()) || !StringUtils.hasText(loginRequest.password())) {
      throw new RuntimeException("이메일 또는 비밀번호를 입력해주세요.");
    }
  }

  private Authentication createAuthentication(LoginRequest loginRequest) {
    String email = loginRequest.email();
    String password = loginRequest.password();
    return new UsernamePasswordAuthenticationToken(email, password);
  }


  private record LoginRequest(
      String email,
      String password
  ) {

  }

}