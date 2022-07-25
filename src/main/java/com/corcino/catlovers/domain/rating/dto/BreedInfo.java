package com.corcino.catlovers.domain.rating.dto;

import com.corcino.catlovers.domain.breed.dto.ImageResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonIgnoreProperties
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BreedInfo {

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
    private ImageResponse image;

}
