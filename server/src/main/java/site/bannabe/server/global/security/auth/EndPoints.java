package site.bannabe.server.global.security.auth;

import java.util.List;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpMethod;
import site.bannabe.server.global.type.EndPoint;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class EndPoints {

  public static final List<EndPoint> PERMIT_ALL = List.of(
      new EndPoint(HttpMethod.POST, "/auth/register"),
      new EndPoint(HttpMethod.POST, "/auth/login"),
      new EndPoint(HttpMethod.POST, "/auth/token/refresh"),
      new EndPoint(HttpMethod.GET, "/notices/**"),
      new EndPoint(HttpMethod.GET, "/events/**")
  );

}