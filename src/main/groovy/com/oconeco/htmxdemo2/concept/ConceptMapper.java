package com.oconeco.htmxdemo2.concept;

import org.mapstruct.*;


@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ConceptMapper {

    ConceptDTO updateConceptDTO(Concept concept, @MappingTarget ConceptDTO conceptDTO);

    @Mapping(target = "id", ignore = true)
    Concept updateConcept(ConceptDTO conceptDTO, @MappingTarget Concept concept);

}
