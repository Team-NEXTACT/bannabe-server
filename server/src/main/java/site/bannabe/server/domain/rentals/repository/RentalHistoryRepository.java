package site.bannabe.server.domain.rentals.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.bannabe.server.domain.rentals.entity.RentalHistory;
import site.bannabe.server.domain.rentals.repository.querydsl.CustomRentalHistoryRepository;

public interface RentalHistoryRepository extends JpaRepository<RentalHistory, Long>, CustomRentalHistoryRepository {

}https://console.cloud.google.com/apis/agreements?inv=1&invt=Abryag&organizationId=0&project=bannabee-b15ef