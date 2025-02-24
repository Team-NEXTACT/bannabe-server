package site.bannabe.server.global.type;

import java.util.Collections;

public record ApiResponse<T>(
    Boolean success,
    String message,
    T data
) {

  public static <T> ApiResponse<T> success(T data) {
    return new ApiResponse<>(true, "요청이 성공적으로 처리되었습니다.", data);
  }

  public static <T> ApiResponse<T> success(String message, T data) {
    return new ApiResponse<>(true, message, data);
  }

  public static <T> ApiResponse<T> failure(String message, T data) {
    return new ApiResponse<>(false, message, data);
  }

  public static ApiResponse<Object> failure(String message) {
    return new ApiResponse<>(false, message, Collections.emptyMap());
  }

}