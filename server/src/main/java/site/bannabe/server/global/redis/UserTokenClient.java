package site.bannabe.server.global.redis;

import java.time.Duration;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import site.bannabe.server.global.exceptions.BannabeServiceException;
import site.bannabe.server.global.exceptions.ErrorCode;
import site.bannabe.server.global.type.UserTokens;

@Component
@RequiredArgsConstructor
public class UserTokenClient implements RedisHashClient<UserTokens> {

  private static final String USER_TOKEN_FORMAT = "UserToken:%s";
  private static final Long TTL = 7L;

  private final RedisTemplate<String, UserTokens> redis;

  @Override
  public void save(String key, UserTokens value) {
    key = String.format(USER_TOKEN_FORMAT, key);
    redis.opsForHash().put(key, value.getRefreshToken(), value.getDeviceToken());
    redis.expire(key, Duration.ofDays(TTL));
  }

  @Override
  public UserTokens findBy(String key, String hashKey) {
    key = String.format(USER_TOKEN_FORMAT, key);
    String deviceToken = (String) redis.opsForHash().get(key, hashKey);
    if (Objects.isNull(deviceToken)) {
      throw new BannabeServiceException(ErrorCode.REFRESH_TOKEN_NOT_FOUND);
    }
    return new UserTokens(hashKey, deviceToken);
  }

  @Override
  public void deleteBy(String key, String hashKey) {
    key = String.format(USER_TOKEN_FORMAT, key);
    redis.opsForHash().delete(key, hashKey);
  }

  @Override
  public void deleteAll(String key) {
    key = String.format(USER_TOKEN_FORMAT, key);
    redis.delete(key);
  }

}