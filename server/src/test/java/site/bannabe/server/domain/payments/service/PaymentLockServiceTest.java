package site.bannabe.server.domain.payments.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import site.bannabe.server.config.AbstractTestContainers;
import site.bannabe.server.domain.rentals.entity.RentalItemCategory;
import site.bannabe.server.domain.rentals.entity.RentalItemTypes;
import site.bannabe.server.domain.rentals.entity.RentalItems;
import site.bannabe.server.domain.rentals.entity.RentalStationItems;
import site.bannabe.server.domain.rentals.entity.RentalStations;
import site.bannabe.server.domain.rentals.entity.StationGrade;
import site.bannabe.server.domain.rentals.entity.StationStatus;
import site.bannabe.server.domain.rentals.repository.RentalItemRepository;
import site.bannabe.server.domain.rentals.repository.RentalItemTypeRepository;
import site.bannabe.server.domain.rentals.repository.RentalStationItemRepository;
import site.bannabe.server.domain.rentals.repository.RentalStationRepository;

@Slf4j
@SpringBootTest
class PaymentLockServiceTest extends AbstractTestContainers {

  private static final String TOKEN = "token";

  @Autowired
  private PaymentLockService paymentLockService;

  @Autowired
  private RentalStockService rentalStockService;

  @Autowired
  private RentalStationItemRepository rentalStationItemRepository;

  @Autowired
  private RentalItemRepository rentalItemRepository;

  @Autowired
  private RentalItemTypeRepository rentalItemTypeRepository;

  @Autowired
  private RentalStationRepository rentalStationRepository;

  private RentalItems rentalItems;
  private RentalStationItems rentalStationItems;
  private RentalItemTypes rentalItemTypes;
  private RentalStations rentalStations;

  @Test
  @DisplayName("재고 감소 분산락 적용 테스트")
  void decreaseStockWithDistributedLock() throws InterruptedException {
    //given
    int threadSize = 100;
    ExecutorService executorService = Executors.newFixedThreadPool(threadSize);
    CountDownLatch latch = new CountDownLatch(threadSize);
    //when
    for (int i = 0; i < threadSize; i++) {
      executorService.execute(() -> {
        try {
          paymentLockService.decreaseStock(rentalItems);
        } finally {
          latch.countDown();
        }
      });
    }

    latch.await();
    //then
    RentalStationItems rentalStationItems = rentalStationItemRepository.findByItemTypeAndStation(rentalItems.getRentalItemType(),
        rentalItems.getCurrentStation());

    assertThat(rentalStationItems.getStock()).isZero();
  }

  @Test
  @DisplayName("재고 감소 분산락 미적용 테스트")
  void decreaseStockWithOutDistributedLock() throws InterruptedException {
    //given
    int threadSize = 100;
    ExecutorService executorService = Executors.newFixedThreadPool(threadSize);
    CountDownLatch latch = new CountDownLatch(threadSize);
    //when
    for (int i = 0; i < threadSize; i++) {
      executorService.execute(() -> {
        try {
          rentalStockService.decreaseStock(rentalItems);
        } finally {
          latch.countDown();
        }
      });
    }

    latch.await();
    //then
    RentalStationItems rentalStationItems = rentalStationItemRepository.findByItemTypeAndStation(rentalItems.getRentalItemType(),
        rentalItems.getCurrentStation());

    assertThat(rentalStationItems.getStock()).isNotZero();
  }


  @BeforeEach
  void init() {
    rentalItemTypes = RentalItemTypes.builder()
                                     .category(RentalItemCategory.CHARGER)
                                     .name("Test Item Name")
                                     .build();
    rentalStations = RentalStations.builder()
                                   .name("Test Station Name")
                                   .status(StationStatus.OPEN)
                                   .grade(StationGrade.FIRST)
                                   .build();
    rentalItemTypeRepository.saveAndFlush(rentalItemTypes);
    rentalStationRepository.saveAndFlush(rentalStations);

    rentalItems = RentalItems.builder()
                             .token(TOKEN)
                             .rentalItemType(rentalItemTypes)
                             .currentStation(rentalStations)
                             .build();

    rentalStationItems = new RentalStationItems(rentalItemTypes, rentalStations, 100);
    rentalItemRepository.saveAndFlush(rentalItems);
    rentalStationItemRepository.saveAndFlush(rentalStationItems);
  }

  @AfterEach
  void tearDown() {
    rentalStationItemRepository.deleteAllInBatch();
    rentalItemRepository.deleteAllInBatch();
    rentalItemTypeRepository.deleteAllInBatch();
    rentalStationRepository.deleteAllInBatch();
  }

}