package com.corcino.catlovers.unit.rating.controller;

import com.corcino.catlovers.domain.rating.dto.RatingRequest;
import com.corcino.catlovers.domain.rating.service.RatingService;
import com.corcino.catlovers.error.exception.NotFoundException;
import com.corcino.catlovers.unit.util.JsonUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static com.corcino.catlovers.unit.rating.builder.RatingCreator.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RatingControllerTest extends InfraControllerRating {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RatingService ratingService;

    @Test
    @DisplayName("deve_retornar_rating_pelo_id")
    public void getRatingById() throws Exception {
        BDDMockito.when(ratingService.getRating(eq("abc"))).thenReturn(buildRatingArabianResponse());

        MockHttpServletRequestBuilder getRequest = doGet("/abc");

        mockMvc.perform(getRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ratingId").value("abc"))
                .andExpect(jsonPath("$.intelligent").value(4))
                .andExpect(jsonPath("$.breed.origin").value("United Arab Emirates"));
    }

    @Test
    @DisplayName("deve_retornar_nao_encontrado_buscando_por_id_inexistente")
    public void getRatingByIdNotFound() throws Exception {
        Mockito.when(ratingService.getRating(eq("not_found"))).thenThrow(new NotFoundException("Rating nao encontrado"));

        MockHttpServletRequestBuilder getRequest = doGet("/not_found");

        mockMvc.perform(getRequest)
                .andExpect(status().isNotFound())
                .andExpect(content().json("{'title': 'Object Not Found Exception. Check documentation'}"))
                .andExpect(content().json("{'errorMessage': 'Rating nao encontrado'}"));
    }

    @Test
    @DisplayName("deve_salvar_rating_com_sucesso")
    public void createRating() throws Exception {
        Mockito.when(ratingService.registerRating(any(RatingRequest.class))).thenReturn(buildRatingAegan());

        RatingRequest ratingRequest = buildRatingRequest();

        MockHttpServletRequestBuilder postRequest = doPost(JsonUtil.toJson(ratingRequest));

        mockMvc.perform(postRequest)
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", RATING_API + "/cba"));
    }

    @Test
    @DisplayName("deve_validar_request_para_criar_rating")
    public void validateRequest() throws Exception {
        String ratingRequest = JsonUtil.toJson(new RatingRequest());
        MockHttpServletRequestBuilder postRequest = doPost(ratingRequest);

        mockMvc.perform(postRequest)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @DisplayName("deve_deletar_rating")
    public void deleteRating() throws Exception {
        Mockito.doNothing().when(ratingService).deleteRating(eq("bca"));

        MockHttpServletRequestBuilder deleteRequest = doDelete("/bca");

        mockMvc.perform(deleteRequest)
                .andExpect(status().isNoContent());
    }

}
