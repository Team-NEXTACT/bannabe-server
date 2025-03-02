package site.bannabe.server.domain.rentals.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.bannabe.server.domain.users.entity.Users;
import site.bannabe.server.global.type.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RentalHistory extends BaseEntity {

  private RentalStatus status;

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
  private RentalStations rentalStations;

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