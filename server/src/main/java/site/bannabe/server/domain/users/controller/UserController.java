package site.bannabe.server.domain.users.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.bannabe.server.domain.users.controller.request.UserChangeNicknameRequest;
import site.bannabe.server.domain.users.controller.request.UserChangePasswordRequest;
import site.bannabe.server.domain.users.controller.request.UserChangeProfileImageRequest;
import site.bannabe.server.domain.users.controller.response.S3PreSignedUrlResponse;
import site.bannabe.server.domain.users.controller.response.UserGetActiveRentalResponse.RentalHistoryResponse;
import site.bannabe.server.domain.users.service.UserService;
import site.bannabe.server.global.security.auth.PrincipalDetails;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PutMapping("/me/password")
  public void changePassword(@RequestBody @Valid UserChangePasswordRequest changePasswordRequest,
      @AuthenticationPrincipal PrincipalDetails principalDetails) {
    String email = principalDetails.getUsername();
    userService.changePassword(email, changePasswordRequest);
  }

  @PatchMapping("/me/profile-image")
  public void changeProfileImage(@RequestBody UserChangeProfileImageRequest changeProfileImageRequest,
      @AuthenticationPrincipal PrincipalDetails principalDetails) {
    String email = principalDetails.getUsername();
    userService.changeProfileImage(email, changeProfileImageRequest);
  }

  @PatchMapping("/me/nickname")
  public void changeNickname(@RequestBody @Valid UserChangeNicknameRequest changeNicknameRequest,
      @AuthenticationPrincipal PrincipalDetails principalDetails) {
    String email = principalDetails.getUsername();
    userService.changeNickname(email, changeNicknameRequest);
  }

  @GetMapping("/me/profile-image/pre-signed")
  public S3PreSignedUrlResponse getPreSignedUrl(@RequestParam(name = "extension") String extension) {
    return userService.getPreSignedUrl(extension);
  }

  @GetMapping("/me/rentals/active")
  public List<RentalHistoryResponse> getActiveRentalHistory(@AuthenticationPrincipal PrincipalDetails principalDetails) {
    String email = principalDetails.getUsername();
    return userService.getActiveRentalHistory(email);
  }

}