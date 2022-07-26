package com.corcino.catlovers.domain.breed.mapper;

import com.corcino.catlovers.domain.breed.dto.BreedResponse;
import com.corcino.catlovers.domain.favorite.model.Breed;
import com.corcino.catlovers.domain.rating.model.BreedDocument;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BreedMapper {

    BreedMapper INSTANCE = Mappers.getMapper(BreedMapper.class);

    Breed toModel(BreedResponse breedResponse);

    BreedDocument toDocument(BreedResponse breedResponse);

}
