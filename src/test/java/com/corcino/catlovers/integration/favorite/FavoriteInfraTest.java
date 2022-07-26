package com.corcino.catlovers.integration.favorite;

import com.corcino.catlovers.domain.favorite.dto.FavoriteRequest;
import com.corcino.catlovers.domain.favorite.dto.FavoriteResponse;
import com.corcino.catlovers.domain.favorite.dto.ListFavoriteResponse;
import com.corcino.catlovers.integration.wrapper.PageableResponse;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Objects;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = DEFINED_PORT)
public abstract class FavoriteInfraTest {

    private static final String FAVORITE_API = "http://localhost:8080/api/v1/favorite";

    protected TestRestTemplate testRestTemplate = new TestRestTemplate();

    protected ResponseEntity<PageableResponse<ListFavoriteResponse>> doGet() {
        return testRestTemplate.exchange(
                FAVORITE_API,
                HttpMethod.GET,
                new HttpEntity<>(createHeader()),
                new ParameterizedTypeReference<>() {}
        );
    }

    protected ResponseEntity<?> doGetById(Long favoriteId) {
        return testRestTemplate.exchange(
                FAVORITE_API + favoriteId,
                HttpMethod.GET,
                new HttpEntity<>(createHeader()),
                new ParameterizedTypeReference<>() {}
        );
    }

    protected ResponseEntity<FavoriteResponse> doGet(String resource) {
        return testRestTemplate.exchange(
                "http://localhost:8080" + resource,
                HttpMethod.GET,
                new HttpEntity<>(createHeader()),
                new ParameterizedTypeReference<>() {}
        );
    }

    protected ResponseEntity<String> doPost(FavoriteRequest favoriteRequest) {
        return testRestTemplate.postForEntity(
                FAVORITE_API,
                favoriteRequest,
                String.class
        );
    }

    protected ResponseEntity<String> doDelete(String resource) {
        return testRestTemplate.exchange(
                "http://localhost:8080" + resource,
                HttpMethod.DELETE,
                new HttpEntity<>(createHeader()),
                String.class
        );
    }

    protected HttpHeaders createHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        return headers;
    }

    protected void populateDatabase() {
        doPost(new FavoriteRequest("siam", 1));
        doPost(new FavoriteRequest("hima", 1));
        doPost(new FavoriteRequest("pers", 1));
    }

    protected String extractUrlContext(ResponseEntity<String> postResponse) {
        return Objects.requireNonNull(postResponse.getHeaders().getLocation()).getPath();
    }

}
