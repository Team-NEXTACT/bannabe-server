package site.bannabe.server.global.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import site.bannabe.server.global.exceptions.ErrorCode;
import site.bannabe.server.global.exceptions.auth.BannabeAuthenticationException;
import site.bannabe.server.global.type.ApiResponse;
import site.bannabe.server.global.type.ErrorResponse;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements org.springframework.security.web.AuthenticationEntryPoint {

  private final ObjectMapper objectMapper;

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
      throws IOException {
    ErrorCode errorCode;
    response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setCharacterEncoding(StandardCharsets.UTF_8.name());
    
    if (authException instanceof BannabeAuthenticationException bannabeAuthenticationException) {
      errorCode = bannabeAuthenticationException.getErrorCode();
      response.setStatus(errorCode.getHttpStatus().value());
    } else {
      errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
      log.error("Authentication EntryPoint Error!: {} \n Request URI: {}", authException.getMessage(), request.getRequestURI(),
          authException);
    }

    ErrorResponse errorResponse = ErrorResponse.of(errorCode);
    ApiResponse<ErrorResponse> failure = ApiResponse.failure(errorResponse);
    String body = objectMapper.writeValueAsString(failure);
    response.getWriter().write(body);
    response.getWriter().flush();
  }

}