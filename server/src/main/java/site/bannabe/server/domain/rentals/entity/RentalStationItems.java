package site.bannabe.server.domain.rentals.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.bannabe.server.global.type.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RentalStationItems extends BaseEntity {

  private Integer stock;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "rental_station_id")
  private RentalStations rentalStation;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "rental_item_type_id")
  private RentalItemTypes rentalItemType;

}