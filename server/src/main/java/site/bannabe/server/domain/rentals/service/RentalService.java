package site.bannabe.server.domain.rentals.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.bannabe.server.domain.rentals.controller.response.RentalItemDetailResponse;
import site.bannabe.server.domain.rentals.controller.response.RentalSuccessSimpleResponse;
import site.bannabe.server.domain.rentals.entity.RentalItems;
import site.bannabe.server.domain.rentals.repository.RentalHistoryRepository;
import site.bannabe.server.domain.rentals.repository.RentalItemRepository;

@Service
@RequiredArgsConstructor
public class RentalService {

  private final RentalItemRepository rentalItemRepository;
  private final RentalHistoryRepository rentalHistoryRepository;

  @Transactional(readOnly = true)
  public RentalItemDetailResponse getRentalItemInfo(String rentalItemToken) {
    RentalItems rentalItem = rentalItemRepository.findByToken(rentalItemToken);
    return RentalItemDetailResponse.create(rentalItem);
  }

  @Transactional(readOnly = true)
  public RentalSuccessSimpleResponse getRentalHistoryInfo(String rentalHistoryToken) {
    return rentalHistoryRepository.findRentalHistoryInfoBy(rentalHistoryToken);
  }

}