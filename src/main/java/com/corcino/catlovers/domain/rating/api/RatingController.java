package com.corcino.catlovers.domain.rating.api;

import com.corcino.catlovers.domain.favorite.dto.FavoriteRequest;
import com.corcino.catlovers.domain.favorite.model.Favorite;
import com.corcino.catlovers.domain.rating.dto.RatingRequest;
import com.corcino.catlovers.domain.rating.model.RatingDocument;
import com.corcino.catlovers.domain.rating.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/v1/rating")
public class RatingController {

    private final RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping()
    public ResponseEntity<String> create(@Valid @RequestBody RatingRequest ratingRequest, UriComponentsBuilder uriBuilder) throws Exception {
        RatingDocument rating = ratingService.registerRating(ratingRequest);
        URI uri = uriBuilder.path("/api/v1/rating/{ratingId}").buildAndExpand(rating.getRatingId()).toUri();
        return ResponseEntity.created(uri).build();
    }


}
