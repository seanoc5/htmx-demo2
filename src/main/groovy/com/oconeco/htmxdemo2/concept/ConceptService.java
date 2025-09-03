package com.oconeco.htmxdemo2.concept;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;


public interface ConceptService {

    Page<ConceptDTO> findAll(String filter, Pageable pageable);

    ConceptDTO get(Long id);

    Long create(ConceptDTO conceptDTO);

    void update(Long id, ConceptDTO conceptDTO);

    void delete(Long id);

    Map<Long, Long> getConceptValues();

}
