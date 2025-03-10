package site.bannabe.server.domain.rentals.repository.querydsl;

import site.bannabe.server.domain.rentals.controller.response.RentalItemTypeDetailResponse;

public interface CustomRentalItemRepository {

  RentalItemTypeDetailResponse findRentalItemDetailBy(Long stationId, Long itemTypeId);

}