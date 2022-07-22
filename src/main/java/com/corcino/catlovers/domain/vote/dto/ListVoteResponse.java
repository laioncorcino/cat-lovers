package com.corcino.catlovers.domain.vote.dto;

import com.corcino.catlovers.domain.vote.model.Vote;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ListVoteResponse {

    private Long id;
    private String name;
    private String cfa_url;
    private String vetstreet_url;
    private String vcahospitals_url;
    private String temperament;
    private String origin;
    private String image;

    public ListVoteResponse(Vote vote) {
        this.id = vote.getVoteId();
        this.name = vote.getBreed().getName();
        this.cfa_url = vote.getBreed().getCfa_url();
        this.vetstreet_url = vote.getBreed().getVetstreet_url();
        this.vcahospitals_url = vote.getBreed().getVcahospitals_url();
        this.temperament = vote.getBreed().getTemperament();
        this.origin = vote.getBreed().getOrigin();
        this.image = vote.getBreed().getImage().getUrl();
    }

    public Page<ListVoteResponse> convertList(Page<Vote> votes) {
        return votes.map(ListVoteResponse::new);
    }
}
