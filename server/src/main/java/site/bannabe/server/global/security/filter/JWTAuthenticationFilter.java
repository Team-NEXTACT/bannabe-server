package site.bannabe.server.global.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.filter.OncePerRequestFilter;
import site.bannabe.server.global.jwt.JWTVerificationStatus;
import site.bannabe.server.global.jwt.JwtService;
import site.bannabe.server.global.security.auth.EndPoints;

@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {

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

    JWTVerificationStatus verificationStatus = jwtService.validateToken(accessToken);

    switch (verificationStatus) {
      case VALID:
        jwtService.saveAuthentication(accessToken);
        filterChain.doFilter(request, response);
        break;
      case EXPIRED:
        throw new RuntimeException("토큰이 만료되었습니다.");
      case INVALID:
        throw new RuntimeException("유효하지 않은 토큰입니다.");
      default:
        throw new RuntimeException("알 수 없는 오류가 발생했습니다.");
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