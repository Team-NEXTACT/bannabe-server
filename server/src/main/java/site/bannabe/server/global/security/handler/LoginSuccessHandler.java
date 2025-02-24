package site.bannabe.server.global.security.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import site.bannabe.server.global.jwt.GenerateToken;
import site.bannabe.server.global.jwt.JwtService;
import site.bannabe.server.global.security.auth.PrincipalDetails;
import site.bannabe.server.global.type.ApiResponse;
import site.bannabe.server.global.utils.JsonUtils;

@Component
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

  private final JwtService jwtService;
  private final JsonUtils jsonUtils;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException {
    GenerateToken tokens = generateToken(authentication);
    ApiResponse<TokenResponse> apiResponse = ApiResponse.success("로그인 성공", TokenResponse.create(tokens));
    writeResponse(response, apiResponse);
    clearLoginUsedSession(request);
  }

  private GenerateToken generateToken(Authentication authentication) {
    PrincipalDetails user = (PrincipalDetails) authentication.getPrincipal();
    String email = user.getUsername();
    String authorities = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
    return jwtService.createJWT(email, authorities);
  }

  private void writeResponse(HttpServletResponse response, ApiResponse<TokenResponse> apiResponse) throws IOException {
    String body = jsonUtils.serializedObjectToJson(apiResponse);
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setCharacterEncoding(StandardCharsets.UTF_8.name());
    response.getWriter().write(body);
    response.getWriter().flush();
  }

  private void clearLoginUsedSession(HttpServletRequest request) {
    SecurityContextHolder.clearContext();
    HttpSession session = request.getSession(false);
    if (Objects.nonNull(session)) {
      session.invalidate();
    }
  }

  private record TokenResponse(String accessToken, String refreshToken) {

    public static TokenResponse create(GenerateToken generateToken) {
      return new TokenResponse(generateToken.accessToken(), generateToken.refreshToken().getRefreshToken());
    }

  }

}