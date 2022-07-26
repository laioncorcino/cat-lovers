package com.corcino.catlovers.unit.favorite.controller;

import com.corcino.catlovers.domain.favorite.api.FavoriteController;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = FavoriteController.class)
@AutoConfigureMockMvc
public abstract class ControllerFavoriteTest {

    protected static final String FAVORITE_API = "http://localhost:8080/api/v1/favorite";

    protected MockHttpServletRequestBuilder doGet(String path) {
        return get(FAVORITE_API + path)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
    }

    protected MockHttpServletRequestBuilder doPost(String favoriteRequestJson) {
        return post(FAVORITE_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(favoriteRequestJson);
    }

    protected MockHttpServletRequestBuilder doDelete(String favoriteId) {
        return delete(FAVORITE_API + favoriteId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
    }

    protected Pageable buildPageable() {
        return PageRequest.of(0, 10, Sort.Direction.ASC, "favoriteId");
    }


}
