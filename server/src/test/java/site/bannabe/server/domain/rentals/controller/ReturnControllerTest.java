package site.bannabe.server.domain.rentals.controller;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import site.bannabe.server.config.AbstractIntegrationTest;
import site.bannabe.server.domain.payments.entity.PaymentMethod;
import site.bannabe.server.domain.payments.entity.PaymentStatus;
import site.bannabe.server.domain.payments.entity.PaymentType;
import site.bannabe.server.domain.payments.entity.RentalPayments;
import site.bannabe.server.domain.payments.repository.RentalPaymentRepository;
import site.bannabe.server.domain.rentals.controller.request.ReturnStationRequest;
import site.bannabe.server.domain.rentals.controller.response.ReturnItemDetailResponse;
import site.bannabe.server.domain.rentals.entity.RentalHistory;
import site.bannabe.server.domain.rentals.entity.RentalItemCategory;
import site.bannabe.server.domain.rentals.entity.RentalItemStatus;
import site.bannabe.server.domain.rentals.entity.RentalItemTypes;
import site.bannabe.server.domain.rentals.entity.RentalItems;
import site.bannabe.server.domain.rentals.entity.RentalStationItems;
import site.bannabe.server.domain.rentals.entity.RentalStations;
import site.bannabe.server.domain.rentals.entity.RentalStatus;
import site.bannabe.server.domain.rentals.entity.StationGrade;
import site.bannabe.server.domain.rentals.entity.StationStatus;
import site.bannabe.server.domain.rentals.repository.RentalHistoryRepository;
import site.bannabe.server.domain.rentals.repository.RentalItemRepository;
import site.bannabe.server.domain.rentals.repository.RentalItemTypeRepository;
import site.bannabe.server.domain.rentals.repository.RentalStationItemRepository;
import site.bannabe.server.domain.rentals.repository.RentalStationRepository;
import site.bannabe.server.domain.users.entity.ProviderType;
import site.bannabe.server.domain.users.entity.Role;
import site.bannabe.server.domain.users.entity.Users;
import site.bannabe.server.domain.users.repository.UserRepository;
import site.bannabe.server.global.jwt.GenerateToken;
import site.bannabe.server.global.jwt.JwtProvider;
import site.bannabe.server.global.jwt.UserTokenService;
import site.bannabe.server.global.type.UserTokens;

class ReturnControllerTest extends AbstractIntegrationTest {

  @Autowired
  private RentalPaymentRepository rentalPaymentRepository;
  @Autowired
  private RentalHistoryRepository rentalHistoryRepository;
  @Autowired
  private RentalItemRepository rentalItemRepository;
  @Autowired
  private RentalItemTypeRepository rentalItemTypeRepository;
  @Autowired
  private RentalStationRepository rentalStationRepository;
  @Autowired
  private RentalStationItemRepository rentalStationItemRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private JwtProvider jwtProvider;
  @MockitoBean
  private UserTokenService userTokenService;

  @AfterEach
  void tearDown() {
    rentalStationItemRepository.deleteAllInBatch();
    rentalPaymentRepository.deleteAllInBatch();
    rentalHistoryRepository.deleteAllInBatch();
    rentalItemRepository.deleteAllInBatch();
    rentalStationRepository.deleteAllInBatch();
    rentalItemTypeRepository.deleteAllInBatch();
    userRepository.deleteAllInBatch();
  }

  @Test
  @DisplayName("rentalItemToken과 currentStationId로 반납 물품 데이터와 반납스테이션 정보를 조회한다.")
  void getReturnItemInfo() {
    //given
    RentalItemTypes rentalItemTypes = RentalItemTypes.builder().category(RentalItemCategory.CHARGER).name("65W 충전기").build();
    rentalItemTypeRepository.saveAndFlush(rentalItemTypes);
    RentalItems rentalItems = RentalItems.builder().status(RentalItemStatus.RENTED).rentalItemType(rentalItemTypes).build();
    rentalItemRepository.saveAndFlush(rentalItems);

    RentalStations rentalStation = RentalStations.builder()
                                                 .status(StationStatus.OPEN)
                                                 .grade(StationGrade.FIRST)
                                                 .name("대여 스테이션")
                                                 .build();
    RentalStations returnStation = RentalStations.builder()
                                                 .status(StationStatus.OPEN)
                                                 .grade(StationGrade.FIRST)
                                                 .name("반납 스테이션")
                                                 .build();
    rentalStationRepository.saveAllAndFlush(List.of(rentalStation, returnStation));
    LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    RentalHistory rentalHistory = RentalHistory.builder()
                                               .rentalItem(rentalItems)
                                               .rentalStation(rentalStation)
                                               .status(RentalStatus.RENTAL)
                                               .rentalTimeHour(1)
                                               .startTime(now)
                                               .expectedReturnTime(now.plusHours(1))
                                               .build();
    RentalPayments rentalPayments = RentalPayments.builder()
                                                  .rentalHistory(rentalHistory)
                                                  .status(PaymentStatus.APPROVED)
                                                  .paymentType(PaymentType.RENT)
                                                  .paymentMethod(
                                                      PaymentMethod.CARD)
                                                  .build();
    rentalPaymentRepository.saveAndFlush(rentalPayments);

    GenerateToken generateToken = jwtProvider.generateToken("user", "ROLE_USER");
    //when
    ReturnItemDetailResponse result = given().log().all()
                                             .contentType(ContentType.JSON)
                                             .header(HttpHeaders.AUTHORIZATION, "Bearer " + generateToken.accessToken())
                                             .param("currentStationId", returnStation.getId())
                                             .when()
                                             .get("/v1/returns/{rentalItemToken}", rentalItems.getToken())
                                             .then().log().all()
                                             .assertThat()
                                             .statusCode(HttpStatus.OK.value())
                                             .extract()
                                             .body().jsonPath().getObject("data", ReturnItemDetailResponse.class);

    //then
    assertThat(result).isNotNull()
                      .extracting(
                          ReturnItemDetailResponse::rentalItemName,
                          r -> r.rentalHistory().status(),
                          r -> r.rentalHistory().rentalTime(),
                          r -> r.rentalHistory().startTime(),
                          r -> r.rentalHistory().expectedReturnTime(),
                          r -> r.rentalStation().rentalStationName(),
                          r -> r.rentalStation().currentStationName()
                      ).containsExactly(
                          rentalItemTypes.getName(),
                          rentalHistory.getStatus().name(),
                          rentalHistory.getRentalTimeHour(),
                          rentalHistory.getStartTime(),
                          rentalHistory.getExpectedReturnTime(),
                          rentalStation.getName(),
                          returnStation.getName()
                      );
  }

  @Test
  @DisplayName("연체 상태라면 푸시알림을 전송한다.")
  void sendOverduePushAlert() {
    //given
    Users user = Users.builder().role(Role.USER).providerType(ProviderType.LOCAL).build();
    userRepository.saveAndFlush(user);

    RentalItemTypes rentalItemTypes = RentalItemTypes.builder().category(RentalItemCategory.CHARGER).name("65W 충전기").build();
    rentalItemTypeRepository.saveAndFlush(rentalItemTypes);

    RentalItems rentalItems = RentalItems.builder().status(RentalItemStatus.RENTED).rentalItemType(rentalItemTypes).build();
    rentalItemRepository.saveAndFlush(rentalItems);

    RentalStations rentalStation = RentalStations.builder()
                                                 .status(StationStatus.OPEN)
                                                 .grade(StationGrade.FIRST)
                                                 .name("대여 스테이션")
                                                 .build();
    RentalStations returnStation = RentalStations.builder()
                                                 .status(StationStatus.OPEN)
                                                 .grade(StationGrade.FIRST)
                                                 .name("반납 스테이션")
                                                 .build();
    rentalStationRepository.saveAllAndFlush(List.of(rentalStation, returnStation));

    LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    RentalHistory rentalHistory = RentalHistory.builder()
                                               .user(user)
                                               .rentalItem(rentalItems)
                                               .rentalStation(rentalStation)
                                               .status(RentalStatus.OVERDUE)
                                               .rentalTimeHour(1)
                                               .startTime(now)
                                               .expectedReturnTime(now.plusHours(1))
                                               .build();
    RentalPayments rentalPayments = RentalPayments.builder()
                                                  .rentalHistory(rentalHistory)
                                                  .status(PaymentStatus.APPROVED)
                                                  .paymentType(PaymentType.RENT)
                                                  .paymentMethod(
                                                      PaymentMethod.CARD)
                                                  .build();
    rentalPaymentRepository.saveAndFlush(rentalPayments);

    GenerateToken generateToken = jwtProvider.generateToken(user.getToken(), user.getRole().getRoleKey());
    given(userTokenService.findAllUserTokens(user.getToken())).willReturn(
        List.of(new UserTokens(generateToken.refreshToken(), "deviceToken")));

    //when
    ReturnItemDetailResponse result = given().log().all()
                                             .contentType(ContentType.JSON)
                                             .header(HttpHeaders.AUTHORIZATION, "Bearer " + generateToken.accessToken())
                                             .param("currentStationId", returnStation.getId())
                                             .when()
                                             .get("/v1/returns/{rentalItemToken}", rentalItems.getToken())
                                             .then().log().all()
                                             .assertThat()
                                             .statusCode(HttpStatus.OK.value())
                                             .extract()
                                             .body().jsonPath().getObject("data", ReturnItemDetailResponse.class);

    //then
    assertThat(result).isNotNull()
                      .extracting(
                          ReturnItemDetailResponse::rentalItemName,
                          r -> r.rentalHistory().status(),
                          r -> r.rentalHistory().rentalTime(),
                          r -> r.rentalHistory().startTime(),
                          r -> r.rentalHistory().expectedReturnTime(),
                          r -> r.rentalStation().rentalStationName(),
                          r -> r.rentalStation().currentStationName()
                      ).containsExactly(
                          rentalItemTypes.getName(),
                          rentalHistory.getStatus().name(),
                          rentalHistory.getRentalTimeHour(),
                          rentalHistory.getStartTime(),
                          rentalHistory.getExpectedReturnTime(),
                          rentalStation.getName(),
                          returnStation.getName()
                      );
  }

  @Test
  @DisplayName("반납 성공 시 200 응답")
  void returnItem() {
    //given
    RentalItemTypes rentalItemTypes = RentalItemTypes.builder().category(RentalItemCategory.CHARGER).name("65W 충전기").build();
    rentalItemTypeRepository.saveAndFlush(rentalItemTypes);
    RentalItems rentalItems = RentalItems.builder().status(RentalItemStatus.RENTED).rentalItemType(rentalItemTypes).build();
    rentalItemRepository.saveAndFlush(rentalItems);

    RentalStations rentalStation = RentalStations.builder()
                                                 .status(StationStatus.OPEN)
                                                 .grade(StationGrade.FIRST)
                                                 .name("대여 스테이션")
                                                 .build();
    RentalStations returnStation = RentalStations.builder()
                                                 .status(StationStatus.OPEN)
                                                 .grade(StationGrade.FIRST)
                                                 .name("반납 스테이션")
                                                 .build();
    rentalStationRepository.saveAllAndFlush(List.of(rentalStation, returnStation));
    LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    RentalHistory rentalHistory = RentalHistory.builder()
                                               .rentalItem(rentalItems)
                                               .rentalStation(rentalStation)
                                               .status(RentalStatus.RENTAL)
                                               .rentalTimeHour(1)
                                               .startTime(now)
                                               .expectedReturnTime(now.plusHours(1))
                                               .build();
    RentalPayments rentalPayments = RentalPayments.builder()
                                                  .rentalHistory(rentalHistory)
                                                  .status(PaymentStatus.APPROVED)
                                                  .paymentType(PaymentType.RENT)
                                                  .paymentMethod(
                                                      PaymentMethod.CARD)
                                                  .build();
    rentalPaymentRepository.saveAndFlush(rentalPayments);
    RentalStationItems rentalStationItems = new RentalStationItems(rentalItemTypes, returnStation, 10);
    rentalStationItemRepository.saveAndFlush(rentalStationItems);

    GenerateToken generateToken = jwtProvider.generateToken("user", "ROLE_USER");

    ReturnStationRequest request = new ReturnStationRequest(returnStation.getId());

    //when
    RestAssured.given().log().all()
               .contentType(ContentType.JSON)
               .header(HttpHeaders.AUTHORIZATION, "Bearer " + generateToken.accessToken())
               .body(request)
               .when()
               .post("/v1/returns/{rentalItemToken}", rentalItems.getToken())
               .then().log().all()
               .assertThat()
               .statusCode(HttpStatus.OK.value());
    //then
    RentalStationItems result = rentalStationItemRepository.findByItemTypeAndStation(rentalItemTypes,
        returnStation);
    assertThat(result.getStock()).isEqualTo(11);
  }

}