package com.corcino.catlovers.domain.favorite.api;

import com.corcino.catlovers.domain.favorite.dto.FavoriteResponse;
import com.corcino.catlovers.domain.favorite.dto.ListFavoriteResponse;
import com.corcino.catlovers.domain.favorite.dto.FavoriteRequest;
import com.corcino.catlovers.domain.favorite.model.Favorite;
import com.corcino.catlovers.domain.favorite.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.data.domain.Sort.Direction.ASC;

@RestController
@RequestMapping("/api/v1/favorite")
public class FavoriteController implements FavoriteDocs {

    private final FavoriteService favoriteService;

    @Autowired
    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @GetMapping
    public ResponseEntity<Page<ListFavoriteResponse>> list(@PageableDefault(sort = "favoriteId", direction = ASC) Pageable pageable) {
        Page<ListFavoriteResponse> votes = favoriteService.listFavorites(pageable);
        return ResponseEntity.ok(votes);
    }

    @GetMapping("/{favoriteId}")
    public ResponseEntity<FavoriteResponse> getById(@PathVariable Long favoriteId) {
        FavoriteResponse favoriteResponse = favoriteService.getFavorite(favoriteId);
        return ResponseEntity.ok(favoriteResponse);
    }

    @PostMapping()
    public ResponseEntity<String> create(@Valid @RequestBody FavoriteRequest favoriteRequest, UriComponentsBuilder uriBuilder) throws Exception {
        Favorite favorite = favoriteService.registerFavorite(favoriteRequest);
        URI uri = uriBuilder.path("/api/v1/favorite/{favoriteId}").buildAndExpand(favorite.getFavoriteId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/{favoriteId}")
    public ResponseEntity<Void> delete(@PathVariable Long favoriteId) {
        favoriteService.deleteFavorite(favoriteId);
        return ResponseEntity.noContent().build();
    }

}
