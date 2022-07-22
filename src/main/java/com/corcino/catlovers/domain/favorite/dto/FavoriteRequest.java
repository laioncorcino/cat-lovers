package com.corcino.catlovers.domain.favorite.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class FavoriteRequest {

    @NotNull @NotEmpty
    private String breedId;

    @Min(value = 1)
    private int value;

}
