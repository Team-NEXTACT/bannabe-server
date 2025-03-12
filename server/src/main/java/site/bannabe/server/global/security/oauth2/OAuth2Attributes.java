// OAuth2 속성 매핑(구글 응답을 우리 시스템의 사용자 정보로 매핑)
package site.bannabe.server.global.security.oauth2;

import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import site.bannabe.server.domain.users.entity.ProviderType;
import site.bannabe.server.domain.users.entity.Role;
import site.bannabe.server.domain.users.entity.Users;

@Getter
@Builder
public class OAuth2Attributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;
    private ProviderType providerType;

    public static OAuth2Attributes of(ProviderType providerType, Map<String, Object> attributes) {
        switch (providerType) {
            case GOOGLE -> {
                return ofGoogle(attributes);
            }
            default -> throw new IllegalArgumentException("Invalid Provider Type");
        }
    }

    private static OAuth2Attributes ofGoogle(Map<String, Object> attributes) {
        return OAuth2Attributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey("sub")
                .providerType(ProviderType.GOOGLE)
                .build();
    }

    public Users toEntity() {
        return Users.builder()
                .nickname(name)
                .email(email)
                .profileImage(picture)
                .role(Role.USER)
                .providerType(providerType)
                .build();
    }
} 