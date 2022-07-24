package com.corcino.catlovers.domain.rating.service;

import com.corcino.catlovers.domain.breed.dto.BreedResponse;
import com.corcino.catlovers.domain.breed.mapper.BreedMapper;
import com.corcino.catlovers.domain.breed.service.BreedService;
import com.corcino.catlovers.domain.rating.dto.RatingRequest;
import com.corcino.catlovers.domain.rating.model.BreedDocument;
import com.corcino.catlovers.domain.rating.model.RatingDocument;
import com.corcino.catlovers.domain.rating.repository.RatingRepository;
import com.corcino.catlovers.error.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class RatingService {

    private final BreedService breedService;
    private final RatingRepository ratingRepository;
    private final static BreedMapper breed_mapper = BreedMapper.INSTANCE;

    @Autowired
    public RatingService(BreedService breedService, RatingRepository ratingRepository) {
        this.breedService = breedService;
        this.ratingRepository = ratingRepository;
    }

    public RatingDocument registerRating(RatingRequest ratingRequest) throws Exception {
        BreedResponse breedResponse = breedService.findCatByBreedId(ratingRequest.getBreedId());
        BreedDocument breedDocument = breed_mapper.toDocument(breedResponse);
        checkEvaluate(ratingRequest);
        RatingDocument rating = new RatingDocument(ratingRequest, breedDocument);
        return saveRating(rating);
    }

    private void checkEvaluate(RatingRequest ratingRequest) {
        Optional<RatingDocument> optionalRating = ratingRepository.findByBreedDocument_Id(ratingRequest.getBreedId());
        if (optionalRating.isPresent()) {
            throw new BadRequestException("Voce já avaliou este gato");
        }
    }

    private RatingDocument saveRating(RatingDocument rating) {
        try {
            log.info("Salvando rating para o gato {}", rating.getBreedDocument().getName());
            return ratingRepository.save(rating);
        }
        catch (Exception e) {
            log.error("Erro ao salvar rating para este gato" + e.getMessage());
            throw new BadRequestException("Erro ao salvar rating para este gato" + e.getMessage());
        }
    }



}
