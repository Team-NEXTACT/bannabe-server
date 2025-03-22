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
import site.bannabe.server.domain.payments.entity.PaymentType;
import site.bannabe.server.domain.payments.entity.RentalPayments;
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
import site.bannabe.server.domain.rentals.repository.RentalStationItemRepository;
import site.bannabe.server.domain.users.entity.Users;
import site.bannabe.server.global.jwt.GenerateToken;
import site.bannabe.server.global.jwt.JwtProvider;
import site.bannabe.server.global.jwt.UserTokenService;
import site.bannabe.server.global.type.UserTokens;
import site.bannabe.server.util.EntityFixture;

class ReturnControllerTest extends AbstractIntegrationTest {

  @Autowired
  private RentalStationItemRepository rentalStationItemRepository;
  @Autowired
  private JwtProvider jwtProvider;
  @Autowired
  private EntityFixture entityFixture;
  @MockitoBean
  private UserTokenService userTokenService;

  @AfterEach
  void tearDown() {
    entityFixture.clearAll();
  }

  @Test
  @DisplayName("rentalItemToken과 currentStationId로 반납 물품 데이터와 반납스테이션 정보를 조회한다.")
  void getReturnItemInfo() {
    //given
    RentalItemTypes rentalItemTypes = entityFixture.createItemType("65W 충전기", 1000, RentalItemCategory.CHARGER);
    RentalItems rentalItems = entityFixture.createItem(RentalItemStatus.RENTED, null, rentalItemTypes);

    RentalStations rentalStation = entityFixture.createStation("대여 스테이션");
    RentalStations returnStation = entityFixture.createStation("반납 스테이션");

    LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    RentalHistory rentalHistory = entityFixture.createRentalHistory(RentalStatus.RENTAL, null, rentalItems, rentalStation, now);
    entityFixture.createPayment(PaymentType.RENT, "테스트주문", 1000, rentalHistory);

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
    Users user = entityFixture.createUser("test@test.com");
    RentalItemTypes rentalItemTypes = entityFixture.createItemType("65W 충전기", 1000, RentalItemCategory.CHARGER);
    RentalItems rentalItems = entityFixture.createItem(RentalItemStatus.RENTED, null, rentalItemTypes);

    RentalStations rentalStation = entityFixture.createStation("대여 스테이션");
    RentalStations returnStation = entityFixture.createStation("반납 스테이션");

    LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    RentalHistory rentalHistory = entityFixture.createRentalHistory(RentalStatus.OVERDUE, user, rentalItems, rentalStation, now);
    RentalPayments rentalPayments = entityFixture.createPayment(PaymentType.RENT, "테스트주문", 1000, rentalHistory);

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
    RentalItemTypes rentalItemTypes = entityFixture.createItemType("65W 충전기", 1000, RentalItemCategory.CHARGER);
    RentalItems rentalItems = entityFixture.createItem(RentalItemStatus.RENTED, null, rentalItemTypes);

    RentalStations rentalStation = entityFixture.createStation("대여 스테이션");
    RentalStations returnStation = entityFixture.createStation("반납 스테이션");
    LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    RentalHistory rentalHistory = entityFixture.createRentalHistory(RentalStatus.RENTAL, null, rentalItems, rentalStation, now);
    entityFixture.createPayment(PaymentType.RENT, "테스트주문", 1000, rentalHistory);
    entityFixture.createStationItem(rentalItemTypes, returnStation);

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
    RentalStationItems result = rentalStationItemRepository.findByItemTypeAndStation(rentalItemTypes, returnStation);
    assertThat(result.getStock()).isEqualTo(11);
  }

}