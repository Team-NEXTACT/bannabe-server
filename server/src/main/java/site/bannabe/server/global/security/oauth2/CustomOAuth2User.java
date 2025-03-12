// 커스텀 OAuth2 사용자(Spring Security의 OAuth2User 구현)
package site.bannabe.server.global.security.oauth2;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import site.bannabe.server.domain.users.entity.Users;

public class CustomOAuth2User implements OAuth2User {
    private final Users user;
    private final Map<String, Object> attributes;

    public CustomOAuth2User(Users user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRole().getRoleKey().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getName() {
        return user.getEmail();
    }

    public String getEmail() {
        return user.getEmail();
    }
} 