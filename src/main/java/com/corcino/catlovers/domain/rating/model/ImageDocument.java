package com.corcino.catlovers.domain.rating.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class ImageDocument {

    private String id;
    private String url;

}
