package site.bannabe.server.domain.users.entity;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.bannabe.server.global.converter.ProviderTypeConverter;
import site.bannabe.server.global.converter.RoleConverter;
import site.bannabe.server.global.type.BaseEntity;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Users extends BaseEntity {

  private String email;

  private String password;

  private String profileImage;

  private String nickname;

  @Default
  @Convert(converter = RoleConverter.class)
  private Role role = Role.USER;

  @Default
  @Convert(converter = ProviderTypeConverter.class)
  private ProviderType providerType = ProviderType.LOCAL;

  private Users(String email, String password, String profileImage, String nickname, Role role, ProviderType providerType) {
    this.email = email;
    this.password = password;
    this.profileImage = profileImage;
    this.nickname = nickname;
    this.role = role;
    this.providerType = providerType;
  }

  public static Users createUser(String email, String password) {
    return Users.builder().email(email).password(password).build();
  }

}