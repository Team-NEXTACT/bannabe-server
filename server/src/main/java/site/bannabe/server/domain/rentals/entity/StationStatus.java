package site.bannabe.server.domain.rentals.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StationStatus {

  OPEN("운영 중"),
  CLOSE("운영 종료");

  private final String description;

}