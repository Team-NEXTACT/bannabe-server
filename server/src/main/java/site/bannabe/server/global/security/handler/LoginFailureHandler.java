package site.bannabe.server.global.security.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import site.bannabe.server.global.type.ApiResponse;
import site.bannabe.server.global.utils.JsonUtils;

@Component
@RequiredArgsConstructor
public class LoginFailureHandler implements AuthenticationFailureHandler {

  private final JsonUtils jsonUtils;

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
      throws IOException {
    response.setStatus(HttpStatus.BAD_REQUEST.value());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setCharacterEncoding(StandardCharsets.UTF_8.name());

    ApiResponse<?> apiResponse = ApiResponse.failure("로그인에 실패했습니다. 이메일 또는 비밀번호를 확인해주세요.");
    String body = jsonUtils.serializedObjectToJson(apiResponse);
    response.getWriter().write(body);
    response.getWriter().flush();
  }

}