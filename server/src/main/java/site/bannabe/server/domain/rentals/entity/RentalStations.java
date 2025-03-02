package site.bannabe.server.domain.rentals.entity;

import jakarta.persistence.Entity;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.bannabe.server.global.type.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RentalStations extends BaseEntity {

  private String name;

  private String address;

  private BigDecimal latitude;

  private BigDecimal longitude;

  private String openTime;

  private String closeTime;

  private String closeDay; // 휴무일 (문자열로 저장)

  private StationStatus status;

  private StationGrade grade;

}