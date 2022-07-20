package com.corcino.catlovers.domain.breed.api;

import com.corcino.catlovers.domain.breed.dto.BreedResponse;
import com.corcino.catlovers.domain.breed.service.BreedService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/breed")
@Tag(name = "/api/v1/breed", description = "API's group for list cat breed")
public class BreedController {

    private final BreedService breedService;

    @Autowired
    public BreedController(BreedService breedService) {
        this.breedService = breedService;
    }
    
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "API to list breed")
    @Parameters(value = { @Parameter(name = "name", in = ParameterIn.QUERY) })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returned list successfully"),
            @ApiResponse(responseCode = "401", description = "Authentication error"),
            @ApiResponse(responseCode = "500", description = "Internal error server")
    })
    public ResponseEntity<List<BreedResponse>> list(@RequestParam(required = false) String name) throws Exception {
        List<BreedResponse> breedResponses = breedService.listBreed(name);
        return ResponseEntity.ok(breedResponses);
    }
    
}
