package com.data.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.data.jpa.domain.Parent;

public interface ParentRepository extends JpaRepository<Parent, Long>{
    // Spring에서 지원한 EntityGraph - Left Join 만 된다.
    @EntityGraph(attributePaths = "childrens", type = EntityGraph.EntityGraphType.LOAD)
    @Query(value = "SELECT p FROM Parent p")
    List<Parent> findByAllByEntityGraph();
    
    // JPA진영에서 지원한 fetch join - [LEFT [OUTER] | INNER] join fetch
    @Query(value = "SELECT distinct p FROM Parent p join fetch p.childrens")
    List<Parent> findByAllByJoinFetch();
}
