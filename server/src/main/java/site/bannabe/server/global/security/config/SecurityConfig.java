package site.bannabe.server.global.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import site.bannabe.server.global.security.auth.EndPoints;
import site.bannabe.server.global.security.filter.JSONUsernamePasswordAuthenticationFilter;
import site.bannabe.server.global.type.EndPoint;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final SecurityFilterConfig securityFilterConfig;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .cors(AbstractHttpConfigurer::disable);

    configurePermitAllEndPoints(http);

    http.exceptionHandling(handlerConfigurer ->
        handlerConfigurer.authenticationEntryPoint(securityFilterConfig.getAuthenticationEntryPoint())
    );

    http.addFilterAt(securityFilterConfig.getJsonLoginFilter(), UsernamePasswordAuthenticationFilter.class);
    http.addFilterBefore(securityFilterConfig.getJwtAuthenticationFilter(), JSONUsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  private void configurePermitAllEndPoints(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(authorizeRequests -> {
      for (EndPoint endPoint : EndPoints.PERMIT_ALL) {
        authorizeRequests.requestMatchers(endPoint.method(), endPoint.pattern()).permitAll();
      }
      authorizeRequests.anyRequest().authenticated();
    });
  }

}