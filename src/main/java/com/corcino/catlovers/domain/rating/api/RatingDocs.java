package com.corcino.catlovers.domain.rating.api;

import com.corcino.catlovers.domain.rating.dto.RatingRequest;
import com.corcino.catlovers.domain.rating.dto.RatingResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@Tag(name = "rating", description = "API's group for manipulation rating")
public interface RatingDocs {

    @Operation(description = "API to find rating by id")
    @Parameters(value = {
            @Parameter(name = "ratingId", in = ParameterIn.PATH)
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rating ok return"),
            @ApiResponse(responseCode = "404", description = "Rating not found"),
            @ApiResponse(responseCode = "500", description = "Internal error server")
    })
    ResponseEntity<RatingResponse> getById(@PathVariable String ratingId);


    @Operation(description = "API to create a rating")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Rating created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal error server")
    })
    ResponseEntity<String> create(@Valid @RequestBody RatingRequest ratingRequest, UriComponentsBuilder uriBuilder) throws Exception;


    @Operation(description = "API to delete rating by id")
    @Parameters(value = {
            @Parameter(name = "ratingId", in = ParameterIn.PATH)
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Rating delete successfully"),
            @ApiResponse(responseCode = "404", description = "Rating not found"),
            @ApiResponse(responseCode = "500", description = "Internal error server")
    })
    ResponseEntity<Void> delete(@PathVariable String ratingId);

}
