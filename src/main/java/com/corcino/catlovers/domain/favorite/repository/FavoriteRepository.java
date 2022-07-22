package com.corcino.catlovers.domain.favorite.repository;

import com.corcino.catlovers.domain.favorite.model.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

}
