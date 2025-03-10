package site.bannabe.server.domain.rentals.repository.querydsl;

import static site.bannabe.server.domain.rentals.entity.QRentalHistory.rentalHistory;
import static site.bannabe.server.domain.rentals.entity.QRentalItemTypes.rentalItemTypes;
import static site.bannabe.server.domain.rentals.entity.QRentalItems.rentalItems;
import static site.bannabe.server.domain.rentals.entity.RentalStatus.OVERDUE;
import static site.bannabe.server.domain.rentals.entity.RentalStatus.RENTAL;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
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

  @Override
  public Page<RentalHistory> findAllRentalsBy(String email, Pageable pageable) {
    List<RentalHistory> rentalHistories = jpaQueryFactory.selectFrom(rentalHistory)
                                                         .leftJoin(rentalHistory.rentalItem, rentalItems).fetchJoin()
                                                         .leftJoin(rentalItems.rentalItemType, rentalItemTypes).fetchJoin()
                                                         .where(rentalHistory.user.email.eq(email))
                                                         .orderBy(getOrderSpecifiers(pageable.getSort()))
                                                         .offset(pageable.getOffset())
                                                         .limit(pageable.getPageSize())
                                                         .fetch();
    JPAQuery<Long> queryCount = jpaQueryFactory.select(rentalHistory.count())
                                               .from(rentalHistory)
                                               .where(rentalHistory.user.email.eq(email));
    return PageableExecutionUtils.getPage(rentalHistories, pageable, queryCount::fetchOne);
  }

  @SuppressWarnings("unchecked")
  private OrderSpecifier<?>[] getOrderSpecifiers(Sort sort) {
    return sort.stream().map(order -> {
      Order direction = order.isAscending() ? Order.ASC : Order.DESC;
      String property = order.getProperty();
      return new OrderSpecifier(direction, Expressions.path(RentalHistory.class, rentalHistory, property));
    }).toArray(OrderSpecifier[]::new);
  }

}