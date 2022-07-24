package com.corcino.catlovers.domain.favorite.service;

import com.corcino.catlovers.domain.breed.dto.BreedResponse;
import com.corcino.catlovers.domain.breed.mapper.BreedMapper;
import com.corcino.catlovers.domain.breed.service.BreedService;
import com.corcino.catlovers.domain.favorite.dto.FavoriteResponse;
import com.corcino.catlovers.domain.favorite.dto.ListFavoriteResponse;
import com.corcino.catlovers.domain.favorite.dto.FavoriteRequest;
import com.corcino.catlovers.domain.favorite.mapper.FavoriteMapper;
import com.corcino.catlovers.domain.favorite.model.Breed;
import com.corcino.catlovers.domain.favorite.model.Favorite;
import com.corcino.catlovers.domain.favorite.repository.FavoriteRepository;
import com.corcino.catlovers.error.exception.BadRequestException;
import com.corcino.catlovers.error.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class FavoriteService {

    private final BreedService breedService;
    private final FavoriteRepository favoriteRepository;
    private final static FavoriteMapper mapper = FavoriteMapper.INSTANCE;
    private final static BreedMapper breed_mapper = BreedMapper.INSTANCE;

    @Autowired
    public FavoriteService(BreedService breedService, FavoriteRepository favoriteRepository) {
        this.breedService = breedService;
        this.favoriteRepository = favoriteRepository;
    }

    public Favorite registerFavorite(FavoriteRequest favoriteRequest) throws Exception {
        BreedResponse breedResponse = breedService.findCatByBreedId(favoriteRequest.getBreedId());
        Breed breed = breed_mapper.toModel(breedResponse);
        Favorite favorite = new Favorite(favoriteRequest.getValue(), breed);
        return saveFavorite(favorite);
    }

    private Favorite saveFavorite(Favorite favorite) {
        try {
            log.info("Salvando favorito para o gato {}", favorite.getBreed().getName());
            return favoriteRepository.save(favorite);
        }
        catch (DataIntegrityViolationException e) {
            log.error("Usuario ja possui este gato como favorito - {}", favorite.getBreed().getName());
            throw new BadRequestException("Voce ja possui este gato como favorito - " + favorite.getBreed().getName());
        }
        catch (Exception e) {
            log.error("Erro ao salvar este gato como favorito" + e.getMessage());
            throw new BadRequestException("Erro ao salvar este gato como favorito" + e.getMessage());
        }
    }

    public FavoriteResponse getVote(Long voteId) {
        Favorite favorite = getFavoriteById(voteId);
        return mapper.toResponse(favorite);
    }

    private Favorite getFavoriteById(Long favoriteId) {
        log.info("Buscando favorito de id {}", favoriteId);

        Optional<Favorite> favorite = favoriteRepository.findById(favoriteId);

        return favorite.orElseThrow(() -> {
            log.error("Favorito de id {} nao encontrado", favoriteId);
            return new NotFoundException("Favorito nao encontrado");
        });
    }

    public Page<ListFavoriteResponse> listFavorites(Pageable pageable) {
        Page<Favorite> favorites = favoriteRepository.findAll(pageable);
        return new ListFavoriteResponse().convertList(favorites);
    }

    public void deleteFavorite(Long voteId) {
        Favorite favorite = getFavoriteById(voteId);
        log.info("Deletando favorito de id {}", favorite.getFavoriteId());
        favoriteRepository.deleteById(voteId);
    }

}




