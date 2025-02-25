package site.bannabe.server.domain.users.entity;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.bannabe.server.global.converter.ProviderTypeConverter;
import site.bannabe.server.global.converter.RoleConverter;
import site.bannabe.server.global.type.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Users extends BaseEntity {

  private String email;

  private String password;

  private String profileImage;

  private String nickname;

  @Convert(converter = RoleConverter.class)
  private Role role;

  @Convert(converter = ProviderTypeConverter.class)
  private ProviderType providerType;

}