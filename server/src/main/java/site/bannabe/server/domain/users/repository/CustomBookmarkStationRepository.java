package site.bannabe.server.domain.users.repository;

import java.util.List;
import site.bannabe.server.domain.users.controller.response.UserBookmarkStationsResponse.BookmarkStationResponse;

public interface CustomBookmarkStationRepository {

  List<BookmarkStationResponse> findBookmarkStationsBy(String email);

  Boolean existsBookmarkByEmail(String email, Long bookmarkId);

}