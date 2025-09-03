package com.oconeco.htmxdemo2.concept;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param;


public interface ConceptRepository extends JpaRepository<Concept, Long> {

    Page<Concept> findAllById(Long id, Pageable pageable);

    @Query(value = "SELECT c FROM Concept c WHERE c.label ILIKE :filter")
    Page<Concept> filter(@Param("filter") String filter, Pageable pageable);


}
