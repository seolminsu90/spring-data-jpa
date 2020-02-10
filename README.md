# spring-data-jpa
1:N 문제 해결법 메모

- 하위 Entity 같이 Select 시 하위 Entity의 개수만큼 쿼리 실행하는 문제가 있음
- join fetch 와 @EntityGraph로 해결
