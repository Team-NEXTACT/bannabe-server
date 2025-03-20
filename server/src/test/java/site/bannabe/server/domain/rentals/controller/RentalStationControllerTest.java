package site.bannabe.server.domain.rentals.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import site.bannabe.server.config.AbstractIntegrationTest;
import site.bannabe.server.domain.rentals.controller.response.RentalItemTypeDetailResponse;
import site.bannabe.server.domain.rentals.controller.response.RentalStationSimpleResponse;
import site.bannabe.server.domain.rentals.entity.RentalItemCategory;
import site.bannabe.server.domain.rentals.entity.RentalItemTypes;
import site.bannabe.server.domain.rentals.entity.RentalStationItems;
import site.bannabe.server.domain.rentals.entity.RentalStations;
import site.bannabe.server.domain.rentals.entity.StationGrade;
import site.bannabe.server.domain.rentals.entity.StationStatus;
import site.bannabe.server.domain.rentals.repository.RentalItemTypeRepository;
import site.bannabe.server.domain.rentals.repository.RentalStationItemRepository;
import site.bannabe.server.domain.rentals.repository.RentalStationRepository;
import site.bannabe.server.domain.users.entity.BookmarkStations;
import site.bannabe.server.domain.users.entity.ProviderType;
import site.bannabe.server.domain.users.entity.Role;
import site.bannabe.server.domain.users.entity.Users;
import site.bannabe.server.domain.users.repository.BookmarkStationRepository;
import site.bannabe.server.domain.users.repository.UserRepository;
import site.bannabe.server.global.jwt.GenerateToken;
import site.bannabe.server.global.jwt.JwtProvider;

class RentalStationControllerTest extends AbstractIntegrationTest {

  @Autowired
  private RentalStationRepository rentalStationRepository;
  @Autowired
  private RentalItemTypeRepository rentalItemTypeRepository;
  @Autowired
  private RentalStationItemRepository rentalStationItemRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private BookmarkStationRepository bookmarkStationRepository;
  @Autowired
  private JwtProvider jwtProvider;

  @AfterEach
  void tearDown() {
    bookmarkStationRepository.deleteAllInBatch();
    userRepository.deleteAllInBatch();
    rentalStationItemRepository.deleteAllInBatch();
    rentalItemTypeRepository.deleteAllInBatch();
    rentalStationRepository.deleteAllInBatch();
  }

  @Test
  @DisplayName("전체 대여소 정보를 조회한다.")
  void getAllRentalStation() {
    //given
    List<RentalStations> stations = new ArrayList<>();
    for (int i = 0; i < 20; i++) {
      RentalStations station = RentalStations.builder()
                                             .name("테스트 스테이션" + i)
                                             .address("서울시 강남구 테스트로 123")
                                             .latitude(BigDecimal.valueOf(36.123456))
                                             .longitude(BigDecimal.valueOf(127.123456))
                                             .status(StationStatus.OPEN)
                                             .grade(StationGrade.FIRST)
                                             .openTime("10:00")
                                             .closeTime("23:00")
                                             .build();
      stations.add(station);
    }
    rentalStationRepository.saveAllAndFlush(stations);

    //when
    RentalStationSimpleResponse result = RestAssured.given().log().all()
                                                    .contentType(ContentType.JSON)
                                                    .when()
                                                    .get("/v1/stations")
                                                    .then().log().all()
                                                    .assertThat()
                                                    .statusCode(HttpStatus.OK.value())
                                                    .extract().jsonPath().getObject("data", RentalStationSimpleResponse.class);

    //then
    assertThat(result).isNotNull();
    assertThat(result.stations()).hasSize(20);
  }

  @Test
  @DisplayName("stationId 입력시 대여소 상세정보를 조회한다.")
  void getRentalStationDetail() {
    //given
    RentalItemTypes itemType1 = RentalItemTypes.builder()
                                               .category(RentalItemCategory.CHARGER)
                                               .name("65W 충전기")
                                               .image("test-image.png")
                                               .build();

    RentalItemTypes itemType2 = RentalItemTypes.builder()
                                               .category(RentalItemCategory.CHARGER)
                                               .name("100W 충전기")
                                               .image("test-image.png")
                                               .build();
    rentalItemTypeRepository.saveAllAndFlush(List.of(itemType1, itemType2));

    RentalStations station = RentalStations.builder()
                                           .name("테스트 스테이션")
                                           .address("서울시 강남구 테스트로 123")
                                           .latitude(BigDecimal.valueOf(36.123456))
                                           .longitude(BigDecimal.valueOf(127.123456))
                                           .status(StationStatus.OPEN)
                                           .grade(StationGrade.FIRST)
                                           .openTime("10:00")
                                           .closeTime("23:00")
                                           .build();
    rentalStationRepository.saveAndFlush(station);

    RentalStationItems stationItem1 = new RentalStationItems(itemType1, station, 10);
    RentalStationItems stationItem2 = new RentalStationItems(itemType2, station, 10);

    rentalStationItemRepository.saveAllAndFlush(List.of(stationItem1, stationItem2));

    //when then
    RestAssured.given().log().all()
               .contentType(ContentType.JSON)
               .when()
               .get("/v1/stations/{stationId}", station.getId())
               .then().log().all()
               .assertThat()
               .statusCode(HttpStatus.OK.value())
               .body("data.rentalItems", hasSize(2));
  }

  @Test
  @DisplayName("stationId와 itemTypeId를 통해 물품 상세정보를 조회한다.")
  void getRentalItemDetail() {
    //given
    RentalItemTypes itemType = RentalItemTypes.builder()
                                              .category(RentalItemCategory.CHARGER)
                                              .name("65W 충전기")
                                              .image("test-image.png")
                                              .build();
    rentalItemTypeRepository.saveAndFlush(itemType);
    RentalStations station = RentalStations.builder()
                                           .name("테스트 스테이션")
                                           .address("서울시 강남구 테스트로 123")
                                           .latitude(BigDecimal.valueOf(36.123456))
                                           .longitude(BigDecimal.valueOf(127.123456))
                                           .status(StationStatus.OPEN)
                                           .grade(StationGrade.FIRST)
                                           .openTime("10:00")
                                           .closeTime("23:00")
                                           .build();
    rentalStationRepository.saveAndFlush(station);

    RentalStationItems rentalStationItems = new RentalStationItems(itemType, station, 10);
    rentalStationItemRepository.saveAndFlush(rentalStationItems);

    //when
    RentalItemTypeDetailResponse result = RestAssured.given().log().all()
                                                     .contentType(ContentType.JSON)
                                                     .when()
                                                     .get("/v1/stations/{stationId}/items/{itemTypeId}", station.getId(),
                                                         itemType.getId())
                                                     .then().log().all()
                                                     .assertThat()
                                                     .statusCode(HttpStatus.OK.value())
                                                     .extract().jsonPath().getObject("data", RentalItemTypeDetailResponse.class);

    //then
    assertThat(result).isNotNull()
                      .extracting(
                          RentalItemTypeDetailResponse::name,
                          RentalItemTypeDetailResponse::image,
                          RentalItemTypeDetailResponse::category,
                          RentalItemTypeDetailResponse::description,
                          RentalItemTypeDetailResponse::price,
                          RentalItemTypeDetailResponse::stock
                      ).containsExactly(
                          itemType.getName(),
                          itemType.getImage(),
                          itemType.getCategory().name(),
                          itemType.getDescription(),
                          itemType.getPrice(),
                          rentalStationItems.getStock()
                      );
  }

  @Test
  @DisplayName("stationId를 통해 북마크 스테이션을 추가한다.")
  void bookmarkRentalStation() {
    //given
    RentalStations station = RentalStations.builder()
                                           .name("테스트 스테이션")
                                           .address("서울시 강남구 테스트로 123")
                                           .latitude(BigDecimal.valueOf(36.123456))
                                           .longitude(BigDecimal.valueOf(127.123456))
                                           .status(StationStatus.OPEN)
                                           .grade(StationGrade.FIRST)
                                           .openTime("10:00")
                                           .closeTime("23:00")
                                           .build();
    rentalStationRepository.saveAndFlush(station);

    Users user = Users.builder().providerType(ProviderType.LOCAL).role(Role.USER).build();
    userRepository.saveAndFlush(user);

    GenerateToken generateToken = jwtProvider.generateToken(user.getToken(), user.getRole().getRoleKey());

    //when then
    RestAssured.given().log().all()
               .contentType(ContentType.JSON)
               .header(HttpHeaders.AUTHORIZATION, "Bearer " + generateToken.accessToken())
               .when()
               .post("/v1/stations/{stationId}/bookmark", station.getId())
               .then().log().all()
               .assertThat()
               .statusCode(HttpStatus.OK.value());
  }

  @Test
  @DisplayName("이미 북마크한 스테이션 이라면 409 응답")
  void duplicateBookmark() {
    //given
    RentalStations station = RentalStations.builder()
                                           .name("테스트 스테이션")
                                           .address("서울시 강남구 테스트로 123")
                                           .latitude(BigDecimal.valueOf(36.123456))
                                           .longitude(BigDecimal.valueOf(127.123456))
                                           .status(StationStatus.OPEN)
                                           .grade(StationGrade.FIRST)
                                           .openTime("10:00")
                                           .closeTime("23:00")
                                           .build();
    rentalStationRepository.saveAndFlush(station);

    Users user = Users.builder().providerType(ProviderType.LOCAL).role(Role.USER).build();
    userRepository.saveAndFlush(user);

    BookmarkStations bookmarkStations = new BookmarkStations(user, station);
    bookmarkStationRepository.saveAndFlush(bookmarkStations);
    GenerateToken generateToken = jwtProvider.generateToken(user.getToken(), user.getRole().getRoleKey());

    //when then
    RestAssured.given().log().all()
               .contentType(ContentType.JSON)
               .header(HttpHeaders.AUTHORIZATION, "Bearer " + generateToken.accessToken())
               .when()
               .post("/v1/stations/{stationId}/bookmark", station.getId())
               .then().log().all()
               .assertThat()
               .statusCode(HttpStatus.CONFLICT.value());

  }

}