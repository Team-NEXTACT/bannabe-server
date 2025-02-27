package site.bannabe.server.global.jwt;

import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import site.bannabe.server.global.exceptions.ErrorCode;
import site.bannabe.server.global.exceptions.auth.BannabeAuthenticationException;
import site.bannabe.server.global.jwt.JwtProvider.TokenClaims;
import site.bannabe.server.global.security.auth.PrincipalDetails;
import site.bannabe.server.global.type.RefreshToken;

@Service
@RequiredArgsConstructor
public class JwtService {

  private final JwtProvider jwtProvider;

  private final RefreshTokenService refreshTokenService;

  public GenerateToken createJWT(String email, String authorities) {
    GenerateToken generateToken = jwtProvider.generateToken(email, authorities);
    refreshTokenService.save(generateToken.refreshToken());
    return generateToken;
  }

  public GenerateToken refreshJWT(String requestRefreshToken) {
    TokenClaims tokenClaims = jwtProvider.getTokenClaims(requestRefreshToken);
    RefreshToken refreshToken = refreshTokenService.findRefreshTokenBy(tokenClaims.email());
    if (!requestRefreshToken.equals(refreshToken.getRefreshToken())) {
      throw new BannabeAuthenticationException(ErrorCode.INVALID_REFRESH_TOKEN);
    }
    GenerateToken newToken = jwtProvider.generateToken(tokenClaims.email(), tokenClaims.authorities());
    refreshTokenService.updateRefreshToken(tokenClaims.email(), newToken.refreshToken());
    return newToken;
  }

  public void validateToken(String token) {
    jwtProvider.verifyToken(token);
  }

  public void saveAuthentication(String token) {
    String email = jwtProvider.getEmail(token);
    String role = jwtProvider.getAuthorities(token);
    List<SimpleGrantedAuthority> authorities = Arrays.stream(role.split(",")).map(SimpleGrantedAuthority::new).toList();
    PrincipalDetails user = PrincipalDetails.create(email, authorities);
    Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);
    SecurityContextHolder.getContext().setAuthentication(authentication);
  }

  public void removeRefreshToken(String accessToken) {
    String email = jwtProvider.getEmail(accessToken);
    refreshTokenService.removeRefreshToken(email);
  }

}