package site.bannabe.server.global.security.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import site.bannabe.server.global.exceptions.ErrorCode;
import site.bannabe.server.global.exceptions.auth.BannabeAuthenticationException;
import site.bannabe.server.global.type.ApiResponse;
import site.bannabe.server.global.type.ErrorResponse;
import site.bannabe.server.global.utils.JsonUtils;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginFailureHandler implements AuthenticationFailureHandler {

  private final JsonUtils jsonUtils;

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
      throws IOException {
    ErrorCode errorCode;

    if (exception instanceof BannabeAuthenticationException authException) {
      errorCode = authException.getErrorCode();
    } else if (exception instanceof BadCredentialsException) {
      errorCode = ErrorCode.INVALID_CREDENTIALS;
    } else if (exception instanceof UsernameNotFoundException) {
      errorCode = ErrorCode.USER_NOT_FOUND;
    } else {
      errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
      log.error("AuthenticationFailure Error! ReqeustURI: {}", request.getRequestURI(), exception);
    }
    response.setStatus(HttpStatus.BAD_REQUEST.value());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setCharacterEncoding(StandardCharsets.UTF_8.name());

    ApiResponse<ErrorResponse> apiResponse = ApiResponse.failure(ErrorResponse.of(errorCode));
    String body = jsonUtils.serializedObjectToJson(apiResponse);
    response.getWriter().write(body);
    response.getWriter().flush();
  }

}