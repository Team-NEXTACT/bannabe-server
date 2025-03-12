// OAuth2 사용자 서비스(구글에서 받아온 사용자 정보를 처리)
package site.bannabe.server.global.security.oauth2;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import site.bannabe.server.domain.users.entity.ProviderType;
import site.bannabe.server.domain.users.entity.Users;
import site.bannabe.server.domain.users.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);
        
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        ProviderType providerType = ProviderType.valueOf(registrationId.toUpperCase());
        
        OAuth2Attributes attributes = OAuth2Attributes.of(providerType, oauth2User.getAttributes());
        
        Users user = saveOrUpdate(attributes);
        
        return new CustomOAuth2User(user, oauth2User.getAttributes());
    }

    private Users saveOrUpdate(OAuth2Attributes attributes) {
        Users user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.updateOAuth2User(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());
                
        return userRepository.save(user);
    }
} 