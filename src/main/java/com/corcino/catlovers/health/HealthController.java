package com.corcino.catlovers.health;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/health")
public class HealthController {

    @GetMapping
    @Operation(description = "API to response greetings")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success method return") })
    public ResponseEntity<HealthResponse> hello() {
        return ResponseEntity.ok(new HealthResponse("Hello! Cat lovers is on!"));
    }

}
