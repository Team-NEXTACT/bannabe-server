package site.bannabe.server.domain.users.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.bannabe.server.domain.users.controller.request.UserRegisterRequest;
import site.bannabe.server.domain.users.entity.Users;
import site.bannabe.server.domain.users.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;

  private final BCryptPasswordEncoder passwordEncoder;

  @Transactional
  public void registerUser(UserRegisterRequest registerRequest) {
    Boolean isDuplicateEmail = userRepository.existsByEmail(registerRequest.email());
    if (isDuplicateEmail) {
      throw new IllegalArgumentException("이미 사용중인 이메일입니다.");
    }

    String encodedPassword = passwordEncoder.encode(registerRequest.password());

    Users user = Users.createUser(registerRequest.email(), encodedPassword);

    userRepository.save(user);
  }

}