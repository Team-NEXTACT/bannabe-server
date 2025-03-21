package site.bannabe.server.domain.payments.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;
import static org.mockito.BDDMockito.given;

import io.restassured.http.ContentType;
import java.time.LocalDateTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import site.bannabe.server.config.AbstractIntegrationTest;
import site.bannabe.server.domain.payments.controller.request.PaymentCalculateRequest;
import site.bannabe.server.domain.payments.controller.request.PaymentConfirmRequest;
import site.bannabe.server.domain.payments.entity.PaymentMethod;
import site.bannabe.server.domain.payments.entity.PaymentType;
import site.bannabe.server.domain.payments.repository.RentalPaymentRepository;
import site.bannabe.server.domain.payments.service.OrderInfoService;
import site.bannabe.server.domain.rentals.entity.RentalItemCategory;
import site.bannabe.server.domain.rentals.entity.RentalItemStatus;
import site.bannabe.server.domain.rentals.entity.RentalItemTypes;
import site.bannabe.server.domain.rentals.entity.RentalItems;
import site.bannabe.server.domain.rentals.entity.RentalStationItems;
import site.bannabe.server.domain.rentals.entity.RentalStations;
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
import site.bannabe.server.global.api.TossPaymentApiClient;
import site.bannabe.server.global.api.TossPaymentConfirmResponse;
import site.bannabe.server.global.api.TossPaymentConfirmResponse.ReceiptInfo;
import site.bannabe.server.global.jwt.GenerateToken;
import site.bannabe.server.global.jwt.JwtProvider;

class PaymentControllerTest extends AbstractIntegrationTest {

  @Autowired
  private RentalItemRepository rentalItemRepository;
  @Autowired
  private RentalItemTypeRepository rentalItemTypeRepository;
  @Autowired
  private RentalStationRepository rentalStationRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private RentalStationItemRepository rentalStationItemRepository;
  @Autowired
  private RentalHistoryRepository rentalHistoryRepository;
  @Autowired
  private RentalPaymentRepository rentalPaymentRepository;
  @Autowired
  private JwtProvider jwtProvider;
  @Autowired
  private OrderInfoService orderInfoService;
  @MockitoBean
  private TossPaymentApiClient tossPaymentApiClient;

  @AfterEach
  void tearDown() {
    rentalPaymentRepository.deleteAllInBatch();
    rentalHistoryRepository.deleteAllInBatch();
    rentalItemRepository.deleteAllInBatch();
    rentalStationItemRepository.deleteAllInBatch();
    rentalStationRepository.deleteAllInBatch();
    rentalItemTypeRepository.deleteAllInBatch();
    userRepository.deleteAllInBatch();
    orderInfoService.removeOrderInfo("orderId");
  }

  @Test
  @DisplayName("rentalItemToken과 rentalTime을 받아서 결제금액을 계산한다.")
  void calculateAmount() {
    //given
    RentalItemTypes rentalItemTypes = RentalItemTypes.builder().category(RentalItemCategory.CHARGER).price(1000).build();
    rentalItemTypeRepository.save(rentalItemTypes);

    RentalItems rentalItems = RentalItems.builder().status(RentalItemStatus.AVAILABLE).rentalItemType(rentalItemTypes).build();
    rentalItemRepository.save(rentalItems);

    PaymentCalculateRequest request = new PaymentCalculateRequest(rentalItems.getToken(), 2);
    GenerateToken generateToken = jwtProvider.generateToken("entityToken", "ROLE_USER");
    //when then
    given().log().all()
           .contentType(ContentType.JSON)
           .header(HttpHeaders.AUTHORIZATION, "Bearer " + generateToken.accessToken())
           .body(request)
           .when()
           .post("/v1/payments/calculate")
           .then().log().all()
           .assertThat()
           .statusCode(HttpStatus.OK.value())
           .body("data.amount", equalTo(rentalItemTypes.getPrice() * request.rentalTime()));
  }

  @Test
  @DisplayName("결제창 호출 URL 조회 요청 시 결제창 url을 응답한다.")
  void getCheckoutUrl() {
    //given
    GenerateToken generateToken = jwtProvider.generateToken("entityToken", "ROLE_USER");

    //when then
    given().log().all()
           .contentType(ContentType.JSON)
           .header(HttpHeaders.AUTHORIZATION, "Bearer " + generateToken.accessToken())
           .when()
           .get("/v1/payments/checkout-url")
           .then().log().all()
           .assertThat()
           .statusCode(HttpStatus.OK.value())
           .body("data.checkoutUrl", equalTo("http://localhost:8080/v1/payments/checkout"));
  }

  // 결제 승인 테스트 작성 (대여만)
  @Test
  @DisplayName("결제승인 요청시 PG사 결제 승인과 대여내역을 저장하고, 대여내역 토큰을 응답한다.")
  void confirmPayment() {
    //given
    Users user = Users.builder().providerType(ProviderType.LOCAL).role(Role.USER).build();
    userRepository.save(user);
    RentalItemTypes rentalItemTypes = RentalItemTypes.builder()
                                                     .category(RentalItemCategory.CHARGER)
                                                     .price(1000)
                                                     .name("65W 충전기")
                                                     .build();
    rentalItemTypeRepository.save(rentalItemTypes);
    RentalStations rentalStations = RentalStations.builder()
                                                  .name("강남역")
                                                  .grade(StationGrade.FIRST)
                                                  .status(StationStatus.OPEN)
                                                  .build();
    rentalStationRepository.save(rentalStations);
    RentalItems rentalItems = RentalItems.builder()
                                         .rentalItemType(rentalItemTypes)
                                         .currentStation(rentalStations)
                                         .status(RentalItemStatus.AVAILABLE)
                                         .build();
    rentalItemRepository.save(rentalItems);
    RentalStationItems rentalStationItems = new RentalStationItems(rentalItemTypes, rentalStations, 10);
    rentalStationItemRepository.save(rentalStationItems);

    GenerateToken generateToken = jwtProvider.generateToken(user.getToken(), user.getRole().getRoleKey());
    PaymentConfirmRequest confirmRequest = new PaymentConfirmRequest("paymentKey", 1000, "orderId");
    TossPaymentConfirmResponse confirmResponse = new TossPaymentConfirmResponse("paymentKey", PaymentMethod.CARD,
        "orderId", "65W 충전기/1시간", 1000, LocalDateTime.now(),
        new ReceiptInfo("receiptUrl"));
    orderInfoService.saveOrderInfo("orderId", rentalItems.getToken(), 1, 1000, PaymentType.RENT);
    given(tossPaymentApiClient.confirmPaymentRequest(confirmRequest)).willReturn(confirmResponse);

    //when
    given().log().all()
           .contentType(ContentType.JSON)
           .header(HttpHeaders.AUTHORIZATION, "Bearer " + generateToken.accessToken())
           .body(confirmRequest)
           .when()
           .post("/v1/payments/confirm")
           .then().log().all()
           .assertThat()
           .statusCode(HttpStatus.OK.value())
           .body("data.rentalHistoryToken", startsWith("RH_"));
  }

}