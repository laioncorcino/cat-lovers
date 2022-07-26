package com.corcino.catlovers.unit.favorite.util;

import com.corcino.catlovers.domain.breed.dto.BreedResponse;
import com.corcino.catlovers.domain.breed.dto.ImageResponse;
import com.corcino.catlovers.domain.favorite.dto.FavoriteResponse;
import com.corcino.catlovers.domain.favorite.dto.ListFavoriteResponse;
import com.corcino.catlovers.domain.favorite.mapper.FavoriteMapper;
import com.corcino.catlovers.domain.favorite.model.Breed;
import com.corcino.catlovers.domain.favorite.model.Favorite;
import com.corcino.catlovers.domain.favorite.model.Image;

public class FavoriteCreator {

    private final static FavoriteMapper favorite_mapper = FavoriteMapper.INSTANCE;

    public static Favorite buildFavoriteArabian() {
        Breed arabianMauBreed = createArabianMauBreed();

        Favorite favorite = new Favorite(1, arabianMauBreed);
        favorite.setFavoriteId(1L);

        return favorite;
    }

    public static Favorite buildFavoriteAegan() {
        Breed aeganBreed = createAeganBreed();

        Favorite favorite = new Favorite(1, aeganBreed);
        favorite.setFavoriteId(2L);

        return favorite;
    }

    public static FavoriteResponse buildFavoriteResponseAegan() {
        Favorite favorite = buildFavoriteAegan();
        return favorite_mapper.toResponse(favorite);
    }

    public static FavoriteResponse buildFavoriteResponseArabian() {
        Favorite favorite = buildFavoriteArabian();
        return favorite_mapper.toResponse(favorite);
    }

    public static ListFavoriteResponse buildFavoriteListArabianResponse() {
        ListFavoriteResponse favorite = new ListFavoriteResponse();
        favorite.setFavoriteId(1L);
        favorite.setName("Arabian Mau");
        favorite.setCfa_url("");
        favorite.setVetstreet_url("");
        favorite.setTemperament("Affectionate, Agile, Curious, Independent, Playful, Loyal");
        favorite.setOrigin("United Arab Emirates");
        favorite.setImage("https://cdn2.thecatapi.com/images/k71ULYfRr.jpg");

        return favorite;
    }

    public static ListFavoriteResponse buildFavoriteListAeganResponse() {
        ListFavoriteResponse favorite = new ListFavoriteResponse();
        favorite.setFavoriteId(2L);
        favorite.setName("Aegean");
        favorite.setCfa_url("");
        favorite.setVetstreet_url("http://www.vetstreet.com/cats/aegean-cat");
        favorite.setTemperament("Affectionate, Social, Intelligent, Playful, Active");
        favorite.setOrigin("Greece");
        favorite.setImage("https://cdn2.thecatapi.com/images/ozEvzdVM-.jpg");

        return favorite;
    }

    private static Image createArabianImage() {
        Image image = new Image();
        image.setId("k71ULYfRr");
        image.setUrl("https://cdn2.thecatapi.com/images/k71ULYfRr.jpg");
        return image;
    }

    private static Image createAeganImage() {
        Image image = new Image();
        image.setId("ozEvzdVM-");
        image.setUrl("https://cdn2.thecatapi.com/images/ozEvzdVM-.jpg");
        return image;
    }

    private static Breed createArabianMauBreed() {
        Breed breed = new Breed();
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
        breed.setLap("");
        breed.setAdaptability(3);
        breed.setAffection_level(4);
        breed.setChild_friendly(3);
        breed.setDog_friendly(4);
        breed.setEnergy_level(2);
        breed.setHealth_issues(5);
        breed.setIntelligence(5);
        breed.setSocial_needs(1);
        breed.setStranger_friendly(3);
        breed.setRare(5);
        breed.setImage(createArabianImage());
        return breed;
    }

    public static Breed createAeganBreed() {
        Breed breed = new Breed();
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
        breed.setLap("");
        breed.setAdaptability(4);
        breed.setAffection_level(1);
        breed.setChild_friendly(5);
        breed.setDog_friendly(2);
        breed.setEnergy_level(2);
        breed.setHealth_issues(1);
        breed.setIntelligence(3);
        breed.setSocial_needs(3);
        breed.setStranger_friendly(1);
        breed.setRare(2);
        breed.setImage(createAeganImage());
        return breed;
    }

    public static BreedResponse createAeganBreedResponse() {
        BreedResponse breed = new BreedResponse();
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
        breed.setLap("");
        breed.setAdaptability(4);
        breed.setAffection_level(1);
        breed.setChild_friendly(5);
        breed.setDog_friendly(2);
        breed.setEnergy_level(2);
        breed.setHealth_issues(1);
        breed.setIntelligence(3);
        breed.setSocial_needs(3);
        breed.setStranger_friendly(1);
        breed.setRare(2);
        breed.setImage(createAeganImageResponse());
        return breed;
    }

    private static ImageResponse createAeganImageResponse() {
        ImageResponse image = new ImageResponse();
        image.setId("ozEvzdVM-");
        image.setUrl("https://cdn2.thecatapi.com/images/ozEvzdVM-.jpg");
        return image;
    }

    private static BreedResponse createArabianMauBreedResponse() {
        BreedResponse breed = new BreedResponse();
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
        breed.setLap("");
        breed.setAdaptability(3);
        breed.setAffection_level(4);
        breed.setChild_friendly(3);
        breed.setDog_friendly(4);
        breed.setEnergy_level(2);
        breed.setHealth_issues(5);
        breed.setIntelligence(5);
        breed.setSocial_needs(1);
        breed.setStranger_friendly(3);
        breed.setRare(5);
        breed.setImage(createArabianImageResponse());
        return breed;
    }

    private static ImageResponse createArabianImageResponse() {
        ImageResponse image = new ImageResponse();
        image.setId("k71ULYfRr");
        image.setUrl("https://cdn2.thecatapi.com/images/k71ULYfRr.jpg");
        return image;
    }
}
