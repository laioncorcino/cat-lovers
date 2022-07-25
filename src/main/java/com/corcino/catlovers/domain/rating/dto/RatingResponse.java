package com.corcino.catlovers.domain.rating.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RatingResponse {

    private String ratingId;
    private Integer familiar;
    private Integer clean;
    private Integer independent;
    private Integer curious;
    private Integer hunter;
    private Integer emotional;
    private Integer intelligent;
    private Integer sociable;

    @JsonProperty("breed")
    private BreedResponse breedDocument;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime creationDate;

}
