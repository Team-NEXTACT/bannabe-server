package site.bannabe.server.global.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.filter.OncePerRequestFilter;
import site.bannabe.server.global.exceptions.ErrorCode;
import site.bannabe.server.global.exceptions.auth.BannabeAuthenticationException;
import site.bannabe.server.global.exceptions.auth.ExpiredTokenException;
import site.bannabe.server.global.exceptions.auth.InvalidTokenException;
import site.bannabe.server.global.jwt.JwtService;
import site.bannabe.server.global.security.auth.EndPoints;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private static final String BEARER_PREFIX = "Bearer ";
  private final JwtService jwtService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

    if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
      filterChain.doFilter(request, response);
      return;
    }

    String accessToken = authHeader.substring(BEARER_PREFIX.length());

    try {
      jwtService.validateToken(accessToken);
      jwtService.saveAuthentication(accessToken);
      filterChain.doFilter(request, response);
    } catch (ExpiredTokenException e) {
      throw new BannabeAuthenticationException(ErrorCode.TOKEN_EXPIRED);
    } catch (InvalidTokenException e) {
      throw new BannabeAuthenticationException(ErrorCode.INVALID_TOKEN);
    }

  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    String pattern = request.getRequestURI();
    String method = request.getMethod();
    return EndPoints.PERMIT_ALL.stream()
                               .anyMatch(endpoint ->
                                   pattern.startsWith(endpoint.pattern()) && method.equals(endpoint.method().name()));
  }

}