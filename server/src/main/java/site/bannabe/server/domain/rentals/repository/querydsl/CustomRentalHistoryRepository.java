package site.bannabe.server.domain.rentals.repository.querydsl;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import site.bannabe.server.domain.rentals.controller.response.RentalSuccessSimpleResponse;
import site.bannabe.server.domain.rentals.entity.RentalHistory;

public interface CustomRentalHistoryRepository {

  List<RentalHistory> findActiveRentalsBy(String email);

  Page<RentalHistory> findAllRentalsBy(String email, Pageable pageable);

  RentalSuccessSimpleResponse findRentalHistoryInfoBy(String token);

}