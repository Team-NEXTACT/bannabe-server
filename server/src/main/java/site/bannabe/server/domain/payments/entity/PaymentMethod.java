package site.bannabe.server.domain.payments.entity;

import jakarta.persistence.Converter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import site.bannabe.server.global.converter.AbstractEnumConverter;

@Getter
@RequiredArgsConstructor
public enum PaymentMethod {

  // 추후 간편 결제등 추가 가능
  CARD("카드");

  private final String value;

  @Converter(autoApply = true)
  static class EnumConverter extends AbstractEnumConverter<PaymentMethod> {

    public EnumConverter() {
      super(PaymentMethod.class);
    }
  }

}