package site.bannabe.server.global.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import site.bannabe.server.global.security.auth.EndPoints;
import site.bannabe.server.global.security.filter.JSONUsernamePasswordAuthenticationFilter;
import site.bannabe.server.global.security.filter.JwtAuthenticationFilter;
import site.bannabe.server.global.type.EndPoint;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final SecurityComponentProvider securityProvider;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .cors(AbstractHttpConfigurer::disable)
        .sessionManagement(sessionConfigurer ->
            sessionConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterAt(securityProvider.getJsonLoginFilter(), UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(securityProvider.getJwtAuthenticationFilter(), JSONUsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(securityProvider.getExceptionHandleFilter(), JwtAuthenticationFilter.class)
        .logout(logoutConfigurer ->
            logoutConfigurer.logoutRequestMatcher(new AntPathRequestMatcher("/v1/auth/logout", HttpMethod.POST.name()))
                            .addLogoutHandler(securityProvider.getLogoutHandler())
                            .logoutSuccessHandler(securityProvider.getLogoutSuccessHandler()))
        .exceptionHandling(handlerConfigurer ->
            handlerConfigurer.authenticationEntryPoint(securityProvider.getAuthenticationEntryPoint()));

    configurePermitAllEndPoints(http);

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