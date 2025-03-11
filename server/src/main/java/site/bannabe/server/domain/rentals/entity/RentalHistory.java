package site.bannabe.server.domain.rentals.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.bannabe.server.domain.users.entity.Users;
import site.bannabe.server.global.type.BaseEntity;
import site.bannabe.server.global.type.OrderInfo;
import site.bannabe.server.global.utils.RandomCodeGenerator;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RentalHistory extends BaseEntity {

  @Default
  private RentalStatus status = RentalStatus.RENTAL;

  private LocalDateTime startTime;

  private LocalDateTime expectedReturnTime;

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
  private RentalStations returnStation;

  private RentalHistory(RentalStatus status, LocalDateTime startTime, LocalDateTime expectedReturnTime, LocalDateTime returnTime,
      Integer rentalTimeHour, String token, Users user, RentalItems rentalItem, RentalStations rentalStation,
      RentalStations returnStation) {
    this.status = status;
    this.startTime = startTime;
    this.expectedReturnTime = expectedReturnTime;
    this.returnTime = returnTime;
    this.rentalTimeHour = rentalTimeHour;
    this.token = token;
    this.user = user;
    this.rentalItem = rentalItem;
    this.rentalStation = rentalStation;
    this.returnStation = returnStation;
  }

  public static RentalHistory create(RentalItems rentalItem, Users user, OrderInfo orderInfo, LocalDateTime startTime) {
    return RentalHistory.builder()
                        .startTime(startTime)
                        .expectedReturnTime(startTime.plusHours(orderInfo.getRentalTime()))
                        .rentalTimeHour(orderInfo.getRentalTime())
                        .token(RandomCodeGenerator.generateRandomToken(RentalHistory.class))
                        .user(user)
                        .rentalItem(rentalItem)
                        .rentalStation(rentalItem.getCurrentStation())
                        .build();
  }

  public void changeStatus(RentalStatus status) {
    this.status = status;
  }

  public boolean isOverdue(LocalDateTime now) {
    return !this.expectedReturnTime.isAfter(now);
  }

  public void validateOverdue(LocalDateTime now) {
    if (this.status.equals(RentalStatus.RENTAL) && isOverdue(now)) {
      changeStatus(RentalStatus.OVERDUE);
    }
  }

}