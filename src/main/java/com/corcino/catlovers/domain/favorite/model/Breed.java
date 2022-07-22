package com.corcino.catlovers.domain.favorite.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Breed {

    @Id
    private String id;
    private String name;
    private String cfa_url;
    private String vetstreet_url;
    private String vcahospitals_url;
    private String temperament;
    private String origin;
    private String country_code;

    @Column(length = 1024)
    private String description;
    private String life_span;
    private String lap;
    private Integer adaptability;
    private Integer affection_level;
    private Integer child_friendly;
    private Integer dog_friendly;
    private Integer energy_level;
    private Integer health_issues;
    private Integer intelligence;
    private Integer social_needs;
    private Integer stranger_friendly;
    private Integer rare;

    @OneToOne(cascade= CascadeType.ALL, fetch = FetchType.LAZY)
    private Image image;

}
