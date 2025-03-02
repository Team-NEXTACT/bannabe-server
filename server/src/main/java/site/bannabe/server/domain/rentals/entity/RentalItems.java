package site.bannabe.server.domain.rentals.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.bannabe.server.global.type.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RentalItems extends BaseEntity {

  private RentalItemStatus status;

  private String token; // 물품 고유 식별값

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "rental_item_type_id")
  private RentalItemTypes rentalItemType;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "current_station_id")
  private RentalStations currentStationId;

}