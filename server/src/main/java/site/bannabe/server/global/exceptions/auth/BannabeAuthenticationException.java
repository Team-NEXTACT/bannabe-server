package site.bannabe.server.global.exceptions.auth;

import lombok.Getter;
import org.springframework.security.core.AuthenticationException;
import site.bannabe.server.global.exceptions.ErrorCode;

@Getter
public class BannabeAuthenticationException extends AuthenticationException {

  private final ErrorCode errorCode;

  public BannabeAuthenticationException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }

}