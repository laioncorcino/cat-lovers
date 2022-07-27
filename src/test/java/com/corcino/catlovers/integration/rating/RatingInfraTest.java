package com.corcino.catlovers.integration.rating;

import com.corcino.catlovers.domain.rating.dto.RatingRequest;
import com.corcino.catlovers.domain.rating.dto.RatingResponse;
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
public abstract class RatingInfraTest {

    private static final String RATING_API = "http://localhost:8080/api/v1/rating";

    protected TestRestTemplate testRestTemplate = new TestRestTemplate();

    protected ResponseEntity<?> doGetById(String ratingId) {
        return testRestTemplate.exchange(
                RATING_API + ratingId,
                HttpMethod.GET,
                new HttpEntity<>(createHeader()),
                new ParameterizedTypeReference<>() {}
        );
    }

    protected ResponseEntity<RatingResponse> doGet(String resource) {
        return testRestTemplate.exchange(
                "http://localhost:8080" + resource,
                HttpMethod.GET,
                new HttpEntity<>(createHeader()),
                new ParameterizedTypeReference<>() {}
        );
    }

    protected ResponseEntity<String> doPost(RatingRequest ratingRequest) {
        return testRestTemplate.postForEntity(
                RATING_API,
                ratingRequest,
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
        doPost(new RatingRequest("mala", 1, 4, 5, 2, 2, 5, 2, 1));
        doPost(new RatingRequest("hima", 4, 2, 3, 4, 4, 1, 3, 3));
        doPost(new RatingRequest("pers", 1, 2, 3, 5, 5, 2, 4, 4));
    }

    protected String extractUrlContext(ResponseEntity<String> postResponse) {
        return Objects.requireNonNull(postResponse.getHeaders().getLocation()).getPath();
    }

}
