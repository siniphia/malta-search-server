## 개요
* 프로젝트 : Java 17, Spring Boot 3, Gradle
* 멀티모듈 : malta-adapter, malta-server 두 개 모듈
* 다운로드
  * malta-adapter : https://drive.google.com/file/d/1JDYBuPJJnA3_ZDs6MJkyh476RaruGz4b/view?usp=sharing
  * malta-server : https://drive.google.com/file/d/1t7s6s0OlPkgPHRowGjePPOqrs2QiI4mH/view?usp=sharing
    

## 멀티모듈 상세
### **malta-adapter**
    * 개요
        * 외부 API를 호출해 통일된 Json 포맷으로 변경/반환하는 독립 모듈
    * 특징
        * 모듈 간 의존성 없음
        * 카카오 API에 대용량 트래픽을 대비한 Circuit Breaker 설치
        * 카카오 API 오류 시 네이버 API로 Fallback
        * 외부 모듈을 위한 Port-Adapter 인터페이스 제공
        * Pagination 정보로 감싸 반환
    
### **malta-server** 
     * 개요
        * malta-adapter 모듈을 호출하고 추가로 키워드 분석을 제공하는 의존 모듈
     * 특징
         * malta-adapter에 의존성 있음
         * H2의 Isolation Level을 Read-Committed로 유지
            * Dirty Read : 격리레벨에 의해 미발생
            * Non-Repeatable Read : 트랜잭션을 단일 Select 문으로 구성하여 미발생
            * Phantom Read : 발생 가능하지만 대용량 트래픽 상황에서는 격리레벨을 더 높이기 어려우므로 타협
         * 외부 모듈 사용을 위한 Port-Adapter 구현체 구현
    

3. API 명세
    * https://docs.google.com/presentation/d/1bM2U6F-fF4R8WAHi5Pb2DuyBOcxGgY1yUIqLAfe2mRU/edit?usp=sharing
    * 홈페이지를 통해 제출드린 pdf에도 같은 내용이 들어 있습니다.

4. 라이브러리
    * spring-boot-starter-validation : @Min, @Max 등의 검증 용도로 활용
    * spring-boot-starter-webflux : 카카오 API 연동을 위해 WebClient 활용을 위함
    * resilience4j : 서킷브레이커와 폴백 구현을 위함
    * org.json : 카카오 API 연동 시 Body를 Json 파싱하여 DTO에 매핑하기 위함
    * lombok : 가독성 및 편의성을 위한 유틸리티 어노테이션 활용을 위함
    * assertj, mockito-inline : 테스트 코드 작성 및 모킹을 위함
