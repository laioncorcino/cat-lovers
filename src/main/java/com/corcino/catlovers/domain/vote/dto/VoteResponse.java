package com.corcino.catlovers.domain.vote.dto;

import com.corcino.catlovers.domain.breed.dto.BreedResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class VoteResponse {

    private Long voteId;
    private Integer value;
    private BreedResponse breed;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime creationDate;

}
