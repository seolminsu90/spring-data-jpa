# spring-data-jpa
1 + N 문제 해결법 메모

- 하위 Entity 같이 Select 시 하위 Entity의 개수만큼 쿼리 실행하는 문제가 있음
- join fetch 와 @EntityGraph로 해결
- QueryDSL 사용하여 join, fetch

* 부모 자식 손자 관계에서도 부모 자식에만 적용 하면 손자 단계에서 1+N이 생기기에 손자도 조회해야 할 경우 주의해야 한다. 
  * 해당 관계에서 손자를 조회 안할거면 그냥 자식의 손자 필드에 Lazy처리만 해도 된다
```bash
// Fetch join 을 이용한 자식 키를 이용한 손자 조회
SELECT DISTINCT a, b 
FROM A a
LEFT JOIN a.bs b
LEFT JOIN FETCH a.bs
LEFT JOIN FETCH b.c
WHERE a.id = :id

// EntityGraph를 이용한 손자 까지 한번에 join 처리
@EntityGraph(attributePaths = {"questionReceive", "questionReceive.counselor"})
List<Question> findByCustomerIdOrderBySeqDesc(String customerId);
```
* oneToMany 2개 이상에 fetch join 2개이상 사용하면.. MultipleBagFetchException이 난다.
* 애초에 이런구조로 만들지 말던지 @Query를 쓰면 맘편하지만 1개만 fetchJoin 하던지 아래 옵션으로 n+1보다 in을 유도함
```bash
// 글로벌
spring:
  jpa:
    properties:
      hibernate.default_batch_fetch_size: 1000
      
// 기능별
@BatchSize(size = 10)
```
* 부모자식 교차 직렬화 무한루프 관련 오류시
```bash
# 1. 키 기반으로 중복 제외
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)


# 2. 주/부 나눔.
@ManyToOne
@JoinColumn(name="pkey")
@JsonBackReference <<
private Parent parent;


@OneToMany(mappedBy="cKey ")
@JsonManagedReference <<
private List<Child> child;

## 1이 간편하고 양방향 조회하기 
```
---
동시성 이슈 처리 관련 메모

1. 낙관적 잠금(service)
- Versioning 컬럼 생성하여 업데이트 대상 프로그래밍 레벨에서 버전 관리

2. 비관적 잠금(db tx lock)
- LockModeType.PESSIMISTIC_WRITE
- LockModeType.PESSIMISTIC_READ
