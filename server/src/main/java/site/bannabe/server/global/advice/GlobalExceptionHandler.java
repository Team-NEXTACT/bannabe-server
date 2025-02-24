package site.bannabe.server.global.advice;

import java.util.List;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import site.bannabe.server.global.type.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
    List<String> errorMessages = ex.getBindingResult()
                                   .getAllErrors()
                                   .stream()
                                   .map(DefaultMessageSourceResolvable::getDefaultMessage)
                                   .toList();
    return ResponseEntity.badRequest().body(ApiResponse.failure("입력값이 올바르지 않습니다.", errorMessages));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse<?>> handleException(Exception ex) {
    return ResponseEntity.badRequest().body(ApiResponse.failure(ex.getMessage()));
  }

}