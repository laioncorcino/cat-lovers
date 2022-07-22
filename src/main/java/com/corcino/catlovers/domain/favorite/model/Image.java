package com.corcino.catlovers.domain.favorite.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Image {

    @Id
    private String id;
    private String url;

}
