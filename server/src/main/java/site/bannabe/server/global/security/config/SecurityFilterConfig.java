package site.bannabe.server.global.security.config;

import lombok.Getter;
import org.springframework.context.annotation.Configuration;
import site.bannabe.server.global.jwt.JwtService;
import site.bannabe.server.global.security.filter.JSONUsernamePasswordAuthenticationFilter;
import site.bannabe.server.global.security.filter.JwtAuthenticationFilter;
import site.bannabe.server.global.security.handler.CustomAuthenticationEntryPoint;

@Getter
@Configuration
public class SecurityFilterConfig {

  private final JSONUsernamePasswordAuthenticationFilter jsonLoginFilter;

  private final CustomAuthenticationEntryPoint authenticationEntryPoint;

  private final JwtAuthenticationFilter jwtAuthenticationFilter;

  public SecurityFilterConfig(JSONUsernamePasswordAuthenticationFilter jsonLoginFilter, JwtService jwtService,
      CustomAuthenticationEntryPoint authenticationEntryPoint) {
    this.jsonLoginFilter = jsonLoginFilter;
    this.authenticationEntryPoint = authenticationEntryPoint;
    this.jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtService);
  }

}