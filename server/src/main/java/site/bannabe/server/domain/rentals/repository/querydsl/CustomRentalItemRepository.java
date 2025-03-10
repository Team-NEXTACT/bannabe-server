package site.bannabe.server.domain.rentals.repository.querydsl;

import site.bannabe.server.domain.rentals.controller.response.RentalItemDetailResponse;

public interface CustomRentalItemRepository {

  RentalItemDetailResponse findRentalItemDetailBy(Long stationId, Long itemTypeId);

}