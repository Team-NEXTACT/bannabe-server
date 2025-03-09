package site.bannabe.server.domain.users.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import static site.bannabe.server.domain.rentals.entity.QRentalStations.rentalStations;
import site.bannabe.server.domain.users.controller.response.UserBookmarkStationsResponse.BookmarkStationResponse;
import static site.bannabe.server.domain.users.entity.QBookmarkStations.bookmarkStations;
import static site.bannabe.server.domain.users.entity.QUsers.users;

@Repository
@RequiredArgsConstructor
public class CustomBookmarkStationRepositoryImpl implements CustomBookmarkStationRepository {

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public List<BookmarkStationResponse> findBookmarkStationsBy(String email) {
    return jpaQueryFactory.select(
                              Projections.constructor(
                                  BookmarkStationResponse.class,
                                  rentalStations.name,
                                  rentalStations.id,
                                  bookmarkStations.id
                              ))
                          .from(bookmarkStations)
                          .join(bookmarkStations.user, users)
                          .join(bookmarkStations.rentalStation, rentalStations)
                          .where(users.email.eq(email))
                          .fetch();
  }

  @Override
  public Boolean existsBookmarkByEmail(String email, Long bookmarkId) {
    Integer findBookmark = jpaQueryFactory.selectOne()
                                          .from(bookmarkStations)
                                          .join(bookmarkStations.user, users)
                                          .where(bookmarkStations.id.eq(bookmarkId)
                                                                    .and(bookmarkStations.user.email.eq(email)))
                                          .fetchFirst();
    return findBookmark != null;
  }

}