package com.corcino.catlovers.integration.breed;

import com.corcino.catlovers.domain.breed.dto.BreedResponse;
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

import java.util.List;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = DEFINED_PORT)
public abstract class BreedInfraTest {

    private static final String BREED_API = "http://localhost:8080/api/v1/breed";

    protected TestRestTemplate testRestTemplate = new TestRestTemplate();

    protected ResponseEntity<List<BreedResponse>> doGet() {
        return testRestTemplate.exchange(
                BREED_API,
                HttpMethod.GET,
                new HttpEntity<>(createHeader()),
                new ParameterizedTypeReference<>() {}
        );
    }

    protected ResponseEntity<List<BreedResponse>> doGetWithQuery(String query) {
        return testRestTemplate.exchange(
                BREED_API + query,
                HttpMethod.GET,
                new HttpEntity<>(createHeader()),
                new ParameterizedTypeReference<>() {}
        );
    }

    protected HttpHeaders createHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        return headers;
    }

}
