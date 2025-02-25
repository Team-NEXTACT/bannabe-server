package site.bannabe.server.domain.users.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.bannabe.server.domain.users.controller.request.TokenRefreshRequest;
import site.bannabe.server.domain.users.controller.request.UserRegisterRequest;
import site.bannabe.server.domain.users.service.AuthService;
import site.bannabe.server.global.type.TokenResponse;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/register")
  public void registerUser(@RequestBody @Valid UserRegisterRequest registerRequest) {
    authService.registerUser(registerRequest);
  }

  @PostMapping("/token/refresh")
  public TokenResponse refreshToken(@RequestBody TokenRefreshRequest tokenRefreshRequest) {
    return authService.refreshToken(tokenRefreshRequest.refreshToken());
  }

}