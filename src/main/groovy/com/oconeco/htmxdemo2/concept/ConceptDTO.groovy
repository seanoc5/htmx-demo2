package com.oconeco.htmxdemo2.concept;

import jakarta.validation.constraints.Size;


public class ConceptDTO {

    public Long id;

    public String address;

    @Size(max = 255)
    public String label;

    public String description;

    public String keywords;

    public String antiwords;

    public String skipwords;

    String toString() {
        String s = "($address) $label [$id]"
        return s
    }

}
