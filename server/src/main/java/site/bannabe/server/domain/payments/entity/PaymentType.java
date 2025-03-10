package site.bannabe.server.domain.payments.entity;

import jakarta.persistence.Converter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import site.bannabe.server.global.converter.AbstractEnumConverter;

@Getter
@RequiredArgsConstructor
public enum PaymentType {

  RENT("대여 결제"),
  OVERDUE("연체 결제"),
  EXTENSION("연장 결제");

  private final String description;

  @Converter(autoApply = true)
  static class EnumConverter extends AbstractEnumConverter<PaymentType> {

    public EnumConverter() {
      super(PaymentType.class);
    }

  }

}