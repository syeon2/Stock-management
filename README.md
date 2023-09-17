## 📦 Stock-Management (V1)

### 쿠팡같은 E-commerce의 물류 재고 관리 시스템을 개발하는 토이 프로젝트

---

### 🌱 프로젝트 핵심 기능

- 재고 관리를 통한 트랜젝션, DB Lock, 동시성 등을 효율적으로 사용하여 신뢰할 수 있는 데이터 처리.
- DB 서버에게 요청되는 각종 쿼리 연산 부하를 분산 및 SPOF의 위험을 해결하기 위해 MySQL Replication, Redis Cluster 구축
- 대용량 트래픽 요청시 트랜잭션의 오버헤드로 쓰기 작업의 요청이 지연되는 현상을 Redis와 Token Bucket 개념을 활용하여 해결

### 🌱 필요 기술 스택

for Application : Java 11, Spring Boot(2.7.12), MyBatis
<br />
for Database : MySQL, Redis

### 🌱 프로젝트 ERD

<img src="https://github.com/syeon2/Stock-management/assets/71717303/98b6d05a-3c30-41ed-ab53-64f63c394394" width="800" />

### 🌱 프로젝트 설계
<img width="346" alt="Screenshot 2023-09-11 at 4 14 39 PM" src="https://github.com/syeon2/Stock-management/assets/71717303/cc5801cb-c2d9-467c-8107-8dd1433e159e" width="800">
