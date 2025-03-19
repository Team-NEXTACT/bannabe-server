package site.bannabe.server.domain.users.controller.response;

import site.bannabe.server.domain.users.entity.Users;

public record UserGetSimpleResponse(
    String email,
    String nickname,
    String profileImage
) {

  public static UserGetSimpleResponse of(Users user) {
    return new UserGetSimpleResponse(
        user.getEmail(),
        user.getNickname(),
        user.getProfileImage()
    );
  }

}