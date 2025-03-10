package site.bannabe.server.domain.rentals.repository.querydsl;

import static site.bannabe.server.domain.rentals.entity.QRentalItems.rentalItems;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import site.bannabe.server.domain.rentals.entity.RentalItems;

@Repository
@RequiredArgsConstructor
public class CustomRentalItemRepositoryImpl implements CustomRentalItemRepository {

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public Optional<RentalItems> findByToken(String token) {
    RentalItems rentalItem = jpaQueryFactory.selectFrom(rentalItems)
                                            .join(rentalItems.rentalItemType).fetchJoin()
                                            .join(rentalItems.currentStation).fetchJoin()
                                            .where(rentalItems.token.eq(token))
                                            .fetchOne();
    return Optional.ofNullable(rentalItem);
  }

}