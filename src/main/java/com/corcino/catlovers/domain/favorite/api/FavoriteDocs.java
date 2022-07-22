package com.corcino.catlovers.domain.favorite.api;

import com.corcino.catlovers.domain.favorite.dto.FavoriteResponse;
import com.corcino.catlovers.domain.favorite.dto.ListFavoriteResponse;
import com.corcino.catlovers.domain.favorite.dto.FavoriteRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.util.UriComponentsBuilder;

import static org.springframework.data.domain.Sort.Direction.ASC;

@Tag(name = "favorite", description = "API's group for manipulation favorites")
public interface FavoriteDocs {

    @Operation(description = "API to create a favorite")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Favorite created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal error server")
    })
    ResponseEntity<String> create(FavoriteRequest favoriteRequest, UriComponentsBuilder uriBuilder) throws Exception;


    @Operation(description = "API to list all registered favorites")
    @Parameters(value = {
            @Parameter(name = "page", in = ParameterIn.QUERY),
            @Parameter(name = "size", in = ParameterIn.QUERY),
            @Parameter(name = "sort", in = ParameterIn.QUERY),
            @Parameter(name = "direction", in = ParameterIn.QUERY),
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Favorite created successfully")
    })
    ResponseEntity<Page<ListFavoriteResponse>> list(@PageableDefault(sort = "favoriteId", direction = ASC) Pageable pageable);


    @Operation(description = "API to find favorite by id")
    @Parameters(value = {
            @Parameter(name = "favoriteId", in = ParameterIn.PATH)
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Favorite ok return"),
            @ApiResponse(responseCode = "404", description = "Favorite not found"),
            @ApiResponse(responseCode = "500", description = "Internal error server")
    })
    ResponseEntity<FavoriteResponse> getById(@PathVariable Long favoriteId);


    @Operation(description = "API to delete favorite by id")
    @Parameters(value = {
            @Parameter(name = "favoriteId", in = ParameterIn.PATH)
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Favorite delete successfully"),
            @ApiResponse(responseCode = "404", description = "Favorite not found"),
            @ApiResponse(responseCode = "500", description = "Internal error server")
    })
    ResponseEntity<Void> delete(@PathVariable Long favoriteId);
}
