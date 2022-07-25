package com.corcino.catlovers.domain.rating.api;

import com.corcino.catlovers.domain.rating.dto.RatingRequest;
import com.corcino.catlovers.domain.rating.dto.RatingResponse;
import com.corcino.catlovers.domain.rating.model.RatingDocument;
import com.corcino.catlovers.domain.rating.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/v1/rating")
public class RatingController implements RatingDocs {

    private final RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping("/{ratingId}")
    public ResponseEntity<RatingResponse> getById(@PathVariable String ratingId) {
        RatingResponse ratingResponse = ratingService.getRating(ratingId);
        return ResponseEntity.ok(ratingResponse);
    }

    @PostMapping()
    public ResponseEntity<String> create(@Valid @RequestBody RatingRequest ratingRequest, UriComponentsBuilder uriBuilder) throws Exception {
        RatingDocument rating = ratingService.registerRating(ratingRequest);
        URI uri = uriBuilder.path("/api/v1/rating/{ratingId}").buildAndExpand(rating.getRatingId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/{ratingId}")
    public ResponseEntity<Void> delete(@PathVariable String ratingId) {
        ratingService.deleteRating(ratingId);
        return ResponseEntity.noContent().build();
    }

}
