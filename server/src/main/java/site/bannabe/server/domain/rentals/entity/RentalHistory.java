package site.bannabe.server.domain.rentals.entity;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.bannabe.server.domain.users.entity.Users;
import site.bannabe.server.global.converter.RentalStatusConverter;
import site.bannabe.server.global.type.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RentalHistory extends BaseEntity {

  @Convert(converter = RentalStatusConverter.class)
  private RentalStatus status;

  private LocalDateTime startTime;

  private LocalDateTime endTime;

  private LocalDateTime returnTime;

  private Integer rentalTimeHour;

  private String token;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private Users user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "rental_item_id")
  private RentalItems rentalItem;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "rental_station_id")
  private RentalStations rentalStation;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "return_station_id")
  private RentalStations rentalStations;

}