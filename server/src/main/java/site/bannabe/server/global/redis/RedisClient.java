package site.bannabe.server.global.redis;

public interface RedisClient<V> {

  String REFRESH_TOKEN_PREFIX = "RefreshToken:";
  String AUTH_CODE_PREFIX = "AuthCode:";

  void save(String key, V value);

  V findBy(String key);

  void deleteBy(String key);

  default String generateKey(String prefix, String key) {
    return prefix + key;
  }

}