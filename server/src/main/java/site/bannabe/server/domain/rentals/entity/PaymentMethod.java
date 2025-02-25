package site.bannabe.server.domain.rentals.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentMethod {

  // 추후 간편 결제등 추가 가능
  CARD("카드");

  private final String value;

}