package com.corcino.catlovers.domain.breed.dto;

import lombok.Data;

@Data
public class BreedResponse {

    private String id;
    private String name;
    private String cfa_url;
    private String vetstreet_url;
    private String vcahospitals_url;
    private String temperament;
    private String origin;
    private String country_code;
    private String description;
    private String life_span;
    private String lap;
    private Integer adaptability;
    private Integer affection_level;
    private Integer child_friendly;
    private Integer dog_friendly;
    private Integer energy_level;
    private Integer health_issues;
    private Integer intelligence;
    private Integer social_needs;
    private Integer stranger_friendly;
    private Integer rare;
    private ImageResponse image;

}
