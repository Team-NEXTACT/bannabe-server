package site.bannabe.server.domain.payments.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import site.bannabe.server.domain.rentals.entity.RentalItemTypes;
import site.bannabe.server.domain.rentals.entity.RentalItems;
import site.bannabe.server.domain.rentals.entity.RentalStationItems;
import site.bannabe.server.domain.rentals.entity.RentalStations;
import site.bannabe.server.domain.rentals.repository.RentalStationItemRepository;

@Service
public class RentalStockService {

  private final RentalStationItemRepository rentalStationItemRepository;

  public RentalStockService(RentalStationItemRepository rentalStationItemRepository) {
    this.rentalStationItemRepository = rentalStationItemRepository;
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  void decreaseStock(RentalItems rentalItems) {
    RentalItemTypes rentalItemType = rentalItems.getRentalItemType();
    RentalStations currentStation = rentalItems.getCurrentStation();
    RentalStationItems rentalStationItem = rentalStationItemRepository.findByItemTypeAndStation(rentalItemType, currentStation);
    rentalStationItem.decreaseStock();
  }

}