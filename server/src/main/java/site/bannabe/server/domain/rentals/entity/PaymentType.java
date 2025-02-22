package site.bannabe.server.domain.rentals.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentType {

  RENT("대여 결제"),
  OVERDUE("연체 결제"),
  EXTENSION("연장 결제");

  private final String description;

}