package site.bannabe.server.domain.rentals.entity;

import jakarta.persistence.Converter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import site.bannabe.server.global.converter.AbstractEnumConverter;

@Getter
@RequiredArgsConstructor
public enum PaymentStatus {

  SUCCESS("성공"),
  CANCEL("취소");

  private final String description;

  @Converter(autoApply = true)
  static class EnumConverter extends AbstractEnumConverter<PaymentStatus> {

    public EnumConverter() {
      super(PaymentStatus.class);
    }

  }

}