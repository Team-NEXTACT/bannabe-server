package site.bannabe.server.domain.rentals.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

  @Enumerated(EnumType.STRING) // enum 상수가 많아지면 Convert로 수정
  private StationStatus status; // enum으로 변경해야함! (대여소 상태)

  @Enumerated(EnumType.STRING)
  private StationGrade grade; // enum으로 변경해야함! (대여소 등급)

}