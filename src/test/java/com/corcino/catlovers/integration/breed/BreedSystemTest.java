package com.corcino.catlovers.integration.breed;

import com.corcino.catlovers.domain.breed.dto.BreedResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BreedSystemTest extends BreedInfraTest {

    @Test
    @DisplayName("deve_retornar_lista_de_racas")
    public void listBreeds() {
        ResponseEntity<List<BreedResponse>> response = doGet();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getHeaders()).isNotNull();
        assertThat(response.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isEqualTo(60);
    }

    @Test
    @DisplayName("deve_retornar_lista_de_racas_filtrando_por_nome")
    public void listBreedsByNameQueryParams() {
        ResponseEntity<List<BreedResponse>> response = doGetWithQuery("?name=american");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getHeaders()).isNotNull();
        assertThat(response.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);

        List<BreedResponse> body = response.getBody();

        assertThat(body).isNotNull();
        assertThat(body.size()).isEqualTo(4);

        assertTrue(body.stream().anyMatch(breed -> breed.getName().equals("American Bobtail")));
        assertTrue(body.stream().anyMatch(breed -> breed.getName().equals("American Curl")));
        assertTrue(body.stream().anyMatch(breed -> breed.getName().equals("American Shorthair")));
        assertTrue(body.stream().anyMatch(breed -> breed.getName().equals("American Wirehair")));
    }

    @Test
    @DisplayName("deve_retornar_lista_de_racas_filtrando_pelo_temperamento")
    public void listBreedsByTemperamentQueryParams() {
        ResponseEntity<List<BreedResponse>> response = doGetWithQuery("?temperament=intelligent");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getHeaders()).isNotNull();
        assertThat(response.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);

        List<BreedResponse> body = response.getBody();

        assertThat(body).isNotNull();
        assertThat(body.size()).isEqualTo(40);

        assertTrue(body.stream().allMatch(breed -> breed.getTemperament().toLowerCase().contains("intelligent")));
    }

    @Test
    @DisplayName("deve_retornar_lista_de_racas_filtrando_pela_origem")
    public void listBreedsByOriginQueryParams() {
        ResponseEntity<List<BreedResponse>> response = doGetWithQuery("?origin=egypt");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getHeaders()).isNotNull();
        assertThat(response.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);

        List<BreedResponse> body = response.getBody();

        assertThat(body).isNotNull();
        assertThat(body.size()).isEqualTo(3);

        assertTrue(body.stream().allMatch(breed -> breed.getOrigin().toLowerCase().contains("egypt")));
        assertTrue(body.stream().allMatch(breed -> breed.getCountry_code().equals("EG")));

        assertTrue(body.stream().anyMatch(breed -> breed.getName().equals("Abyssinian")));
        assertTrue(body.stream().anyMatch(breed -> breed.getName().equals("Chausie")));
        assertTrue(body.stream().anyMatch(breed -> breed.getName().equals("Egyptian Mau")));
    }

}


