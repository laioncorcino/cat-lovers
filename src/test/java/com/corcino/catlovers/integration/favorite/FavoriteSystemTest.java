package com.corcino.catlovers.integration.favorite;

import com.corcino.catlovers.domain.favorite.dto.FavoriteRequest;
import com.corcino.catlovers.domain.favorite.dto.FavoriteResponse;
import com.corcino.catlovers.domain.favorite.dto.ListFavoriteResponse;
import com.corcino.catlovers.domain.favorite.repository.FavoriteRepository;
import com.corcino.catlovers.integration.wrapper.PageableResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FavoriteSystemTest extends FavoriteInfraTest {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @BeforeEach
    public void cleanerDatabase() {
        favoriteRepository.deleteAll();
    }

    @Test
    @DisplayName("deve_retornar_lista_de_racas")
    public void listBreeds() {
        populateDatabase();

        ResponseEntity<PageableResponse<ListFavoriteResponse>> getResponse = doGet();

        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getHeaders()).isNotNull();
        assertThat(getResponse.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);

        PageableResponse<ListFavoriteResponse> pageFavorites = getResponse.getBody();

        assertThat(pageFavorites).isNotNull();
        assertThat(pageFavorites.getNumberOfElements()).isEqualTo(3);
        assertThat(pageFavorites.getNumber()).isEqualTo(0);
        assertThat(pageFavorites.getSize()).isEqualTo(10);

        List<ListFavoriteResponse> contentList = pageFavorites.getContent();

        assertTrue(contentList.stream().anyMatch(favorite -> favorite.getName().equals("Siamese")));
        assertTrue(contentList.stream().anyMatch(favorite -> favorite.getName().equals("Himalayan")));
        assertTrue(contentList.stream().anyMatch(favorite -> favorite.getName().equals("Persian")));
    }

    @Test
    @DisplayName("deve_retornar_favorito_pelo_id")
    public void getFavoriteById() {
        populateDatabase();
        ResponseEntity<String> postResponse = doPost(new FavoriteRequest("beng", 1));

        String uri = extractUrlContext(postResponse);
        ResponseEntity<FavoriteResponse> getResponse = doGet(uri);

        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isNotNull();
        assertThat(getResponse.getBody().getBreed().getName()).isEqualTo("Bengal");
    }

    @Test
    @DisplayName("deve_retornar_not_found_para_id_nao_encontrado")
    public void getFavoriteByIdNotFound() {
        populateDatabase();

        ResponseEntity<?> getResponse = doGetById(100000L);

        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(getResponse.getBody()).isNotNull();
    }

    @Test
    @DisplayName("deve_salvar_gato_favorito_com_sucesso")
    public void createFavorite() {
        ResponseEntity<String> postResponse = doPost(new FavoriteRequest("orie", 1));

        assertThat(postResponse).isNotNull();
        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(postResponse.getHeaders().getLocation()).isNotNull();

        String uri = extractUrlContext(postResponse);
        ResponseEntity<FavoriteResponse> getResponse = doGet(uri);

        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isNotNull();
        assertThat(getResponse.getBody().getBreed().getName()).isEqualTo("Oriental");
    }

    @Test
    @DisplayName("deve_validar_os_requests_passados_para_registrar_favorito")
    public void validateEmptyRequest() {
        ResponseEntity<String> postResponse = doPost(new FavoriteRequest());

        assertThat(postResponse).isNotNull();
        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        assertThat(postResponse.getBody()).isNotNull();
        assertTrue(postResponse.getBody().contains("must be greater than or equal to 1"));
        assertTrue(postResponse.getBody().contains("must not be empty"));
        assertTrue(postResponse.getBody().contains("must not be null"));
    }

    @Test
    @DisplayName("nao_deve_salvar_gato_como_favorito_caso_ja_tenha_votado")
    public void createFavoriteDuplicatedBreed() {
        doPost(new FavoriteRequest("sing", 1));
        ResponseEntity<String> postResponse = doPost(new FavoriteRequest("sing", 1));

        assertThat(postResponse).isNotNull();
        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(postResponse.getBody()).isNotNull();
        assertTrue(postResponse.getBody().contains("Voce ja possui este gato como favorito - Singapura"));
    }

    @Test
    @DisplayName("deve_deletar_favorito")
    public void deleteFavorite() {
        populateDatabase();
        ResponseEntity<String> postResponse = doPost(new FavoriteRequest("mala", 1));
        String postResource = extractUrlContext(postResponse);

        ResponseEntity<String> deleteResponse = doDelete(postResource);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<FavoriteResponse> getResponse = doGet(postResource);

        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(getResponse.getBody()).isNotNull();

        ResponseEntity<PageableResponse<ListFavoriteResponse>> getPage = doGet();

        assertThat(getPage).isNotNull();

        PageableResponse<ListFavoriteResponse> pageFavorites = getPage.getBody();

        assertThat(pageFavorites).isNotNull();
        assertThat(pageFavorites.getNumberOfElements()).isEqualTo(3);
        assertThat(pageFavorites.getNumber()).isEqualTo(0);
        assertThat(pageFavorites.getSize()).isEqualTo(10);
    }

}

