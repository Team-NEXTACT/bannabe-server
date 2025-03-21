package site.bannabe.server.config;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.web.server.LocalServerPort;

@IntegrationTest
public abstract class AbstractIntegrationTest extends AbstractTestContainers {

  @LocalServerPort
  private int port;

  @BeforeEach
  void setup() {
    RestAssured.port = port;
  }

}