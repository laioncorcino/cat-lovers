package com.corcino.catlovers.unit.rating.service;

import com.corcino.catlovers.domain.breed.service.BreedService;
import com.corcino.catlovers.domain.rating.dto.RatingRequest;
import com.corcino.catlovers.domain.rating.dto.RatingResponse;
import com.corcino.catlovers.domain.rating.model.RatingDocument;
import com.corcino.catlovers.domain.rating.repository.RatingRepository;
import com.corcino.catlovers.domain.rating.service.RatingService;
import com.corcino.catlovers.error.exception.BadRequestException;
import com.corcino.catlovers.error.exception.NotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static com.corcino.catlovers.unit.favorite.builder.FavoriteCreator.createAeganBreedResponse;
import static com.corcino.catlovers.unit.rating.builder.RatingCreator.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class RatingServiceTest {

    @InjectMocks
    private RatingService ratingService;

    @Mock
    private RatingRepository ratingRepository;

    @Mock
    private BreedService breedService;

    @Test
    @DisplayName("deve_retornar_rating_buscando_por_id")
    public void getRatingById() {
        Mockito.when(ratingRepository.findById(eq("cba"))).thenReturn(Optional.of(buildRatingAegan()));
        RatingResponse rating = ratingService.getRating("cba");

        assertThat(rating).isNotNull();
        assertThat(rating.getBreedDocument().getName()).isEqualTo("Aegean");
        assertThat(rating.getBreedDocument().getId()).isEqualTo("aege");
    }

    @Test
    @DisplayName("deve_tratar_exception_recebida_ao_nao_encontrar_rating_pelo_id")
    public void getRatingByIdNotFound() {
        Mockito.when(ratingRepository.findById("not_found")).thenReturn(Optional.empty());

        Throwable exception = catchThrowable(() -> ratingService.getRating("not_found"));

        assertThat(exception)
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Rating nao encontrado");
    }

    @Test
    @DisplayName("deve_avaliar_gato_com_sucesso")
    public void createRating() throws Exception {
        Mockito.when(breedService.findCatByBreedId(eq("aege"))).thenReturn(createAeganBreedResponse());
        Mockito.when(ratingRepository.save(any(RatingDocument.class))).thenReturn(buildRatingAegan());

        RatingRequest ratingRequest = buildRatingRequest();
        RatingDocument rating = ratingService.registerRating(ratingRequest);

        assertThat(rating).isNotNull();
        assertThat(rating.getRatingId()).isNotNull();
        assertThat(rating.getRatingId()).isEqualTo("cba");
        assertThat(rating.getBreedDocument().getId()).isEqualTo("aege");
    }

    @Test
    @DisplayName("nao_deve_avaliar_gato_que_ja_tenha_sido_avaliado")
    public void createRatingDuplicatedBreed() throws Exception {
        Mockito.when(breedService.findCatByBreedId(eq("aege"))).thenReturn(createAeganBreedResponse());
        Mockito.when(ratingRepository.findByBreedDocument_Id(eq("aege"))).thenReturn(Optional.of(buildRatingAegan()));

        RatingRequest ratingRequest = buildRatingRequest();
        Throwable exception = catchThrowable(() -> ratingService.registerRating(ratingRequest));

        assertThat(exception)
                .isInstanceOf(BadRequestException.class)
                .hasMessage("Voce já avaliou este gato - Aegean");
    }

    @Test
    @DisplayName("deve_deletar_rating")
    public void deleteRating() {
        Mockito.when(ratingRepository.findById("abc")).thenReturn(Optional.of(buildRatingArabian()));
        Mockito.doNothing().when(ratingRepository).deleteById(eq("abc"));

        ratingService.deleteRating("abc");
    }

    @Test
    @DisplayName("nao_deve_deletar_rating_ao_nao_encontrar_id")
    public void deleteRatingWithIdNotFound() {
        Mockito.when(ratingRepository.findById("not_found")).thenReturn(Optional.empty());

        Throwable exception = catchThrowable(() -> ratingService.deleteRating("not_found"));

        assertThat(exception)
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Rating nao encontrado");
    }

}
