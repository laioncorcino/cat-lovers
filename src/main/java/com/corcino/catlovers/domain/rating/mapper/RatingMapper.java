package com.corcino.catlovers.domain.rating.mapper;

import com.corcino.catlovers.domain.rating.dto.RatingResponse;
import com.corcino.catlovers.domain.rating.model.RatingDocument;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RatingMapper {

    RatingMapper INSTANCE = Mappers.getMapper(RatingMapper.class);

    RatingResponse toResponse(RatingDocument rating);

}
