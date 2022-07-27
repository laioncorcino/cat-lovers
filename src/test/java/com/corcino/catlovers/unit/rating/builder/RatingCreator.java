package com.corcino.catlovers.unit.rating.builder;

import com.corcino.catlovers.domain.rating.dto.RatingRequest;
import com.corcino.catlovers.domain.rating.dto.RatingResponse;
import com.corcino.catlovers.domain.rating.mapper.RatingMapper;
import com.corcino.catlovers.domain.rating.model.BreedDocument;
import com.corcino.catlovers.domain.rating.model.ImageDocument;
import com.corcino.catlovers.domain.rating.model.RatingDocument;

public class RatingCreator {

    private final static RatingMapper mapper = RatingMapper.INSTANCE;

    public static RatingDocument buildRatingArabian() {
        RatingDocument rating = new RatingDocument();
        rating.setRatingId("abc");
        rating.setFamiliar(2);
        rating.setClean(4);
        rating.setIndependent(3);
        rating.setCurious(2);
        rating.setHunter(5);
        rating.setEmotional(1);
        rating.setIntelligent(4);
        rating.setSociable(3);
        rating.setBreedDocument(createArabianMauBreed());

        return rating;
    }

    public static RatingDocument buildRatingAegan() {
        RatingDocument rating = new RatingDocument();
        rating.setRatingId("cba");
        rating.setFamiliar(3);
        rating.setClean(5);
        rating.setIndependent(1);
        rating.setCurious(5);
        rating.setHunter(2);
        rating.setEmotional(4);
        rating.setIntelligent(3);
        rating.setSociable(3);
        rating.setBreedDocument(createAeganBreed());

        return rating;
    }

    public static RatingResponse buildRatingArabianResponse() {
        RatingDocument arabian = buildRatingArabian();
        return mapper.toResponse(arabian);
    }

    private static BreedDocument createArabianMauBreed() {
        BreedDocument breed = new BreedDocument();
        breed.setId("amau");
        breed.setName("Arabian Mau");
        breed.setCfa_url("");
        breed.setVetstreet_url("");
        breed.setVcahospitals_url("");
        breed.setTemperament("Affectionate, Agile, Curious, Independent, Playful, Loyal");
        breed.setOrigin("United Arab Emirates");
        breed.setCountry_code("AE");
        breed.setDescription("Arabian Mau cats are social and energetic. Due to their energy levels, these cats do best in homes where their owners will be able to provide them with plenty of playtime, attention and interaction from their owners. These kitties are friendly, intelligent, and adaptable, and will even get along well with other pets and children.");
        breed.setLife_span("12 - 14");
        breed.setImage(createArabianImage());
        return breed;
    }

    public static BreedDocument createAeganBreed() {
        BreedDocument breed = new BreedDocument();
        breed.setId("aege");
        breed.setName("Aegean");
        breed.setCfa_url("");
        breed.setVetstreet_url("http://www.vetstreet.com/cats/aegean-cat");
        breed.setVcahospitals_url("");
        breed.setTemperament("Affectionate, Social, Intelligent, Playful, Active");
        breed.setOrigin("Greece");
        breed.setCountry_code("GR");
        breed.setDescription("Native to the Greek islands known as the Cyclades in the Aegean Sea, these are natural cats, meaning they developed without humans getting involved in their breeding. As a breed, Aegean Cats are rare, although they are numerous on their home islands. They are generally friendly toward people and can be excellent cats for families with children.");
        breed.setLife_span("9 - 12");
        breed.setImage(createAeganImage());
        return breed;
    }

    private static ImageDocument createArabianImage() {
        ImageDocument image = new ImageDocument();
        image.setId("k71ULYfRr");
        image.setUrl("https://cdn2.thecatapi.com/images/k71ULYfRr.jpg");
        return image;
    }

    private static ImageDocument createAeganImage() {
        ImageDocument image = new ImageDocument();
        image.setId("ozEvzdVM-");
        image.setUrl("https://cdn2.thecatapi.com/images/ozEvzdVM-.jpg");
        return image;
    }

    public static RatingRequest buildRatingRequest() {
        RatingRequest ratingRequest = new RatingRequest();
        ratingRequest.setBreedId("aege");
        ratingRequest.setFamiliar(1);
        ratingRequest.setClean(3);
        ratingRequest.setIndependent(2);
        ratingRequest.setCurious(4);
        ratingRequest.setHunter(2);
        ratingRequest.setEmotional(3);
        ratingRequest.setIntelligent(5);
        ratingRequest.setSociable(3);
        return ratingRequest;
    }

}
