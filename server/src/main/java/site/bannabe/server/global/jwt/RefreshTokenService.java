package site.bannabe.server.global.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.bannabe.server.global.redis.RefreshTokenClient;
import site.bannabe.server.global.type.RefreshToken;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

  private final RefreshTokenClient refreshTokenClient;

  public void save(RefreshToken refreshToken) {
    refreshTokenClient.save(refreshToken.getEmail(), refreshToken);
  }

  public void updateRefreshToken(String email, RefreshToken refreshToken) {
    refreshTokenClient.save(email, refreshToken);
  }

  public RefreshToken findRefreshTokenBy(String email) {
    return refreshTokenClient.findBy(email);
  }

  public void removeRefreshToken(String email) {
    refreshTokenClient.deleteBy(email);
  }

}