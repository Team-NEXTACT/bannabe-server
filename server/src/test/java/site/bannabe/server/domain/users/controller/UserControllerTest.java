package site.bannabe.server.domain.users.controller;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import io.restassured.RestAssured;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import site.bannabe.server.config.AbstractTestContainers;
import site.bannabe.server.config.IntegrationTest;
import site.bannabe.server.domain.rentals.entity.RentalHistory;
import site.bannabe.server.domain.rentals.entity.RentalItemCategory;
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
import site.bannabe.server.domain.users.controller.request.UserChangeNicknameRequest;
import site.bannabe.server.domain.users.controller.request.UserChangePasswordRequest;
import site.bannabe.server.domain.users.controller.request.UserChangeProfileImageRequest;
import site.bannabe.server.domain.users.controller.response.S3PreSignedUrlResponse;
import site.bannabe.server.domain.users.controller.response.UserGetActiveRentalResponse;
import site.bannabe.server.domain.users.controller.response.UserGetSimpleResponse;
import site.bannabe.server.domain.users.entity.BookmarkStations;
import site.bannabe.server.domain.users.entity.ProviderType;
import site.bannabe.server.domain.users.entity.Role;
import site.bannabe.server.domain.users.entity.Users;
import site.bannabe.server.domain.users.repository.BookmarkStationRepository;
import site.bannabe.server.domain.users.repository.UserRepository;
import site.bannabe.server.global.aws.S3Service;
import site.bannabe.server.global.jwt.GenerateToken;
import site.bannabe.server.global.jwt.JwtProvider;

@IntegrationTest
class UserControllerTest extends AbstractTestContainers {

  @LocalServerPort
  private int port;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private RentalHistoryRepository rentalHistoryRepository;
  @Autowired
  private RentalItemRepository rentalItemRepository;
  @Autowired
  private RentalItemTypeRepository rentalItemTypeRepository;
  @Autowired
  private RentalStationRepository rentalStationRepository;
  @Autowired
  private BookmarkStationRepository bookmarkStationRepository;
  @Autowired
  private BCryptPasswordEncoder passwordEncoder;
  @Autowired
  private JwtProvider jwtProvider;
  @MockitoBean
  private S3Service s3Service;

  private Users user;
  private String accessToken;

  @BeforeEach
  void setup() {
    RestAssured.port = port;
    user = Users.builder()
                .email("test@test.com")
                .nickname("TestUser")
                .profileImage("https://test.com/test.jpg")
                .password(passwordEncoder.encode("password"))
                .role(Role.USER)
                .providerType(ProviderType.LOCAL)
                .build();
    userRepository.saveAndFlush(user);
    GenerateToken generateToken = jwtProvider.generateToken(user.getToken(), user.getRole().getRoleKey());
    accessToken = generateToken.accessToken();
  }

  @AfterEach
  void tearDown() {
    bookmarkStationRepository.deleteAllInBatch();
    rentalStationRepository.deleteAllInBatch();
    rentalHistoryRepository.deleteAllInBatch();
    rentalItemRepository.deleteAllInBatch();
    rentalItemTypeRepository.deleteAllInBatch();
    userRepository.deleteAllInBatch();
  }

  @Test
  @DisplayName("회원 정보 조회(Me)")
  void getUserInfo() {
    //given when
    UserGetSimpleResponse result = given().log().all()
                                          .contentType(JSON)
                                          .header(AUTHORIZATION, "Bearer " + accessToken)
                                          .when()
                                          .get("/v1/users/me")
                                          .then().log().all()
                                          .assertThat()
                                          .statusCode(HttpStatus.OK.value())
                                          .extract()
                                          .response().jsonPath().getObject("data", UserGetSimpleResponse.class);

    //then
    assertThat(result).isNotNull()
                      .extracting(
                          UserGetSimpleResponse::email,
                          UserGetSimpleResponse::nickname,
                          UserGetSimpleResponse::profileImage
                      ).containsExactly(
                          user.getEmail(),
                          user.getNickname(),
                          user.getProfileImage()
                      );
  }

  @Test
  @DisplayName("비밀번호 변경 성공시 200 응답")
  void changePassword() {
    //given
    UserChangePasswordRequest requestBody = new UserChangePasswordRequest("password", "newPassword", "newPassword");

    //when then
    given().log().all()
           .contentType(JSON)
           .header(AUTHORIZATION, "Bearer " + accessToken)
           .body(requestBody)
           .when()
           .put("/v1/users/me/password")
           .then().log().all()
           .statusCode(HttpStatus.OK.value());
  }

  @Test
  @DisplayName("비밀번호 변경 실패시 409 응답")
  void mismatchPassword() {
    //given
    UserChangePasswordRequest requestBody = new UserChangePasswordRequest("password", "newPassword", "newPasswordConfirm");

    //when then
    given().log().all()
           .contentType(JSON)
           .header(AUTHORIZATION, "Bearer " + accessToken)
           .body(requestBody)
           .when()
           .put("/v1/users/me/password")
           .then().log().all()
           .statusCode(HttpStatus.CONFLICT.value());
  }

  @Test
  @DisplayName("닉네임 변경 성공시 200 응답")
  void changeNickname() {
    //given
    UserChangeNicknameRequest requestBody = new UserChangeNicknameRequest("newNickname");

    //when then
    given().log().all()
           .contentType(JSON)
           .header(AUTHORIZATION, "Bearer " + accessToken)
           .body(requestBody)
           .when()
           .patch("/v1/users/me/nickname")
           .then().log().all()
           .statusCode(HttpStatus.OK.value());
  }

  @Test
  @DisplayName("닉네임 중복시 409 응답")
  void duplicateNickname() {
    //given
    UserChangeNicknameRequest requestBody = new UserChangeNicknameRequest("TestUser");

    //when then
    given().log().all()
           .contentType(JSON)
           .header(AUTHORIZATION, "Bearer " + accessToken)
           .body(requestBody)
           .when()
           .patch("/v1/users/me/nickname")
           .then().log().all()
           .statusCode(HttpStatus.CONFLICT.value());
  }

  @Test
  @DisplayName("프로필 이미지 변경 성공시 200 응답")
  void changeProfileImage() {
    //given
    UserChangeProfileImageRequest requestBody = new UserChangeProfileImageRequest("newProfileImage.png");

    willDoNothing().given(s3Service).removeProfileImage(user.getProfileImage());

    //when then
    given().log().all()
           .contentType(JSON)
           .header(AUTHORIZATION, "Bearer " + accessToken)
           .body(requestBody)
           .when()
           .patch("/v1/users/me/profile-image")
           .then().log().all()
           .statusCode(HttpStatus.OK.value());
  }

  @Test
  @DisplayName("S3 preSignedUrl 조회")
  void getPreSignedUrl() {
    //given
    String preSignedUrl = "https://test.com/test.jpg";

    BDDMockito.given(s3Service.getPreSignedUrl(anyString())).willReturn(preSignedUrl);

    //when
    S3PreSignedUrlResponse result = given().log().all()
                                           .contentType(JSON)
                                           .header(AUTHORIZATION, "Bearer " + accessToken)
                                           .param("extension", "jpg")
                                           .when()
                                           .get("/v1/users/me/profile-image/pre-signed")
                                           .then().log().all()
                                           .assertThat()
                                           .statusCode(HttpStatus.OK.value())
                                           .extract()
                                           .body()
                                           .jsonPath().getObject("data", S3PreSignedUrlResponse.class);
    //then
    assertThat(result.preSignedUrl()).isEqualTo(preSignedUrl);
  }

  @Test
  @DisplayName("대여 현황 조회")
  void getActiveRentalHistory() {
    //given
    initRentalHistory();

    //when
    UserGetActiveRentalResponse result = given().log().all()
                                                .contentType(JSON)
                                                .header(AUTHORIZATION, "Bearer " + accessToken)
                                                .when()
                                                .get("/v1/users/me/rentals/active")
                                                .then().log().all()
                                                .assertThat()
                                                .statusCode(HttpStatus.OK.value())
                                                .extract().jsonPath().getObject("data", UserGetActiveRentalResponse.class);

    //then
    assertThat(result).isNotNull();
    assertThat(result.rentals()).hasSize(2);
  }

  @Test
  @DisplayName("대여내역 조회")
  void getAllRentalHistory() {
    //given
    initRentalHistory();

    //when then
    given().log().all()
           .contentType(JSON)
           .header(AUTHORIZATION, "Bearer " + accessToken)
           .when()
           .get("/v1/users/me/rentals")
           .then().log().all()
           .assertThat()
           .statusCode(HttpStatus.OK.value())
           .body("data.content", hasSize(10))
           .body("data.page.size", equalTo(10))
           .body("data.page.totalElements", equalTo(20))
           .body("data.page.totalPages", equalTo(2));
  }

  @Test
  @DisplayName("북마크 스테이션 조회")
  void getBookmarkStations() {
    //given
    List<RentalStations> stations = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      stations.add(
          RentalStations.builder()
                        .name("테스트 스테이션" + i)
                        .address("테스트 주소" + i)
                        .grade(StationGrade.FIRST)
                        .status(StationStatus.OPEN)
                        .build()
      );
    }
    rentalStationRepository.saveAllAndFlush(stations);
    List<BookmarkStations> list = stations.stream().map(station -> new BookmarkStations(user, station)).toList();
    bookmarkStationRepository.saveAllAndFlush(list);

    //when then
    given().log().all()
           .contentType(JSON)
           .header(AUTHORIZATION, "Bearer " + accessToken)
           .when()
           .get("/v1/users/me/stations/bookmark")
           .then().log().all()
           .assertThat()
           .statusCode(HttpStatus.OK.value())
           .body("data.bookmarks", hasSize(10));
  }

  // 북마크 스테이션 삭제
  @Test
  @DisplayName("북마크 스테이션 삭제 성공시 200 응답")
  void removeBookmarkStation() {
    //given
    RentalStations rentalStations = RentalStations.builder().status(StationStatus.OPEN).grade(StationGrade.FIRST).build();
    rentalStationRepository.saveAndFlush(rentalStations);

    BookmarkStations bookmarkStations = new BookmarkStations(user, rentalStations);
    bookmarkStationRepository.saveAndFlush(bookmarkStations);

    Long bookmarkId = bookmarkStations.getId();

    //when then
    given().log().all()
           .contentType(JSON)
           .header(AUTHORIZATION, "Bearer " + accessToken)
           .when()
           .delete("/v1/users/me/stations/bookmark/{bookmarkId}", bookmarkId)
           .then().log().all()
           .assertThat()
           .statusCode(HttpStatus.OK.value());
  }

  // 삭제 대상 북마크 미존재시 404 응답
  @Test
  @DisplayName("북마크 삭제대상 미 존재시 404 응답")
  void notExistTargetBookmark() {
    //given
    Long bookmarkId = 100L;

    //when then
    given().log().all()
           .contentType(JSON)
           .header(AUTHORIZATION, "Bearer " + accessToken)
           .when()
           .delete("/v1/users/me/stations/bookmark/{bookmarkId}", bookmarkId)
           .then().log().all()
           .assertThat()
           .statusCode(HttpStatus.NOT_FOUND.value());
  }


  private void initRentalHistory() {
    RentalItemTypes rentalItemTypes = RentalItemTypes.builder()
                                                     .name("65W충전기")
                                                     .category(RentalItemCategory.CHARGER)
                                                     .price(1000)
                                                     .build();
    rentalItemTypeRepository.saveAndFlush(rentalItemTypes);

    RentalItems rentalItems = RentalItems.builder().rentalItemType(rentalItemTypes).build();
    rentalItemRepository.saveAndFlush(rentalItems);

    LocalDateTime now = LocalDateTime.now();
    List<RentalHistory> rentalHistories = new ArrayList<>();
    for (int i = 0; i < 2; i++) {
      rentalHistories.add(
          RentalHistory.builder()
                       .rentalItem(rentalItems)
                       .user(user)
                       .startTime(now.minusHours(1))
                       .rentalTimeHour(3)
                       .expectedReturnTime(now.plusHours(2))
                       .status(RentalStatus.RENTAL)
                       .build()
      );
    }

    for (int i = 0; i < 18; i++) {
      rentalHistories.add(
          RentalHistory.builder()
                       .rentalItem(rentalItems)
                       .user(user)
                       .startTime(now.minusDays(i + 1))
                       .rentalTimeHour(1)
                       .expectedReturnTime(now.minusDays(i + 1).plusHours(1))
                       .status(RentalStatus.RETURNED)
                       .build()
      );
    }

    rentalHistoryRepository.saveAllAndFlush(rentalHistories);
  }

}