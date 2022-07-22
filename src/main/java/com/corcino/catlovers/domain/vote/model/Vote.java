package com.corcino.catlovers.domain.vote.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long voteId;
    private Integer value;

    @OneToOne(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private Breed breed;
    private LocalDateTime creationDate = LocalDateTime.now();

    public Vote(Integer value, Breed breed) {
        this.value = value;
        this.breed = breed;
    }

}
