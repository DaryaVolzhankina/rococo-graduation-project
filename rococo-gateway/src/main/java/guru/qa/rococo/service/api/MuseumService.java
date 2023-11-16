package guru.qa.rococo.service.api;

import guru.qa.rococo.model.MuseumJson;
import guru.qa.rococo.service.RestPageImpl;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
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
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Component
public class MuseumService {

    private final WebClient webClient;
    private final String rococoMuseumBaseUri;

    @Autowired
    public MuseumService(WebClient webClient,
                           @Value("${rococo-museum.base-uri}") String rococoMuseumBaseUri) {
        this.webClient = webClient;
        this.rococoMuseumBaseUri = rococoMuseumBaseUri;
    }

    public @Nonnull
    MuseumJson getMuseumById(@Nonnull UUID id) {
        URI uri = UriComponentsBuilder.fromHttpUrl(rococoMuseumBaseUri + "/api/museum").path("/{id}").build(id);

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(MuseumJson.class)
                .block();
    }

    public @Nonnull
    MuseumJson editMuseum(@Nonnull MuseumJson museum) {
        return webClient.patch()
                .uri(rococoMuseumBaseUri + "/api/museum")
                .body(Mono.just(museum), MuseumJson.class)
                .retrieve()
                .bodyToMono(MuseumJson.class)
                .block();
    }


    public @Nonnull
    MuseumJson addMuseum(@Nonnull MuseumJson museum) {
        return webClient.post()
                .uri(rococoMuseumBaseUri + "/api/museum")
                .body(Mono.just(museum), MuseumJson.class)
                .retrieve()
                .bodyToMono(MuseumJson.class)
                .block();
    }

    public @Nonnull
    Page<MuseumJson> getAll(@Nullable String title,
                            @Nonnull Pageable pageable) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        if (title != null) {
            params.add("title", URLEncoder.encode(title, StandardCharsets.UTF_8));
        } else {
            params.add("size", String.valueOf(pageable.getPageSize()));
            params.add("page", String.valueOf(pageable.getPageNumber()));
        }

        URI uri = UriComponentsBuilder.fromHttpUrl(rococoMuseumBaseUri + "/api/museum")
                .queryParams(params)
                .build()
                .toUri();

        ResponseEntity<RestPageImpl<MuseumJson>> block = webClient.get()
                .uri(uri)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<RestPageImpl<MuseumJson>>() {
                })
                .block();

        return block.getBody();
    }
}
