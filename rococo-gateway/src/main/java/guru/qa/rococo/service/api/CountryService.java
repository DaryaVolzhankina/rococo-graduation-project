package guru.qa.rococo.service.api;

import guru.qa.rococo.model.CountryJson;
import guru.qa.rococo.service.RestPageImpl;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class CountryService {
    private final WebClient webClient;
    private final String rococoGeoBaseUri;

    @Autowired
    public CountryService(WebClient webClient,
                          @Value("${rococo-museum.base-uri}") String rococoGeoBaseUri) {
        this.webClient = webClient;
        this.rococoGeoBaseUri = rococoGeoBaseUri;
    }

    public @Nonnull
    Page<CountryJson> getAll(@Nonnull Pageable pageable) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        params.add("size", String.valueOf(pageable.getPageSize()));
        params.add("page", String.valueOf(pageable.getPageNumber()));

        URI uri = UriComponentsBuilder.fromHttpUrl(rococoGeoBaseUri + "/api/country")
                .queryParams(params)
                .build()
                .toUri();

        ResponseEntity<RestPageImpl<CountryJson>> block = webClient.get()
                .uri(uri)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<RestPageImpl<CountryJson>>() {
                })
                .block();

        return block.getBody();
    }
}
