package com.corcino.catlovers.domain.favorite.mapper;

import com.corcino.catlovers.domain.favorite.dto.FavoriteResponse;
import com.corcino.catlovers.domain.favorite.model.Favorite;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FavoriteMapper {

    FavoriteMapper INSTANCE = Mappers.getMapper(FavoriteMapper.class);

    FavoriteResponse toResponse(Favorite favorite);

}
