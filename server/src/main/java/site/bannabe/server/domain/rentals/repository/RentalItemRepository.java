package site.bannabe.server.domain.rentals.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.bannabe.server.domain.rentals.entity.RentalItemTypes;
import site.bannabe.server.domain.rentals.repository.querydsl.CustomRentalItemRepository;

public interface RentalItemRepository extends JpaRepository<RentalItemTypes, Long>, CustomRentalItemRepository {

}