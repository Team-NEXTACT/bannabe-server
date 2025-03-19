package site.bannabe.server.domain.rentals.service;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.bannabe.server.domain.rentals.controller.response.ReturnItemDetailResponse;
import site.bannabe.server.domain.rentals.entity.RentalHistory;
import site.bannabe.server.domain.rentals.entity.RentalItems;
import site.bannabe.server.domain.rentals.entity.RentalStations;
import site.bannabe.server.domain.rentals.entity.RentalStatus;
import site.bannabe.server.domain.rentals.repository.RentalHistoryRepository;
import site.bannabe.server.domain.rentals.repository.RentalStationRepository;
import site.bannabe.server.global.exceptions.BannabeServiceException;
import site.bannabe.server.global.exceptions.ErrorCode;

@Service
@RequiredArgsConstructor
public class ReturnService {

  private final RentalHistoryRepository rentalHistoryRepository;
  private final RentalStationRepository rentalStationRepository;
  private final StockLockService stockLockService;

  @Transactional
  public ReturnItemDetailResponse getReturnItemInfo(String rentalItemToken, Long currentStationId) {
    RentalHistory rentalHistory = rentalHistoryRepository.findByItemToken(rentalItemToken);
    RentalItems rentalItem = rentalHistory.getRentalItem();
    if (!rentalItem.isRented()) {
      throw new BannabeServiceException(ErrorCode.RENTAL_ITEM_NOT_RENTED);
    }
    rentalHistory.validateOverdue(LocalDateTime.now());
    RentalStations currentStation = rentalStationRepository.findById(currentStationId)
                                                           .orElseThrow(() ->
                                                               new BannabeServiceException(ErrorCode.RENTAL_STATION_NOT_FOUND));
    return ReturnItemDetailResponse.from(rentalHistory, currentStation);
  }

  @Transactional
  public void returnRentalItem(String rentalItemToken, Long returnStationId) {
    RentalHistory rentalHistory = rentalHistoryRepository.findByItemToken(rentalItemToken);
    if (rentalHistory.getStatus().equals(RentalStatus.OVERDUE)) {
      throw new BannabeServiceException(ErrorCode.RENTAL_ITEM_OVERDUE);
    }
    RentalStations currentStation = rentalStationRepository.findById(returnStationId)
                                                           .orElseThrow(() ->
                                                               new BannabeServiceException(ErrorCode.RENTAL_STATION_NOT_FOUND));

    RentalItems rentalItem = rentalHistory.getRentalItem();
    rentalHistory.updateOnReturn(currentStation, LocalDateTime.now());
    rentalItem.updateOnReturn(currentStation);
    stockLockService.increaseStock(rentalItem);
  }

}