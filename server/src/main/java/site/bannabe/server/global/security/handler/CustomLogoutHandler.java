package site.bannabe.server.global.security.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import site.bannabe.server.global.jwt.JwtService;

@Component
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {

  private static final String PREFIX = "Bearer ";
  private final JwtService jwtService;

  @Override
  public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
    String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (authHeader == null || !authHeader.startsWith(PREFIX)) {
      return;
    }
    String accessToken = authHeader.substring(PREFIX.length());
    jwtService.removeRefreshToken(accessToken);
  }

}