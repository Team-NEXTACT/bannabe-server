package site.bannabe.server.domain.common.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EventStatus {

  UP_COMING("예정"),
  ON_GOING("진행중"),
  END("종료");

  private final String description;

}