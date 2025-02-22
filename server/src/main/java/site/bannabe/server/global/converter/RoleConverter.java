package site.bannabe.server.global.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import site.bannabe.server.domain.users.entity.Role;

@Converter
public class RoleConverter implements AttributeConverter<Role, String> {

  @Override
  public String convertToDatabaseColumn(Role attribute) {
    return attribute.name();
  }

  @Override
  public Role convertToEntityAttribute(String dbData) {
    return Role.valueOf(dbData);
  }

}
