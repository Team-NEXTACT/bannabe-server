package site.bannabe.server.domain.users.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import site.bannabe.server.domain.users.entity.Users;

public interface UserRepository extends JpaRepository<Users, Long> {

  Boolean existsByEmail(String email);

  Optional<Users> findByEmail(String email);

}