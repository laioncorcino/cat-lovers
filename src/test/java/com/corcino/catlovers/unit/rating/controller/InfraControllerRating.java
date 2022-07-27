package com.corcino.catlovers.unit.rating.controller;

import com.corcino.catlovers.domain.rating.api.RatingController;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = RatingController.class)
@AutoConfigureMockMvc
public abstract class InfraControllerRating {

    protected static final String RATING_API = "http://localhost:8080/api/v1/rating";

    protected MockHttpServletRequestBuilder doGet(String path) {
        return get(RATING_API + path)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
    }

    protected MockHttpServletRequestBuilder doPost(String favoriteRequestJson) {
        return post(RATING_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(favoriteRequestJson);
    }

    protected MockHttpServletRequestBuilder doDelete(String favoriteId) {
        return delete(RATING_API + favoriteId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
    }

}
