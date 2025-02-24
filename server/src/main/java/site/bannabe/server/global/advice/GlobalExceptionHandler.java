package site.bannabe.server.global.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import site.bannabe.server.global.type.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {


  // 추후 Exception Custom으로 Exception Handler 수정 예정
  @ExceptionHandler
  public ResponseEntity<ApiResponse<?>> handleException(Exception ex) {
    return ResponseEntity.badRequest().body(ApiResponse.failure(ex.getMessage()));
  }

}