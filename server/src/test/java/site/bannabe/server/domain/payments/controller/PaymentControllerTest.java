package site.bannabe.server.domain.payments.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import site.bannabe.server.config.AbstractIntegrationTest;
import site.bannabe.server.domain.payments.controller.request.PaymentCalculateRequest;
import site.bannabe.server.domain.rentals.entity.RentalItemCategory;
import site.bannabe.server.domain.rentals.entity.RentalItemStatus;
import site.bannabe.server.domain.rentals.entity.RentalItemTypes;
import site.bannabe.server.domain.rentals.entity.RentalItems;
import site.bannabe.server.domain.rentals.repository.RentalItemRepository;
import site.bannabe.server.domain.rentals.repository.RentalItemTypeRepository;
import site.bannabe.server.global.jwt.GenerateToken;
import site.bannabe.server.global.jwt.JwtProvider;

class PaymentControllerTest extends AbstractIntegrationTest {

  @Autowired
  private RentalItemRepository rentalItemRepository;

  @Autowired
  private RentalItemTypeRepository rentalItemTypeRepository;

  @Autowired
  private JwtProvider jwtProvider;

  @AfterEach
  void tearDown() {
    rentalItemRepository.deleteAllInBatch();
    rentalItemTypeRepository.deleteAllInBatch();
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

  // 결제 승인 테스트 필요
}