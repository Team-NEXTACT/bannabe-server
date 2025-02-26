package site.bannabe.server.global.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

  UNSUPPORTED_CONTENT_TYPE(HttpStatus.BAD_REQUEST, "지원하지 않는 Content-Type입니다."),
  MISSING_REQUIRED_FIELDS(HttpStatus.BAD_REQUEST, "필수 요청 데이터가 누락되었습니다."),
  INVALID_CREDENTIALS(HttpStatus.BAD_REQUEST, "로그인에 실패했습니다. 이메일 또는 비밀번호를 확인해주세요."),
  TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
  INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
  INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "RefreshToken이 유효하지 않습니다."),
  UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "사용자 인증이 필요합니다."),
  FORBIDDEN(HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),
  BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
  VALIDATION_FAILED(HttpStatus.BAD_REQUEST, "요청 데이터가 올바르지 않습니다."),
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류입니다. 관리자에게 문의하세요."),
  USER_NOT_FOUND(HttpStatus.NOT_FOUND, "회원정보가 존재하지 않습니다."),
  NEW_PASSWORD_NOT_EQUALS(HttpStatus.CONFLICT, "새 비밀번호가 일치하지 않습니다."),
  PASSWORD_NOT_MATCH(HttpStatus.CONFLICT, "비밀번호가 일치하지 않습니다."),
  NICKNAME_EXISTS(HttpStatus.CONFLICT, "이미 사용중인 닉네임입니다."),
  REUSED_PASSWORD(HttpStatus.CONFLICT, "동일한 비밀번호로 변경할 수 없습니다."),
  ;

  private final HttpStatus httpStatus;
  private final String message;

}