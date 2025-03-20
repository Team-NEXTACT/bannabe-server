package site.bannabe.server.domain.rentals.controller;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import java.time.LocalDateTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import site.bannabe.server.config.AbstractIntegrationTest;
import site.bannabe.server.domain.payments.entity.PaymentMethod;
import site.bannabe.server.domain.payments.entity.PaymentStatus;
import site.bannabe.server.domain.payments.entity.PaymentType;
import site.bannabe.server.domain.payments.entity.RentalPayments;
import site.bannabe.server.domain.payments.repository.RentalPaymentRepository;
import site.bannabe.server.domain.rentals.entity.RentalHistory;
import site.bannabe.server.domain.rentals.entity.RentalItemCategory;
import site.bannabe.server.domain.rentals.entity.RentalItemStatus;
import site.bannabe.server.domain.rentals.entity.RentalItemTypes;
import site.bannabe.server.domain.rentals.entity.RentalItems;
import site.bannabe.server.domain.rentals.entity.RentalStations;
import site.bannabe.server.domain.rentals.entity.RentalStatus;
import site.bannabe.server.domain.rentals.entity.StationGrade;
import site.bannabe.server.domain.rentals.entity.StationStatus;
import site.bannabe.server.domain.rentals.repository.RentalHistoryRepository;
import site.bannabe.server.domain.rentals.repository.RentalItemRepository;
import site.bannabe.server.domain.rentals.repository.RentalItemTypeRepository;
import site.bannabe.server.domain.rentals.repository.RentalStationRepository;
import site.bannabe.server.global.jwt.GenerateToken;
import site.bannabe.server.global.jwt.JwtProvider;

class RentalControllerTest extends AbstractIntegrationTest {

  @Autowired
  private RentalItemRepository rentalItemRepository;
  @Autowired
  private RentalItemTypeRepository rentalItemTypeRepository;
  @Autowired
  private RentalStationRepository rentalStationRepository;
  @Autowired
  private RentalHistoryRepository rentalHistoryRepository;
  @Autowired
  private RentalPaymentRepository rentalPaymentRepository;
  @Autowired
  private JwtProvider jwtProvider;

  @AfterEach
  void tearDown() {
    rentalPaymentRepository.deleteAllInBatch();
    rentalHistoryRepository.deleteAllInBatch();
    rentalItemRepository.deleteAllInBatch();
    rentalItemTypeRepository.deleteAllInBatch();
    rentalStationRepository.deleteAllInBatch();
  }

  @Test
  @DisplayName("rentalItemToken을 사용해 대여물품 정보를 조회한다.")
  void getRentalItemToken() {
    //given
    RentalItemTypes itemType = RentalItemTypes.builder().category(RentalItemCategory.CHARGER).name("65W 충전기")
                                              .price(1000).build();
    rentalItemTypeRepository.saveAndFlush(itemType);
    RentalStations station = RentalStations.builder()
                                           .status(StationStatus.OPEN)
                                           .grade(StationGrade.FIRST)
                                           .name("테스트 스테이션")
                                           .build();
    rentalStationRepository.saveAndFlush(station);

    RentalItems item = RentalItems.builder()
                                  .rentalItemType(itemType)
                                  .currentStation(station)
                                  .status(RentalItemStatus.AVAILABLE)
                                  .build();
    rentalItemRepository.saveAndFlush(item);

    GenerateToken generateToken = jwtProvider.generateToken("entityToken", "ROLE_USER");

    //when
    given().log().all()
           .contentType(JSON)
           .header(AUTHORIZATION, "Bearer " + generateToken.accessToken())
           .when()
           .get("/v1/rentals/{rentalItemToken}", item.getToken())
           .then().log().all()
           .assertThat()
           .statusCode(HttpStatus.OK.value())
           .body("data.name", equalTo(itemType.getName()))
           .body("data.price", equalTo(itemType.getPrice()))
           .body("data.currentStationName", equalTo(station.getName()));

    //then
  }

  @Test
  @DisplayName("대여 성공시 rentalItemToken을 사용해 대여내역 정보를 조회한다.")
  void getRentalHistoryInfo() {
    //given
    RentalItemTypes itemType = RentalItemTypes.builder().category(RentalItemCategory.CHARGER).name("65W 충전기")
                                              .price(1000).build();
    rentalItemTypeRepository.saveAndFlush(itemType);
    RentalStations station = RentalStations.builder()
                                           .status(StationStatus.OPEN)
                                           .grade(StationGrade.FIRST)
                                           .name("테스트 스테이션")
                                           .build();
    rentalStationRepository.saveAndFlush(station);

    RentalItems item = RentalItems.builder()
                                  .rentalItemType(itemType)
                                  .currentStation(station)
                                  .status(RentalItemStatus.AVAILABLE)
                                  .build();
    rentalItemRepository.saveAndFlush(item);

    LocalDateTime now = LocalDateTime.now();
    RentalHistory rentalHistory = RentalHistory.builder()
                                               .rentalItem(item)
                                               .rentalStation(station)
                                               .status(RentalStatus.RENTAL)
                                               .rentalTimeHour(1)
                                               .startTime(now)
                                               .expectedReturnTime(now.plusHours(1))
                                               .build();
    RentalPayments rentalPayments = RentalPayments.builder()
                                                  .paymentMethod(PaymentMethod.CARD)
                                                  .paymentType(PaymentType.RENT)
                                                  .status(PaymentStatus.APPROVED)
                                                  .totalAmount(1000)
                                                  .rentalHistory(rentalHistory)
                                                  .build();
    rentalPaymentRepository.saveAndFlush(rentalPayments);
    GenerateToken generateToken = jwtProvider.generateToken("entityToken", "ROLE_USER");

    //when then
    given().log().all()
           .contentType(JSON)
           .header(AUTHORIZATION, "Bearer " + generateToken.accessToken())
           .when()
           .get("/v1/rentals/success/{rentalHistoryToken}", rentalHistory.getToken())
           .then().log().all()
           .assertThat()
           .statusCode(HttpStatus.OK.value())
           .body("data.totalAmount", equalTo(rentalPayments.getTotalAmount()))
           .body("data.itemName", equalTo(itemType.getName()))
           .body("data.rentalTime", equalTo(rentalHistory.getRentalTimeHour()))
           .body("data.rentalStationName", equalTo(station.getName()));
  }

}