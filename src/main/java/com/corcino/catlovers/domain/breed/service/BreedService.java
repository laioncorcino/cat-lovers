package com.corcino.catlovers.domain.breed.service;

import com.corcino.catlovers.domain.breed.dto.BreedResponse;
import com.corcino.catlovers.error.exception.BadRequestException;
import com.corcino.catlovers.error.exception.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BreedService {

    private final RestTemplate restTemplate;
    private final String endpoint;
    private final String apiKey;

    public BreedService(@Qualifier("breedApiTemplate") RestTemplate restTemplate,
                        @Value("${breed.api.url}") String endpoint,
                        @Value("${cat.api.key}") String apiKey) {
        this.restTemplate = restTemplate;
        this.endpoint = endpoint;
        this.apiKey = apiKey;
    }

    public List<BreedResponse> listBreed(String name, String temperament, String origin) throws Exception {
        List<BreedResponse> breedResponses = listBaseBreed(name);

        if (StringUtils.isNotBlank(temperament)) {
            return filterBreedWithTemperament(breedResponses, temperament);
        }

        if (StringUtils.isNotBlank(origin)) {
            return filterBreedWithOrigin(breedResponses, origin);
        }

        return breedResponses;
    }

    public List<BreedResponse> listBaseBreed(String name) throws Exception {
        String url = mountUrl(name);

        try {
            log.info("chamando api " + url);

            ResponseEntity<BreedResponse[]> response = restTemplate.exchange(url, HttpMethod.GET, getHeaderWithApiKey(apiKey), BreedResponse[].class);
            return Arrays.asList(Objects.requireNonNull(response.getBody()));
        }
        catch(HttpClientErrorException e) {
            if (HttpStatus.UNAUTHORIZED.equals(e.getStatusCode())) {
                log.error("voce precisa enviar sua API Key como 'x-api-key' no header");
                throw new UnauthorizedException("voce precisa enviar sua API Key como 'x-api-key' no header");
            }
            log.error("Erro ao acessar thecatapi.com/v1/breeds - " + e.getMessage());
            throw new BadRequestException("Erro ao acessar thecatapi.com/v1/breeds - " + e.getMessage());
        }
        catch (Exception e) {
            log.error("Erro ao acessar thecatapi.com/v1/breeds");
            throw new Exception("Erro ao acessar thecatapi.com/v1/breeds - " + e.getMessage());
        }
    }

    private String mountUrl(String name) {
        if (StringUtils.isNotBlank(name)) {
            return endpoint + "/search?q=" + name;
        }
        return endpoint;
    }

    public HttpEntity<String> getHeaderWithApiKey(String apiKey) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON.toString());
        headers.set("x-api-key", apiKey);
        return new HttpEntity<>(headers);
    }

    private List<BreedResponse> filterBreedWithTemperament(List<BreedResponse> list, String temperament) {
        return list.stream()
                .filter(breed -> breed.getTemperament().toLowerCase().contains(temperament.toLowerCase()))
                .collect(Collectors.toList());
    }

    private List<BreedResponse> filterBreedWithOrigin(List<BreedResponse> list, String origin) {
        return list.stream()
                .filter(breed -> breed.getOrigin().toLowerCase().contains(origin.toLowerCase()))
                .collect(Collectors.toList());
    }

    public BreedResponse findCatByBreedId(String breedId) throws Exception {
        List<BreedResponse> breedResponses = listBaseBreed(null);

        return breedResponses.stream()
                .filter(breed -> breed.getId().equals(breedId))
                .findFirst()
                .orElseThrow(() -> {
                    log.error("raca " + breedId + " desconhecida");
                    return new BadRequestException("raca " + breedId + " desconhecida - " + "consulte http://localhost:8080/api/v1/breed");
                });
    }

}

