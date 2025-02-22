package site.bannabe.server.domain.rentals.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RentalStatus {

  RENTAL("대여 중"),
  EXTENSION("연장 중"),
  OVERDUE("연체 중"),
  RETURN("반납");

  private final String description;

}