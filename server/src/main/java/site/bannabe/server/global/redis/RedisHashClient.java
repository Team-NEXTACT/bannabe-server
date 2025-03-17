package site.bannabe.server.global.redis;

public interface RedisHashClient<T> {

  void save(String key, T value);

  T findBy(String key, String hashKey);

  void deleteBy(String key, String hashKey);

  void deleteAll(String key);

}