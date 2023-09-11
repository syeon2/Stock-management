## 📦 Stock-Management (V1)

### 쿠팡같은 E-commerce의 물류 재고 관리 시스템을 개발하는 토이 프로젝트

---

### 🌱 프로젝트 목표

- 재고 관리를 통한 트랜젝션, DB Lock, 동시성 등을 효율적으로 사용하여 신뢰할 수 있는 데이터 처리를 목표로 합니다.
- DB 서버에게 요청되는 각종 쿼리 연산 부하를 분산시키기 위해 DataBase Replication을 적용합니다.
- Redis 서버를 통해 재고를 감소시켜 응답 속도를 향상시키고 Kafka를 통해 DB의 재고를 감소시켜 정합성을 지키도록 합니다.
- Redis 서버 클러스터링을 구축하여 서버의 안정성을 향상시킵니다.

### 🌱 필요 기술 스택

for Application : Java 11, Spring Boot(2.7.12), MyBatis
<br />
for Database : MySQL, Redis

### 🌱 프로젝트 ERD

<img src="https://github.com/syeon2/Stock-management/assets/71717303/98b6d05a-3c30-41ed-ab53-64f63c394394" width="800" />

### 🌱 프로젝트 설계
<img width="346" alt="Screenshot 2023-09-11 at 4 14 39 PM" src="https://github.com/syeon2/Stock-management/assets/71717303/cc5801cb-c2d9-467c-8107-8dd1433e159e" width="800">
