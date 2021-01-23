# spring-data-jpa
1 + N 문제 해결법 메모

- 하위 Entity 같이 Select 시 하위 Entity의 개수만큼 쿼리 실행하는 문제가 있음
- join fetch 와 @EntityGraph로 해결

* oneToMany 2개 이상에 fetch join 2개이상 사용하면.. MultipleBagFetchException이 난다.
* 애초에 이런구조로 만들지 말던지 @Query를 쓰면 맘편하지만 1개만 fetchJoin 하던지 아래 옵션으로 n+1보다 in을 유도함
```bash
spring:
  jpa:
    properties:
      hibernate.default_batch_fetch_size: 1000
```
