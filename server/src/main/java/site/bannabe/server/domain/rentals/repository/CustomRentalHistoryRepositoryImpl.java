package site.bannabe.server.domain.rentals.repository;

import static site.bannabe.server.domain.rentals.entity.QRentalHistory.rentalHistory;
import static site.bannabe.server.domain.rentals.entity.QRentalItemTypes.rentalItemTypes;
import static site.bannabe.server.domain.rentals.entity.QRentalItems.rentalItems;
import static site.bannabe.server.domain.rentals.entity.RentalStatus.OVERDUE;
import static site.bannabe.server.domain.rentals.entity.RentalStatus.RENTAL;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import site.bannabe.server.domain.rentals.entity.RentalHistory;

@Repository
@RequiredArgsConstructor
public class CustomRentalHistoryRepositoryImpl implements CustomRentalHistoryRepository {

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public List<RentalHistory> findActiveRentalsBy(String email) {
    return jpaQueryFactory.selectFrom(rentalHistory)
                          .join(rentalHistory.user)
                          .leftJoin(rentalHistory.rentalItem, rentalItems).fetchJoin()
                          .leftJoin(rentalItems.rentalItemType, rentalItemTypes).fetchJoin()
                          .where(
                              rentalHistory.user.email.eq(email)
                                                      .and(rentalHistory.status.in(RENTAL, OVERDUE))
                          )
                          .orderBy(rentalHistory.startTime.desc())
                          .fetch();
  }
}