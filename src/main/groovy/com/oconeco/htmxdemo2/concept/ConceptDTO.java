package com.oconeco.htmxdemo2.concept;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ConceptDTO {

    private Long id;

    @Size(max = 255)
    private String label;

    private String descrption;

    private String keywords;

    private String antiwords;

    private String skipwords;

}
