package site.bannabe.server.domain.rentals.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.bannabe.server.domain.rentals.controller.response.RentalItemDetailResponse;
import site.bannabe.server.domain.rentals.controller.response.RentalStationDetailResponse;
import site.bannabe.server.domain.rentals.controller.response.RentalStationSimpleResponse;
import site.bannabe.server.domain.rentals.service.RentalStationService;

@RestController
@RequestMapping("/v1/stations")
@RequiredArgsConstructor
public class RentalStationController {

  private final RentalStationService rentalStationService;

  @GetMapping
  public RentalStationSimpleResponse getAllRentalStation() {
    return rentalStationService.getAllRentalStations();
  }

  @GetMapping("/{stationId}")
  public RentalStationDetailResponse getRentalStationDetail(@PathVariable("stationId") Long stationId) {
    return rentalStationService.getRentalStationDetail(stationId);
  }

  @GetMapping("/{stationId}/items/{itemTypeId}")
  public RentalItemDetailResponse getRentalItemDetail(@PathVariable("stationId") Long stationId,
      @PathVariable("itemTypeId") Long itemTypeId) {
    return rentalStationService.getRentalItemDetail(stationId, itemTypeId);
  }

}