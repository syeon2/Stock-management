## 📦 Stock-Management (V1)

### 쿠팡같은 E-commerce의 물류 재고 관리 시스템을 개발하는 토이 프로젝트

---

### 🌱 프로젝트 목표

- 재고 관리를 통한 트랜젝션, DB Lock, 동시성 등을 효율적으로 사용하여 신뢰할 수 있는 데이터 처리를 목표로 합니다.
- 집중적으로 I/O가 일어나는 상품 패키징 내역 테이블, 유저, 상품 테이블의 부하를 분산시키기 위해 Sharding을 적용합니다.
- 안정 해시를 사용하여 DB 서버의 부하를 분산시키고 장애 복구를 유연하게 처리합니다.
- 또한, 샤딩된 DB 서버의 SPOF를 해결하기 위해 각 서버에 MySQL Cluster를 구축합니다.

### 🌱 필요 기술 스택

for Application : Java 11, Spring Boot(2.7.12), MyBatis
<br />
for Database : MySQL, Redis

### 🌱 프로젝트 ERD

<img src="https://github.com/syeon2/Stock-management/assets/71717303/98b6d05a-3c30-41ed-ab53-64f63c394394" width="800" />

### 🌱 프로젝트 설계

<img src="https://github.com/syeon2/Stock-management/assets/71717303/0488760e-9884-4a8c-a6bd-863e6bdf751d" width="800" />
