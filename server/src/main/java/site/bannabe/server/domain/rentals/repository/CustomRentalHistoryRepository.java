package site.bannabe.server.domain.rentals.repository;

import java.util.List;
import site.bannabe.server.domain.rentals.entity.RentalHistory;

public interface CustomRentalHistoryRepository {

  List<RentalHistory> findActiveRentalsBy(String email);

}