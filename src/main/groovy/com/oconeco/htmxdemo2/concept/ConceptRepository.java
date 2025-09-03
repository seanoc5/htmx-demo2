package com.oconeco.htmxdemo2.concept;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ConceptRepository extends JpaRepository<Concept, Long> {

    Page<Concept> findAllById(Long id, Pageable pageable);

}
