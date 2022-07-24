package com.corcino.catlovers.domain.rating.model;

import com.corcino.catlovers.domain.rating.dto.RatingRequest;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document
@NoArgsConstructor
public class RatingDocument {

    @Id
    private String ratingId;
    private Integer familiar;
    private Integer clean;
    private Integer independent;
    private Integer curious;
    private Integer hunter;
    private Integer emotional;
    private Integer intelligent;
    private Integer sociable;
    private BreedDocument breedDocument;
    private LocalDateTime creationDate = LocalDateTime.now();

    public RatingDocument(RatingRequest ratingRequest, BreedDocument breedDocument) {
        this.familiar = ratingRequest.getFamiliar();
        this.clean = ratingRequest.getClean();
        this.independent = ratingRequest.getIndependent();
        this.curious = ratingRequest.getCurious();
        this.hunter = ratingRequest.getHunter();
        this.emotional = ratingRequest.getEmotional();
        this.intelligent = ratingRequest.getIntelligent();
        this.sociable = ratingRequest.getSociable();
        this.breedDocument = breedDocument;
    }

}
