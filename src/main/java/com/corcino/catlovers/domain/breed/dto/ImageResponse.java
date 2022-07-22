package com.corcino.catlovers.domain.breed.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonIgnoreProperties
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ImageResponse {

    private String id;
    private String url;

}
