package com.corcino.catlovers.domain.favorite.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long favoriteId;
    private Integer value;

    @OneToOne(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private Breed breed;
    private LocalDateTime creationDate = LocalDateTime.now();

    public Favorite(Integer value, Breed breed) {
        this.value = value;
        this.breed = breed;
    }

}
