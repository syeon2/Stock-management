## 📦 Stock-Management

### 물류 재고 관리 시스템을 개발하는 토이 프로젝트

--------

### 🌱 프로젝트 관심사 및 주요 기능

- 재고 관리를 통한 트랜젝션, DB Lock, 동시성 등을 효율적으로 사용하여 신뢰할 수 있는 데이터 처리
- DB 서버에게 요청되는 각종 쿼리 연산 부하를 분산 및 SPOF의 위험을 해결하기 위해 MySQL Replication, Redis Cluster 구축
- 대용량 트래픽 요청시 트랜잭션의 오버헤드로 쓰기 작업의 요청이 지연되는 현상을 Redis와 Token Bucket 개념을 활용하여 해결
- 연관성이 작은 2개의 Request를 Bulkhead Pattern을 사용하여 격리 / 하나의 요청에서 에러가 발생해도 다른 요청은 정상적으로 처리

---------


### 🌱 필요 기술 스택

for Application : Java 11, Spring Boot(2.7.12), MyBatis
<br />
for Database : MySQL, Redis

---------

### 🌱 프로젝트 ERD

<img src="https://i.ibb.co/Lhd2Sbv/stock-management-modeling.png" width="850" />

----------

### 🌱 프로젝트 설계

<img src="https://i.ibb.co/kxzbbFy/Screenshot-2023-09-25-at-3-20-20-PM.png" alt="Screenshot-2023-09-25-at-3-20-20-PM" width="850" />
