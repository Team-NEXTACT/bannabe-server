package site.bannabe.server.domain.rentals.repository;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import site.bannabe.server.config.AbstractTestContainers;
import site.bannabe.server.config.CustomDataJpaTest;
import site.bannabe.server.domain.rentals.entity.RentalItemCategory;
import site.bannabe.server.domain.rentals.entity.RentalItemTypes;
import site.bannabe.server.domain.rentals.entity.RentalItems;
import site.bannabe.server.domain.rentals.entity.RentalStations;
import site.bannabe.server.domain.rentals.entity.StationGrade;
import site.bannabe.server.domain.rentals.entity.StationStatus;
import site.bannabe.server.global.utils.RandomCodeGenerator;

@CustomDataJpaTest
class RentalItemRepositoryTest extends AbstractTestContainers {

  @Autowired
  private RentalItemRepository rentalItemRepository;

  @Autowired
  private EntityManager em;

  @Test
  @DisplayName("Token을 식별값으로 RentalItem을 조회한다.")
  void findRentalItemByTokenTest() {
    //given
    RentalStations rentalStation = RentalStations.builder()
                                                 .name("테스트 대여소")
                                                 .address("서울시 강남구")
                                                 .latitude(BigDecimal.valueOf(127.234516))
                                                 .longitude(BigDecimal.valueOf(36.234523))
                                                 .openTime("09:00")
                                                 .closeTime("18:00")
                                                 .closeDay("매주 일요일")
                                                 .status(StationStatus.OPEN)
                                                 .grade(StationGrade.SECOND)
                                                 .build();
    RentalItemTypes rentalItemTypes = RentalItemTypes.builder()
                                                     .name("65W 충전기")
                                                     .image("test.png")
                                                     .category(RentalItemCategory.CHARGER)
                                                     .description("PD 충전, C타입")
                                                     .price(1000)
                                                     .build();
    em.persist(rentalStation);
    em.persist(rentalItemTypes);
    em.flush();

    RentalItems rentalItems = RentalItems.builder()
                                         .token(RandomCodeGenerator.generateRandomToken(RentalItems.class))
                                         .rentalItemType(rentalItemTypes)
                                         .currentStation(rentalStation)
                                         .build();
    String token = rentalItems.getToken();

    em.persist(rentalItems);
    em.flush();
    em.clear();

    //when
    RentalItems result = rentalItemRepository.findByToken(token).orElse(null);

    //then
    assertThat(result).isNotNull()
                      .extracting(
                          RentalItems::getToken,
                          RentalItems::getStatus
                      )
                      .containsExactly(
                          rentalItems.getToken(),
                          rentalItems.getStatus()
                      );

    assertThat(result.getRentalItemType()).isNotNull()
                                          .extracting(
                                              RentalItemTypes::getName,
                                              RentalItemTypes::getImage,
                                              RentalItemTypes::getCategory,
                                              RentalItemTypes::getDescription,
                                              RentalItemTypes::getPrice
                                          )
                                          .containsExactly(
                                              rentalItemTypes.getName(),
                                              rentalItemTypes.getImage(),
                                              rentalItemTypes.getCategory(),
                                              rentalItemTypes.getDescription(),
                                              rentalItemTypes.getPrice()
                                          );

    assertThat(result.getCurrentStation()).isNotNull()
                                          .extracting(
                                              RentalStations::getName,
                                              RentalStations::getAddress,
                                              RentalStations::getLatitude,
                                              RentalStations::getLongitude,
                                              RentalStations::getOpenTime,
                                              RentalStations::getCloseTime,
                                              RentalStations::getCloseDay,
                                              RentalStations::getStatus,
                                              RentalStations::getGrade
                                          )
                                          .containsExactly(
                                              rentalStation.getName(),
                                              rentalStation.getAddress(),
                                              rentalStation.getLatitude(),
                                              rentalStation.getLongitude(),
                                              rentalStation.getOpenTime(),
                                              rentalStation.getCloseTime(),
                                              rentalStation.getCloseDay(),
                                              rentalStation.getStatus(),
                                              rentalStation.getGrade()
                                          );
  }
}