package site.bannabe.server.global.jwt;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

  @InjectMocks
  private JwtService jwtService;

  @Mock
  private JwtProvider jwtProvider;

  // JWT 정상 생성 및 RefreshToken 저장 확인 테스트

  // refreshJWT 정상적으로 동작하는지 확인하는 테스트

  // refreshJWT 요청한 RefreshToken과 Redis 저장된 RefreshToken이 일치하지 않을 때 예외 발생 확인 테스트

  // saveAuthentication 정상적으로 SecurityContextHolder에 Authentication 저장하는지 확인 테스트

  // removeRefreshToken 정상적으로 Redis에 저장된 RefreshToken 삭제하는지 확인 테스트

}