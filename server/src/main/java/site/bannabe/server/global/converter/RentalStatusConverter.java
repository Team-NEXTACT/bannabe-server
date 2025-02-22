package site.bannabe.server.global.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import site.bannabe.server.domain.rentals.entity.RentalStatus;

@Converter
public class RentalStatusConverter implements AttributeConverter<RentalStatus, String> {

  @Override
  public String convertToDatabaseColumn(RentalStatus attribute) {
    return attribute.name();
  }

  @Override
  public RentalStatus convertToEntityAttribute(String dbData) {
    return RentalStatus.valueOf(dbData);
  }

}
