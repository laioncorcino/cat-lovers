package com.corcino.catlovers.domain.rating.repository;

import com.corcino.catlovers.domain.rating.model.RatingDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RatingRepository extends MongoRepository<RatingDocument, String> {

    Optional<RatingDocument> findByBreedDocument_Id(String breedId);

}
