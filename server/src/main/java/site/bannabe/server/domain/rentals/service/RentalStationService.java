package site.bannabe.server.domain.rentals.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.bannabe.server.domain.rentals.controller.response.RentalItemDetailResponse;
import site.bannabe.server.domain.rentals.controller.response.RentalStationDetailResponse;
import site.bannabe.server.domain.rentals.controller.response.RentalStationDetailResponse.RentalItemResponse;
import site.bannabe.server.domain.rentals.controller.response.RentalStationSimpleResponse;
import site.bannabe.server.domain.rentals.entity.RentalStations;
import site.bannabe.server.domain.rentals.repository.RentalItemRepository;
import site.bannabe.server.domain.rentals.repository.RentalStationRepository;

@Service
@RequiredArgsConstructor
public class RentalStationService {

  private final RentalStationRepository rentalStationRepository;
  private final RentalItemRepository rentalItemRepository;

  @Transactional(readOnly = true)
  public RentalStationSimpleResponse getAllRentalStations() {
    List<RentalStations> rentalStations = rentalStationRepository.findAll();
    return RentalStationSimpleResponse.create(rentalStations);
  }

  @Transactional(readOnly = true)
  public RentalStationDetailResponse getRentalStationDetail(Long stationId) {
    List<RentalItemResponse> rentalItemResponses = rentalStationRepository.findRentalStationDetailBy(stationId);
    return new RentalStationDetailResponse(rentalItemResponses);
  }

  @Transactional(readOnly = true)
  public RentalItemDetailResponse getRentalItemDetail(Long stationId, Long itemTypeId) {
    return rentalItemRepository.findRentalItemDetailBy(stationId, itemTypeId);
  }

}