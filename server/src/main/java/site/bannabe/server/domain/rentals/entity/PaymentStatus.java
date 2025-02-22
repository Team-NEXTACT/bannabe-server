package site.bannabe.server.domain.rentals.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentStatus {

  SUCCESS("성공"),
  CANCEL("취소");

  private final String description;

}