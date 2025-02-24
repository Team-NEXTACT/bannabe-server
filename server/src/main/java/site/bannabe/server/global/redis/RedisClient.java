package site.bannabe.server.global.redis;

public interface RedisClient<V> {

  String REFRESH_TOKEN_PREFIX = "RefreshToken:";

  void save(String key, V value);

  V findBy(String key);

  void deleteBy(String key);

}