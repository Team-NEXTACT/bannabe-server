package site.bannabe.server.domain.rentals.repository.querydsl;

import java.util.Optional;
import site.bannabe.server.domain.rentals.entity.RentalItems;

public interface CustomRentalItemRepository {

  Optional<RentalItems> findByToken(String token);

}