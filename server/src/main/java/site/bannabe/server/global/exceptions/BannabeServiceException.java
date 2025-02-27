package site.bannabe.server.global.exceptions;

import lombok.Getter;

@Getter
public class BannabeServiceException extends RuntimeException {

  private final ErrorCode errorCode;

  public BannabeServiceException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }

}