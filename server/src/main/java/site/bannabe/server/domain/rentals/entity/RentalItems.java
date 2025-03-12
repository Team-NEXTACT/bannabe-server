package site.bannabe.server.domain.rentals.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.bannabe.server.global.type.BaseEntity;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RentalItems extends BaseEntity {

  @Default
  private RentalItemStatus status = RentalItemStatus.AVAILABLE; // 물품 상태

  private String token; // 물품 고유 식별값

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "rental_item_type_id")
  private RentalItemTypes rentalItemType;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "current_station_id")
  private RentalStations currentStation;

  private RentalItems(RentalItemStatus status, String token, RentalItemTypes rentalItemType, RentalStations currentStation) {
    this.status = status;
    this.token = token;
    this.rentalItemType = rentalItemType;
    this.currentStation = currentStation;
  }

  public void rentOut() {
    if (this.status != RentalItemStatus.AVAILABLE) {
      throw new RuntimeException("대여할 수 없는 물품입니다.");
    }
    this.status = RentalItemStatus.RENTED;
    this.currentStation = null;
  }

}