package site.bannabe.server.global.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EncryptUtils {

  private final BCryptPasswordEncoder passwordEncoder;

  public String encodePassword(String password) {
    return passwordEncoder.encode(password);
  }
  
}