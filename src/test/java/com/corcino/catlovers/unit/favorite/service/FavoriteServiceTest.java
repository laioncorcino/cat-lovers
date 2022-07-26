package com.corcino.catlovers.unit.favorite.service;

import com.corcino.catlovers.domain.breed.service.BreedService;
import com.corcino.catlovers.domain.favorite.dto.FavoriteRequest;
import com.corcino.catlovers.domain.favorite.dto.FavoriteResponse;
import com.corcino.catlovers.domain.favorite.dto.ListFavoriteResponse;
import com.corcino.catlovers.domain.favorite.model.Favorite;
import com.corcino.catlovers.domain.favorite.repository.FavoriteRepository;
import com.corcino.catlovers.domain.favorite.service.FavoriteService;
import com.corcino.catlovers.error.exception.BadRequestException;
import com.corcino.catlovers.error.exception.NotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.corcino.catlovers.unit.favorite.util.FavoriteCreator.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class FavoriteServiceTest {

    @InjectMocks
    private FavoriteService favoriteService;

    @Mock
    private FavoriteRepository favoriteRepository;

    @Mock
    private BreedService breedService;

    @Test
    @DisplayName("deve_retornar_lista_paginada_de_favoritos")
    public void listFavoritePaginated() {
        List<Favorite> favoriteList = Arrays.asList(buildFavoriteArabian(), buildFavoriteAegan());
        PageImpl<Favorite> favoriteListPaginated = new PageImpl<>(favoriteList, buildPageable(), 2);

        Mockito.when(favoriteRepository.findAll(any(PageRequest.class))).thenReturn(favoriteListPaginated);
        Page<ListFavoriteResponse> favorites = favoriteService.listFavorites(buildPageable());

        assertFalse(favorites.isEmpty());
        assertThat(favorites.getTotalElements()).isEqualTo(2);

        Pageable pageable = favorites.getPageable();

        assertThat(pageable.getSort().toString()).isEqualTo("favoriteId: ASC");
        assertThat(pageable.getPageNumber()).isEqualTo(0);
        assertThat(pageable.getPageSize()).isEqualTo(10);

        List<ListFavoriteResponse> listContent = favorites.getContent();

        assertThat(listContent.size()).isEqualTo(2);
        assertTrue(listContent.stream().anyMatch(favorite -> favorite.getName().equals("Arabian Mau")));
        assertTrue(listContent.stream().anyMatch(favorite -> favorite.getName().equals("Aegean")));
    }

    @Test
    @DisplayName("deve_retornar_favorito_buscando_por_id")
    public void getFavoriteById() {
        Mockito.when(favoriteRepository.findById(1L)).thenReturn(Optional.of((buildFavoriteArabian())));
        FavoriteResponse favorite = favoriteService.getFavorite(1L);

        assertThat(favorite).isNotNull();
        assertThat(favorite.getBreed().getName()).isEqualTo("Arabian Mau");
        assertThat(favorite.getBreed().getId()).isEqualTo("amau");
    }

    @Test
    @DisplayName("deve_tratar_exception_recebida_ao_nao_encontrar_favorito_pelo_id")
    public void getFavoriteByIdNotFound() {
        Mockito.when(favoriteRepository.findById(100L)).thenReturn(Optional.empty());

        Throwable exception = catchThrowable(() -> favoriteService.getFavorite(100L));

        assertThat(exception)
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Favorito nao encontrado");
    }


    @Test
    @DisplayName("deve_salvar_gato_favorito_com_sucesso")
    public void createFavorite() throws Exception {
        Mockito.when(breedService.findCatByBreedId(eq("aege"))).thenReturn(createAeganBreedResponse());
        Mockito.when(favoriteRepository.save(any(Favorite.class))).thenReturn(buildFavoriteAegan());

        FavoriteRequest favoriteRequest = new FavoriteRequest();
        favoriteRequest.setBreedId("aege");
        favoriteRequest.setValue(1);

        Favorite favorite = favoriteService.registerFavorite(favoriteRequest);

        assertThat(favorite).isNotNull();
        assertThat(favorite.getFavoriteId()).isNotNull();
        assertThat(favorite.getFavoriteId()).isEqualTo(2L);
        assertThat(favorite.getBreed().getId()).isEqualTo("aege");
    }

    @Test
    @DisplayName("nao_deve_salvar_gato_como_favorito_caso_ja_tenha_votado")
    public void createFavoriteDuplicatedBreed() throws Exception {
        Mockito.when(breedService.findCatByBreedId(eq("aege"))).thenReturn(createAeganBreedResponse());
        Mockito.when(favoriteRepository.save(any(Favorite.class))).thenThrow(DataIntegrityViolationException.class);

        FavoriteRequest favoriteRequest = new FavoriteRequest();
        favoriteRequest.setBreedId("aege");
        favoriteRequest.setValue(1);

        Throwable exception = catchThrowable(() -> favoriteService.registerFavorite(favoriteRequest));

        assertThat(exception)
                .isInstanceOf(BadRequestException.class)
                .hasMessage("Voce ja possui este gato como favorito - Aegean");
    }

    @Test
    @DisplayName("deve_deletar_favorito")
    public void deleteFavorite() {
        Mockito.when(favoriteRepository.findById(1L)).thenReturn(Optional.of(buildFavoriteArabian()));
        Mockito.doNothing().when(favoriteRepository).deleteById(eq(1L));

        favoriteService.deleteFavorite(1L);
    }

    @Test
    @DisplayName("should_return_not_found_on_delete_with_id_nonexistent")
    public void deleteFavoriteWithFavoriteIdNotFound() {
        Mockito.when(favoriteRepository.findById(1L)).thenReturn(Optional.empty());

        Throwable exception = catchThrowable(() -> favoriteService.deleteFavorite(1L));

        assertThat(exception)
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Favorito nao encontrado");
    }

    private Pageable buildPageable() {
        return PageRequest.of(0, 10, Sort.Direction.ASC, "favoriteId");
    }

}
