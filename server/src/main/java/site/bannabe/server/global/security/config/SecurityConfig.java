package site.bannabe.server.global.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import site.bannabe.server.global.security.auth.EndPoint;
import site.bannabe.server.global.security.filter.JSONUsernamePasswordAuthenticationFilter;
import site.bannabe.server.global.security.filter.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final SecurityComponentProvider securityProvider;
  private final CustomOAuth2UserService customOAuth2UserService;
  private final OAuth2SuccessHandler oAuth2SuccessHandler;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .cors(AbstractHttpConfigurer::disable)
        .sessionManagement(sessionConfigurer ->
            sessionConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterAt(securityProvider.getJsonLoginFilter(), UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(securityProvider.getJwtAuthenticationFilter(), JSONUsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(securityProvider.getExceptionHandleFilter(), JwtAuthenticationFilter.class)
        .oauth2Login(oauth2 -> oauth2
            .userInfoEndpoint(userInfo -> userInfo
                .userService(customOAuth2UserService))
            .successHandler(oAuth2SuccessHandler))
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
      for (EndPoint endPoint : EndPoint.PERMIT_ALL) {
        authorizeRequests.requestMatchers(endPoint.method(), endPoint.pattern()).permitAll();
      }
      authorizeRequests.requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                       .permitAll()
                       .anyRequest()
                       .authenticated();
    });
  }

}