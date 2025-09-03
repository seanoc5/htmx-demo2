package com.oconeco.htmxdemo2.config

import com.oconeco.htmxdemo2.concept.ConceptMapper
import org.mapstruct.factory.Mappers
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

//@Configuration
class MapperConfig {
    
//    @Bean
    ConceptMapper conceptMapper() {
        return Mappers.getMapper(ConceptMapper)
    }
}
