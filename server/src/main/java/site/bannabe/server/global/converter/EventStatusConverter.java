package site.bannabe.server.global.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import site.bannabe.server.domain.common.entity.EventStatus;

@Converter
public class EventStatusConverter implements AttributeConverter<EventStatus, String> {

  @Override
  public String convertToDatabaseColumn(EventStatus attribute) {
    return attribute.name();
  }

  @Override
  public EventStatus convertToEntityAttribute(String dbData) {
    return EventStatus.valueOf(dbData);
  }

}
