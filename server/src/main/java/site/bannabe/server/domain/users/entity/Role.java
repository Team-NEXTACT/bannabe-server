package site.bannabe.server.domain.users.entity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Role {

  ADMIN("ROLE_ADMIN"),
  MANAGER("ROLE_MANAGER"),
  USER("ROLE_USER");

  private final String key;

  public String getRoleKey() {
    return key;
  }

}