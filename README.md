# 🐝 BannaBee

> 자원 공유 기반 전자기기 액세서리 공유 대여 서비스 **Bannabe**의 API Server Repository 입니다.

- [서비스 소개 및 기능](#서비스-소개-및-기능)
- [팀 구성](#팀-구성)
- [기술 스택](#기술-스택)
- [프로젝트 구조](#프로젝트-구조)
- [시작하기](#시작하기)
- [기술적 경험](#기술적-경험)

## 서비스 소개 및 기능

디지털 네이티브 세대는 평균 5대 이상의 전자기기와 관련 액세서리를 보유하고 있으며, 이로 인한 관리와 비용의 부담이 증가하고 있습니다.
또한 불필요한 구매와 분실로 인한 전자폐기물이 매년 증가하는 추세입니다.
BannaBee는 노트북 고출력 충전기, 거치대, 블루투스 키보드, 마우스 등의 전자기기 액세서리를 공유 대여 서비스로 제공하며, 로컬 상권과 연계하여 지역경제 활성화 기여를 목표하고 있습니다.

앱에 대한 정보는 [Team-NEXTACT/bannabe](https://github.com/Team-NEXTACT/bannabe) Repository를 참고해 주세요.

### 주요 기능

- 다양한 전자기기 액세서리 대여: 5종 이상의 다양한 전자기기 액세서리를 시간당 1,000원의 합리적인 가격으로 대여
- 실시간 재고 확인: 앱을 통해 원하는 지역의 대여 가능한 제품 실시간 확인
- 자유로운 반납 시스템: 어디서나 자유롭게 반납 가능
- 멤버십 프로그램: 월 14,900원으로 모든 품목 자유롭게 이용 가능한 구독 서비스 (예정)
- 이동식 광고 플랫폼: 공유 전자기기 표면을 광고 공간으로 활용하여 로컬 소상공인 홍보 지원 (예정)
- 사용자 커뮤니티: 작업하기 좋은 장소 리뷰 및 정보 공유 (예정)

### 개발 현황

**2025.02.23 ~ 2025.03.27 | 1차 기능 개발 완료**

- 사용자 회원가입 / 로그인 기능
- 회원정보 관리 기능
- 대여 / 반납 기능
- 대여 스테이션 조회 기능
- 재고 확인 기능

## 팀 구성

| 이름  | GitHub                                  | 역할                 |
|-----|-----------------------------------------|--------------------|
| 김기홍 | [GitHub](https://github.com/kihong1602) | 프로젝트 관리, API 서버 개발 |
| 신서영 | [GitHub](https://github.com/pyqvv)      | 앱 개발               |
| 주민우 | [GitHub](https://github.com/minwoojoo)  | 어드민 페이지 개발         | 

## 기술 스택

| 구분        | 기술 스택                                         |
|-----------|-----------------------------------------------|
| Language  | Java 17                                       |
| Framework | Spring Boot 3.4.2, Spring Data JPA , Security |
| DataBase  | MySQL, Redis                                  |
| DevOps    | Docker, GitHub Actions, AWS S3                |
| Tools     | IntelliJ, Notion, Slack                       |

## 프로젝트 구조

```text
BannaBee
  ├── .github/
  │   ├── workflows/
  │
  ├── docker/
  │
  └── server/
      ├── src/
      │   ├── main/java/site/bannabe/server/
      │   │   ├── domain/
      │   │   │   ├── common/     # 공통 도메인 컴포넌트
      │   │   │   ├── users/      # 회원 관련 기능
      │   │   │   │   ├── controller/
      │   │   │   │   ├── entity/
      │   │   │   │   ├── repository/
      │   │   │   │   └── service/
      │   │   │   ├── rentals/    # 대여 관련 기능
      │   │   │   └── payments/   # 결제 관련 기능
      │   │   │
      │   │   ├── global/
      │   │   │   ├── advice/     
      │   │   │   ├── api/        
      │   │   │   ├── aws/        
      │   │   │   ├── config/     
      │   │   │   ├── converter/  
      │   │   │   ├── exceptions/ 
      │   │   │   ├── jwt/        
      │   │   │   ├── mail/       
      │   │   │   ├── redis/      
      │   │   │   ├── security/   
      │   │   │   ├── type/       
      │   │   │   └── utils/      
      │   │   │
      │   │
      │   └── test/
      │       └── ...
      │
      ├── build.gradle
      └── settings.gradle
```

## 시작하기

1. 레포지토리 클론

```bash
git clone https://github.com/Team-NEXTACT/bannabe-server.git
cd bannabe-server
```

2. Docker 실행

```bash
docker-compose -f docker/docker-compose.app.yml up -d
```

### API 문서

- 어플리케이션을 실행한 뒤 [http://localhost:8080/swagger](http://localhost:8080/swagger)에서 Swagger UI를 통해 API 명세서를 확인할 수 있습니다.

## 기술적 경험

### 1. 테스트 커버리지 90% 달성을 통한 안정적인 서비스 구현

- 문제점: 반나비 서비스는 재고 관리, 결제, 사용자 인증 등 다양한 도메인이 얽힌 시스템으로, 버그 발생 시 서비스 신뢰도에 직접적인 영향
- 해결책: 철저한 테스트 자동화 및 높은 테스트 커버리지를 통한 안정적인 서비스 구현
- 구현 내용:
    - JUnit5와 Mockito를 활용한 단위 테스트 및 통합 테스트 작성
    - **TestContainers**를 활용한 실제 데이터베이스 환경 기반의 통합 테스트 구현
    - JaCoCo를 통한 테스트 커버리지 측정 및 90% 이상의 라인 커버리지 달성
    - 주요 비즈니스 로직 및 엣지 케이스에 대한 테스트로 서비스 안정성 보장

### 2. AOP 기반 분산락 적용을 통한 대여 재고 동시성 이슈 해결

- 문제점: 여러 사용자가 동시에 같은 대여 품목에 접근할 경우 재고 관리 및 중복 대여 방지 필요
- 해결책: Redis 기반 분산락과 AOP를 활용한 동시성 제어 메커니즘 구현
- 구현 내용:
    - `@DistributedLock` 커스텀 어노테이션으로 메서드 레벨에서 락 적용 가능
    - SpEL(Spring Expression Language)을 활용한 동적 락 키 생성으로 유연한 리소스 제어
    - AOP를 통해 비즈니스 로직과 락 관리 로직의 관심사 분리

### 3. Slack Webhook을 활용한 PR 이벤트 알림 자동화

- 문제점: 팀 개발 환경에서 PR 생성, 업데이트, 병합 등의 이벤트를 실시간으로 공유하여 코드 리뷰 및 협업 프로세스 개선 필요
- 해결책: GitHub Actions와 Slack Webhook을 연동한 자동 알림 시스템 구축
- 구현 내용:
    - PR 생성, 커밋 추가, 병합 시 자동 Slack 알림 전송
    - PR 작성자, 제목, 번호, URL 등 중요 정보 자동 포함
    - develop 브랜치 대상 PR에 대해서만 알림을 보내도록 필터링하여 노이즈 감소

### 4. Spring RESTDocs 기반 API 문서 자동화 구현

- 문제점: API 문서가 실제 코드와 동기화되지 않아 발생하는 개발 혼선 및 커뮤니케이션 오류 방지 필요
- 해결책: 테스트 코드와 연동된 Spring RESTDocs를 활용한 API 문서 자동화
- 구현 내용:
    - 테스트 코드 실행 결과를 기반으로 OPENAPI3 문서를 자동 생성하여 코드와 문서의 일치성 보장
    - Swagger UI와 통합하여 개발자 친화적인 API 탐색 및 테스트 환경 제공
    - API 요청/응답 예시, 헤더, 파라미터 등 상세 정보 자동 문서화
    - 문서 스니펫을 모듈화하여 공통 요소의 재사용성 향상 및 유지보수 효율화

### 5. 소셜 로그인 통합 및 JWT 기반 인증 시스템

- 문제점: 다양한 플랫폼의 사용자를 위한 접근성 높은 인증 시스템 구축 필요
- 해결책: OAuth2 클라이언트 및 JWT 토큰 기반 인증 시스템 구현
- 구현 내용:
    - 카카오, 구글 등 다양한 소셜 로그인 제공 업체 연동
    - `ProviderType` Enum을 활용한 유연한 소셜 로그인 공급자 관리
    - JWT 토큰 기반 인증으로 서버 상태 관리 부담 감소
    - Redis를 활용한 리프레시 토큰 관리 및 토큰 무효화 기능 구현