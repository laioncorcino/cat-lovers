package com.corcino.catlovers.unit.favorite.controller;

import com.corcino.catlovers.domain.favorite.dto.FavoriteRequest;
import com.corcino.catlovers.domain.favorite.dto.ListFavoriteResponse;
import com.corcino.catlovers.domain.favorite.service.FavoriteService;
import com.corcino.catlovers.error.exception.NotFoundException;
import com.corcino.catlovers.unit.util.JsonUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Arrays;
import java.util.List;

import static com.corcino.catlovers.unit.favorite.builder.FavoriteCreator.*;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class FavoriteControllerTest extends InfraControllerFavorite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FavoriteService favoriteService;

    @Test
    @DisplayName("deve_retornar_lista_paginada_de_favoritos")
    public void listFavoritePaginated() throws Exception {
        List<ListFavoriteResponse> favoriteList = Arrays.asList(buildFavoriteListArabianResponse(), buildFavoriteListAeganResponse());
        PageImpl<ListFavoriteResponse> favoriteListPaginated = new PageImpl<>(favoriteList, buildPageable(), 2);

        BDDMockito.when(favoriteService.listFavorites(any())).thenReturn(favoriteListPaginated);

        MockHttpServletRequestBuilder getRequest = doGet("/");

        mockMvc.perform(getRequest)
                .andExpect(status().isOk())
                .andExpect(content().json("{'totalElements': 2}"))
                .andExpect(content().json("{'size': 10}"))
                .andExpect(content().string(containsString("Arabian Mau")))
                .andExpect(content().string(containsString("Aegean")));
    }


    @Test
    @DisplayName("deve_retornar_favorito_pelo_id")
    public void getFavoriteById() throws Exception {
        BDDMockito.when(favoriteService.getFavorite(eq(2L))).thenReturn(buildFavoriteResponseAegan());

        MockHttpServletRequestBuilder getRequest = doGet("/2");

        mockMvc.perform(getRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.favoriteId").value(2L))
                .andExpect(jsonPath("$.breed.temperament").value("Affectionate, Social, Intelligent, Playful, Active"));
    }

    @Test
    @DisplayName("deve_retornar_nao_encontrado_buscando_por_id_inexistente")
    public void getFavoriteByIdNotFound() throws Exception {
        Mockito.when(favoriteService.getFavorite(eq(1000L))).thenThrow(new NotFoundException("Favorito nao encontrado"));

        MockHttpServletRequestBuilder getRequest = doGet("/1000");

        mockMvc.perform(getRequest)
                .andExpect(status().isNotFound())
                .andExpect(content().json("{'title': 'Object Not Found Exception. Check documentation'}"))
                .andExpect(content().json("{'errorMessage': 'Favorito nao encontrado'}"));
    }

    @Test
    @DisplayName("deve_salvar_favorito_com_sucesso")
    public void createFavorite() throws Exception {
        Mockito.when(favoriteService.registerFavorite(any(FavoriteRequest.class))).thenReturn(buildFavoriteArabian());

        FavoriteRequest favoriteRequest = new FavoriteRequest();
        favoriteRequest.setBreedId("amau");
        favoriteRequest.setValue(1);

        MockHttpServletRequestBuilder postRequest = doPost(JsonUtil.toJson(favoriteRequest));

        mockMvc.perform(postRequest)
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", FAVORITE_API + "/1"));
    }

    @Test
    @DisplayName("deve_validar_request_para_criar_favorito")
    public void validateRequest() throws Exception {
        String favoriteRequest = JsonUtil.toJson(new FavoriteRequest());
        MockHttpServletRequestBuilder postRequest = doPost(favoriteRequest);

        mockMvc.perform(postRequest)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    @DisplayName("deve_deletar_favorito")
    public void deleteFavorite() throws Exception {
        Mockito.doNothing().when(favoriteService).deleteFavorite(eq(2L));

        MockHttpServletRequestBuilder deleteRequest = doDelete("/2");

        mockMvc.perform(deleteRequest)
                .andExpect(status().isNoContent());
    }

}

