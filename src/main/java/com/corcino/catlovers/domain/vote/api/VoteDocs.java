package com.corcino.catlovers.domain.vote.api;

import com.corcino.catlovers.domain.vote.dto.ListVoteResponse;
import com.corcino.catlovers.domain.vote.dto.VoteRequest;
import com.corcino.catlovers.domain.vote.dto.VoteResponse;
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

@Tag(name = "vote", description = "API's group for manipulation votes")
public interface VoteDocs {

    @Operation(description = "API to create a vote")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Vote created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal error server")
    })
    ResponseEntity<String> create(VoteRequest voteRequest, UriComponentsBuilder uriBuilder) throws Exception;


    @Operation(description = "API to list all registered votes")
    @Parameters(value = {
            @Parameter(name = "page", in = ParameterIn.QUERY),
            @Parameter(name = "size", in = ParameterIn.QUERY),
            @Parameter(name = "sort", in = ParameterIn.QUERY),
            @Parameter(name = "direction", in = ParameterIn.QUERY),
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vote created successfully")
    })
    ResponseEntity<Page<ListVoteResponse>> list(@PageableDefault(sort = "voteId", direction = ASC) Pageable pageable);


    @Operation(description = "API to find vote by id")
    @Parameters(value = {
            @Parameter(name = "voteId", in = ParameterIn.PATH)
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vote ok return"),
            @ApiResponse(responseCode = "404", description = "Vote not found"),
            @ApiResponse(responseCode = "500", description = "Internal error server")
    })
    ResponseEntity<VoteResponse> getById(@PathVariable Long voteId);


    @Operation(description = "API to delete vote by id")
    @Parameters(value = {
            @Parameter(name = "voteId", in = ParameterIn.PATH)
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Vote delete successfully"),
            @ApiResponse(responseCode = "404", description = "Vote not found"),
            @ApiResponse(responseCode = "500", description = "Internal error server")
    })
    ResponseEntity<Void> delete(@PathVariable Long voteId);
}
