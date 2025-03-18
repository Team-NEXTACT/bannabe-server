package site.bannabe.server.domain.rentals.service;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.bannabe.server.domain.rentals.entity.RentalHistory;
import site.bannabe.server.domain.rentals.entity.RentalStatus;
import site.bannabe.server.domain.rentals.repository.RentalHistoryRepository;

@Service
@RequiredArgsConstructor
public class ReturnService {

  private final RentalHistoryRepository rentalHistoryRepository;


  @Transactional(readOnly = true)
  public void getReturnItemInfo(String rentalItemToken) {
    RentalHistory rentalHistory = rentalHistoryRepository.findByItemToken(rentalItemToken);
    // 현재시간 보다 expectedReturnTime이 빠르면 OVERDUE로 변경
    if (rentalHistory.getExpectedReturnTime().isBefore(LocalDateTime.now())) {
      rentalHistory.changeStatus(RentalStatus.OVERDUE);
    }
    // 여기서 OVERDUE라면 변경하고 예외터트려서 클라이언트에서 연체 유도하도록 처리
    // RETURNED라면 이미 반납한거지 예외 터트림
    // 그 외에는 DTO로 변환해서 응답
  }

}