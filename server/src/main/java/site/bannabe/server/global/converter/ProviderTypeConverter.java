package site.bannabe.server.global.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import site.bannabe.server.domain.users.entity.ProviderType;

@Converter
public class ProviderTypeConverter implements AttributeConverter<ProviderType, String> {

  @Override
  public String convertToDatabaseColumn(ProviderType providerType) {
    return providerType.name();
  }

  @Override
  public ProviderType convertToEntityAttribute(String dbData) {
    return ProviderType.valueOf(dbData);
  }

}