package com.oconeco.htmxdemo2.concept;

import com.oconeco.trebuchet.events.BeforeDeleteConcept;
import com.oconeco.trebuchet.util.CustomCollectors;
import com.oconeco.trebuchet.util.NotFoundException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;


@Service
@Transactional(rollbackFor = Exception.class)
public class ConceptServiceImpl implements ConceptService {

    private final ConceptRepository conceptRepository;
    private final ConceptMapper conceptMapper;
    private final ApplicationEventPublisher publisher;


    public ConceptServiceImpl(final ConceptRepository conceptRepository,
                              final ConceptMapper conceptMapper, ApplicationEventPublisher publisher) {
        this.conceptRepository = conceptRepository;
        this.conceptMapper = conceptMapper;
        this.publisher = publisher;
    }

    @Override
    public Page<ConceptDTO> findAll(final String filter, final Pageable pageable) {
        Page<Concept> page;
        if (filter != null) {
            Long longFilter = null;
            try {
                longFilter = Long.parseLong(filter);
            } catch (final NumberFormatException numberFormatException) {
                // keep null - no parseable input
            }
            page = conceptRepository.findAllById(longFilter, pageable);
        } else {
            page = conceptRepository.findAll(pageable);
        }
        return new PageImpl<>(page.getContent()
                .stream()
                .map(concept -> conceptMapper.updateConceptDTO(concept, new ConceptDTO()))
                .toList(),
                pageable, page.getTotalElements());
    }

    @Override
    public ConceptDTO get(final Long id) {
        return conceptRepository.findById(id)
                .map(concept -> conceptMapper.updateConceptDTO(concept, new ConceptDTO()))
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public Long create(final ConceptDTO conceptDTO) {
        final Concept concept = new Concept();
        conceptMapper.updateConcept(conceptDTO, concept);
        return conceptRepository.save(concept).getId();
    }

    @Override
    public void update(final Long id, final ConceptDTO conceptDTO) {
        final Concept concept = conceptRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        conceptMapper.updateConcept(conceptDTO, concept);
        conceptRepository.save(concept);
    }

    @Override
    public void delete(final Long id) {
        final Concept concept = conceptRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        publisher.publishEvent(new BeforeDeleteConcept(id));
        conceptRepository.delete(concept);
    }

    @Override
    public Map<Long, Long> getConceptValues() {
        return conceptRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Concept::getId, Concept::getId));
    }

}
