package site.bannabe.server.global.jwt;

import site.bannabe.server.global.type.RefreshToken;

public record GenerateToken(
    String accessToken,
    RefreshToken refreshToken
) {

}