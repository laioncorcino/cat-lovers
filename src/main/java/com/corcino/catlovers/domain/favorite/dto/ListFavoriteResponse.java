package com.corcino.catlovers.domain.favorite.dto;

import com.corcino.catlovers.domain.favorite.model.Favorite;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ListFavoriteResponse {

    private Long favoriteId;
    private String name;
    private String cfa_url;
    private String vetstreet_url;
    private String vcahospitals_url;
    private String temperament;
    private String origin;
    private String image;

    public ListFavoriteResponse(Favorite favorite) {
        this.favoriteId = favorite.getFavoriteId();
        this.name = favorite.getBreed().getName();
        this.cfa_url = favorite.getBreed().getCfa_url();
        this.vetstreet_url = favorite.getBreed().getVetstreet_url();
        this.vcahospitals_url = favorite.getBreed().getVcahospitals_url();
        this.temperament = favorite.getBreed().getTemperament();
        this.origin = favorite.getBreed().getOrigin();
        this.image = favorite.getBreed().getImage().getUrl();
    }

    public Page<ListFavoriteResponse> convertList(Page<Favorite> votes) {
        return votes.map(ListFavoriteResponse::new);
    }

}
