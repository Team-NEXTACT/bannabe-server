package site.bannabe.server.config;

import com.redis.testcontainers.RedisContainer;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

public abstract class AbstractTestContainers {

  @ServiceConnection(name = "mysql")
  static final MySQLContainer<?> mysqlContainer;

  @ServiceConnection(name = "redis")
  static final GenericContainer<?> redisContainer;

  static {
    mysqlContainer = new MySQLContainer<>(DockerImageName.parse("mysql:8.0.34"))
        .withDatabaseName("test");

    redisContainer = new RedisContainer(DockerImageName.parse("redis:latest"))
        .withExposedPorts(6379)
        .withCommand("redis-server", "--requirepass", "testpassword");

    mysqlContainer.start();
    redisContainer.start();
  }

}