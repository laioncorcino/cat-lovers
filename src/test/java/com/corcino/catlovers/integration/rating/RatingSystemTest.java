package com.corcino.catlovers.integration.rating;

import com.corcino.catlovers.domain.rating.dto.RatingRequest;
import com.corcino.catlovers.domain.rating.dto.RatingResponse;
import com.corcino.catlovers.domain.rating.repository.RatingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RatingSystemTest extends RatingInfraTest {

    @Autowired
    private RatingRepository ratingRepository;

    @BeforeEach
    public void cleanerDatabase() {
        ratingRepository.deleteAll();
    }

    @Test
    @DisplayName("deve_retornar_rating_pelo_id")
    public void getRatingById() {
        populateDatabase();

        RatingRequest siam = new RatingRequest("siam", 1, 4, 5, 2, 2, 5, 2, 1);
        ResponseEntity<String> postResponse = doPost(siam);

        String uri = extractUrlContext(postResponse);
        ResponseEntity<RatingResponse> getResponse = doGet(uri);

        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isNotNull();
        assertThat(getResponse.getBody().getBreedDocument().getName()).isEqualTo("Siamese");
        assertThat(getResponse.getBody().getIndependent()).isEqualTo(5);
    }

    @Test
    @DisplayName("deve_retornar_not_found_para_id_nao_encontrado")
    public void getRatingByIdNotFound() {
        populateDatabase();

        ResponseEntity<?> getResponse = doGetById("not_found");

        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(getResponse.getBody()).isNotNull();
    }

    @Test
    @DisplayName("deve_avaliar_gato_com_sucesso")
    public void createRating() {
        RatingRequest hima = new RatingRequest("hima", 4, 2, 3, 4, 4, 1, 3, 3);
        ResponseEntity<String> postResponse = doPost(hima);

        assertThat(postResponse).isNotNull();
        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(postResponse.getHeaders().getLocation()).isNotNull();

        String uri = extractUrlContext(postResponse);
        ResponseEntity<RatingResponse> getResponse = doGet(uri);

        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isNotNull();
        assertThat(getResponse.getBody().getBreedDocument().getName()).isEqualTo("Himalayan");
        assertThat(getResponse.getBody().getFamiliar()).isEqualTo(4);
    }

    @Test
    @DisplayName("deve_validar_os_requests_passados_para_registrar_rating")
    public void validateEmptyRequest() {
        ResponseEntity<String> postResponse = doPost(new RatingRequest());

        assertThat(postResponse).isNotNull();
        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        assertThat(postResponse.getBody()).isNotNull();
        assertTrue(postResponse.getBody().contains("must not be empty"));
        assertTrue(postResponse.getBody().contains("must not be null"));
    }

    @Test
    @DisplayName("nao_deve_avaliar_gato_caso_ja_tenha_votado")
    public void createRatingDuplicatedBreed() {
        populateDatabase();

        RatingRequest pers = new RatingRequest("pers", 1, 2, 3, 5, 5, 2, 4, 4);
        ResponseEntity<String> postResponse = doPost(pers);

        assertThat(postResponse).isNotNull();
        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(postResponse.getBody()).isNotNull();
    }

    @Test
    @DisplayName("deve_deletar_rating")
    public void deleteRating() {
        populateDatabase();

        RatingRequest pers = new RatingRequest("beng", 1, 5, 4, 5, 5, 2, 4, 1);
        ResponseEntity<String> postResponse = doPost(pers);
        String postResource = extractUrlContext(postResponse);

        ResponseEntity<String> deleteResponse = doDelete(postResource);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<RatingResponse> getResponse = doGet(postResource);

        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(getResponse.getBody()).isNotNull();
    }

}
