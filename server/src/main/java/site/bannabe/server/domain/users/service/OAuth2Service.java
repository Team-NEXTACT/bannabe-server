package site.bannabe.server.domain.users.service;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;
import site.bannabe.server.domain.users.entity.Users;
import site.bannabe.server.domain.users.repository.UserRepository;
import site.bannabe.server.global.jwt.GenerateToken;
import site.bannabe.server.global.jwt.JwtService;
import site.bannabe.server.global.security.auth.OAuth2ProviderRegistry;
import site.bannabe.server.global.security.auth.OAuth2ProviderRegistry.OAuth2ProviderType;
import site.bannabe.server.global.security.auth.OAuth2UserInfo;
import site.bannabe.server.global.type.TokenResponse;

@Service
@RequiredArgsConstructor
public class OAuth2Service {

  private final UserRepository userRepository;
  private final RestClient restClient;
  private final JwtService jwtService;

  @Transactional
  public TokenResponse processOAuth2Login(String provider, String accessToken) {
    OAuth2ProviderType oAuth2ProviderType = OAuth2ProviderRegistry.getType(provider);
    Map<String, Object> attributes = restClient.get().uri(oAuth2ProviderType.USER_INFO_URL)
                                               .headers(headers -> {
                                                 headers.setBearerAuth(accessToken);
                                                 headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                                               })
                                               .retrieve()
                                               .body(new ParameterizedTypeReference<>() {
                                               });
    OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfo.from(oAuth2ProviderType, attributes);

    return registerOrAuthenticateUser(oAuth2UserInfo);
  }

  private TokenResponse registerOrAuthenticateUser(OAuth2UserInfo oAuth2UserInfo) {
    Users user = userRepository.findByEmail(oAuth2UserInfo.email()).orElseGet(() -> {
      Users newUser = oAuth2UserInfo.toUser();
      return userRepository.save(newUser);
    });

    GenerateToken token = jwtService.createJWT(user.getEmail(), user.getRole().getRoleKey());
    return TokenResponse.create(token);
  }

}