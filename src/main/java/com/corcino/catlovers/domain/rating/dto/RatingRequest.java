package com.corcino.catlovers.domain.rating.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class RatingRequest {

    @NotEmpty @NotNull
    private String breedId;

    @Min(value = 0) @Max(value = 5)
    private int familiar;

    @Min(value = 0) @Max(value = 5)
    private int clean;

    @Min(value = 0) @Max(value = 5)
    private int independent;

    @Min(value = 0) @Max(value = 5)
    private int curious;

    @Min(value = 0) @Max(value = 5)
    private int hunter;

    @Min(value = 0) @Max(value = 5)
    private int emotional;

    @Min(value = 0) @Max(value = 5)
    private int intelligent;

    @Min(value = 0) @Max(value = 5)
    private int sociable;

}
