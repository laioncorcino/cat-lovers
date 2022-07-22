package com.corcino.catlovers.domain.vote.mapper;

import com.corcino.catlovers.domain.vote.dto.VoteResponse;
import com.corcino.catlovers.domain.vote.model.Vote;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VoteMapper {

    VoteMapper INSTANCE = Mappers.getMapper(VoteMapper.class);

//    @Mapping(source = "vote.breed.breedId", target = "vote.breed.id")
    VoteResponse toResponse(Vote vote);

}
