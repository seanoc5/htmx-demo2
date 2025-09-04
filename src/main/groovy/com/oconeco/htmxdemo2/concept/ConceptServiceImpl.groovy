package com.oconeco.htmxdemo2.concept;

import com.oconeco.htmxdemo2.util.NotFoundException
import groovy.util.logging.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class ConceptServiceImpl implements ConceptService {

    private final ConceptRepository conceptRepository;
    private final ApplicationEventPublisher publisher;


    public ConceptServiceImpl(final ConceptRepository conceptRepository,
                              ApplicationEventPublisher publisher) {
        this.conceptRepository = conceptRepository;
        this.publisher = publisher;
    }

    @Override
    public Page<ConceptDTO> findAll(final String filter, final Pageable pageable) {
        Page<Concept> page;
        if (filter && filter.isNumber()) {
            Long longFilter = null;
            try {
                longFilter = Long.parseLong(filter);
                log.warn("findAllById: {} -- NOTE: likely to remove this", longFilter);
            } catch (final NumberFormatException numberFormatException) {
                log.info("findAllById: Could not parse filter to long: {}, error: {}", filter, numberFormatException.getMessage());
            }
            page = conceptRepository.findAllById(longFilter, pageable);
        } else {
            page = conceptRepository.findAll(pageable);
        }
        return new PageImpl<>(page.getContent()
                .stream()
                .map(concept -> updateConceptDTO(concept, new ConceptDTO()))
                .toList(),
                pageable, page.getTotalElements());
    }

    @Override
    Page<ConceptDTO> filter(String filter, Pageable pageable) {
        String filterString = '%' + filter + '%'
        Page<Concept> page = conceptRepository.filter(filterString, pageable)
        log.info("({}) concepts filter totalcount:({}) in {} pages) -- with filter:'{}'",page.getContent().size(), page.getTotalElements(), page.getTotalPages(), filter);
        def pageImpl = new PageImpl<>(page.getContent()
                .stream()
                .map(concept -> updateConceptDTO(concept, new ConceptDTO()))
                .toList(),
                pageable, page.getTotalElements());
        return pageImpl
    }

    @Override
    Page<ConceptDTO> search(String filter, Pageable pageable) {
        Page<Concept> page = conceptRepository.findByLabelContainingOrDescriptionContainingOrAddressContainingIgnoreCase(filter, filter, filter,pageable)
        log.info("({}) concepts search totalcount:({}) in {} pages) -- with filter:'{}'",page.getContent().size(), page.getTotalElements(), page.getTotalPages(), filter);
        def pageImpl = new PageImpl<>(page.getContent()
                .stream()
                .map(concept -> updateConceptDTO(concept, new ConceptDTO()))
                .toList(),
                pageable, page.getTotalElements());
        return pageImpl
    }


    @Override
    public ConceptDTO get(final Long id) {
        return conceptRepository.findById(id)
                .map(concept -> updateConceptDTO(concept, new ConceptDTO()))
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public Long create(final ConceptDTO conceptDTO) {
        final Concept concept = new Concept();
        updateConcept(conceptDTO, concept);
        def rc = conceptRepository.save(concept)
                return rc.getId();
    }

    @Override
    public void update(final Long id, final ConceptDTO conceptDTO) {
        final Concept concept = conceptRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        updateConcept(conceptDTO, concept);
        conceptRepository.save(concept);
    }

    @Override
    public void delete(final Long id) {
        final Concept concept = conceptRepository.findById(id)
                .orElseThrow(NotFoundException::new);
//        publisher.publishEvent(new BeforeDeleteConcept(id));
        conceptRepository.delete(concept);
    }

    @Override
    public Map<Long, Long> getConceptValues() {
        return conceptRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Concept::getId, Concept::getId));
    }

    // Add direct mapping methods to replace ConceptMapper
    private ConceptDTO updateConceptDTO(Concept concept, ConceptDTO conceptDTO) {
        conceptDTO.id = concept.id
        conceptDTO.label = concept.label
        conceptDTO.address = concept.address
        conceptDTO.description = concept.description
        conceptDTO.keywords = concept.keywords
        conceptDTO.antiwords = concept.antiwords
        conceptDTO.skipwords = concept.skipwords
        return conceptDTO
    }
    
    private void updateConcept(ConceptDTO conceptDTO, Concept concept) {
        // Don't map ID as it should be generated or already set
        concept.label = conceptDTO.label
        concept.address = conceptDTO.address
        concept.description = conceptDTO.description
        concept.keywords = conceptDTO.keywords
        concept.antiwords = conceptDTO.antiwords
        concept.skipwords = conceptDTO.skipwords
    }

}
