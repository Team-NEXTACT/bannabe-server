package site.bannabe.server.global.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

  UNSUPPORTED_CONTENT_TYPE("지원하지 않는 Content-Type입니다."),
  MISSING_REQUIRED_FIELDS("필수 요청 데이터가 누락되었습니다."),
  INVALID_CREDENTIALS("로그인에 실패했습니다. 이메일 또는 비밀번호를 확인해주세요."),
  TOKEN_EXPIRED("토큰이 만료되었습니다."),
  INVALID_TOKEN("유효하지 않은 토큰입니다."),
  UNAUTHORIZED("사용자 인증이 필요합니다."),
  FORBIDDEN("접근 권한이 없습니다."),
  BAD_REQUEST("잘못된 요청입니다."),
  VALIDATION_FAILED("요청 데이터가 올바르지 않습니다."),
  INTERNAL_SERVER_ERROR("서버 오류입니다. 관리자에게 문의하세요."),
  USER_NOT_FOUND("회원정보가 존재하지 않습니다.");

  private final String message;

}