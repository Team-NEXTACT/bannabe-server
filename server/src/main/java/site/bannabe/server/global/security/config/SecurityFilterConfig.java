package site.bannabe.server.global.security.config;

import lombok.Getter;
import org.springframework.context.annotation.Configuration;
import site.bannabe.server.global.jwt.JwtService;
import site.bannabe.server.global.security.filter.JSONUsernamePasswordAuthenticationFilter;
import site.bannabe.server.global.security.filter.JWTAuthenticationFilter;

@Getter
@Configuration
public class SecurityFilterConfig {

  private final JSONUsernamePasswordAuthenticationFilter jsonLoginFilter;

  private final JWTAuthenticationFilter jwtAuthenticationFilter;

  public SecurityFilterConfig(JSONUsernamePasswordAuthenticationFilter jsonLoginFilter, JwtService jwtService) {
    this.jsonLoginFilter = jsonLoginFilter;
    this.jwtAuthenticationFilter = new JWTAuthenticationFilter(jwtService);
  }

}