package site.bannabe.server.global.redis;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import site.bannabe.server.global.exceptions.BannabeServiceException;
import site.bannabe.server.global.exceptions.ErrorCode;
import site.bannabe.server.global.type.AuthCode;

@Component
@RequiredArgsConstructor
public class AuthCodeClient implements RedisClient<AuthCode> {

  private static final long TTL = 10L;

  private final RedisTemplate<String, AuthCode> redis;

  @Override
  public void save(String key, AuthCode value) {
    key = generateKey(key);
    redis.opsForValue().set(key, value, Duration.ofMinutes(TTL));
  }

  @Override
  public AuthCode findBy(String key) {
    AuthCode value = redis.opsForValue().get(generateKey(key));
    return Optional.ofNullable(value).orElseThrow(() -> new BannabeServiceException(ErrorCode.AUTH_CODE_NOT_FOUND));
  }

  @Override
  public void deleteBy(String key) {
    redis.delete(generateKey(key));
  }

  public void updateAuthCodeWithTTL(String key, AuthCode authCode, long ttl) {
    key = generateKey(key);
    redis.opsForValue().set(key, authCode, Duration.ofMinutes(ttl));
  }

  private String generateKey(String key) {
    return AUTH_CODE_PREFIX + key;
  }

  public long getTTL(String key) {
    return redis.getExpire(generateKey(key), TimeUnit.MINUTES);
  }

}