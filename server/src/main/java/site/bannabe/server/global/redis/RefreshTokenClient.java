package site.bannabe.server.global.redis;

import java.time.Duration;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import site.bannabe.server.global.type.RefreshToken;

@Component
@RequiredArgsConstructor
public class RefreshTokenClient implements RedisClient<RefreshToken> {

  private static final long TTL = 7L;

  private final RedisTemplate<String, RefreshToken> redis;

  @Override
  public void save(String key, RefreshToken value) {
    key = REFRESH_TOKEN_PREFIX + key;
    redis.opsForValue().set(key, value, Duration.ofDays(TTL));
  }

  @Override
  public RefreshToken findBy(String key) {
    RefreshToken value = redis.opsForValue().get(REFRESH_TOKEN_PREFIX + key);
    return Optional.ofNullable(value).orElseThrow(RuntimeException::new);
  }

  @Override
  public void deleteBy(String key) {
    redis.delete(REFRESH_TOKEN_PREFIX + key);
  }

}