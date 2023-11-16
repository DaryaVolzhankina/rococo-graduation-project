package guru.qa.rococo.service.api;

import guru.qa.rococo.model.ArtistJson;
import guru.qa.rococo.model.MuseumJson;
import guru.qa.rococo.model.PaintingJson;
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
public class PaintingService {

    private final WebClient webClient;
    private final String rococoPaintingBaseUri;
    private final String rococoMuseumBaseUri;
    private final String rococoArtistBaseUri;

    @Autowired
    public PaintingService(WebClient webClient,
                           @Value("${rococo-painting.base-uri}") String rococoPaintingBaseUri,
                           @Value("${rococo-museum.base-uri}") String rococoMuseumBaseUri,
                           @Value("${rococo-artist.base-uri}") String rococoArtistBaseUri) {
        this.webClient = webClient;
        this.rococoPaintingBaseUri = rococoPaintingBaseUri;
        this.rococoMuseumBaseUri = rococoMuseumBaseUri;
        this.rococoArtistBaseUri = rococoArtistBaseUri;
    }

    public @Nonnull
    PaintingJson getPaintingById(@Nonnull UUID id) {
        URI uri = UriComponentsBuilder.fromHttpUrl(rococoPaintingBaseUri + "/api/painting").path("/{id}").build(id);

        PaintingJson paintingJson = webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(PaintingJson.class)
                .block();

        if (paintingJson.getMuseumId() != null) {
            paintingJson.setMuseum(getMuseumById(paintingJson.getMuseumId()));
        }
        paintingJson.setArtist(getArtistById(paintingJson.getArtistId()));
        return paintingJson;
    }

    public @Nonnull
    PaintingJson addPainting(@Nonnull PaintingJson paintingJson) {
        paintingJson.setMuseumId(paintingJson.getMuseum().getId());
        paintingJson.setArtistId(paintingJson.getArtist().getId());

        return webClient.post()
                .uri(rococoPaintingBaseUri + "/api/painting")
                .body(Mono.just(paintingJson), PaintingJson.class)
                .retrieve()
                .bodyToMono(PaintingJson.class)
                .block();
    }

    public @Nonnull
    Page<PaintingJson> getAll(@Nullable String title,
                              @Nonnull Pageable pageable) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        if (title != null) {
            params.add("title", URLEncoder.encode(title, StandardCharsets.UTF_8));
        } else {
            params.add("size", String.valueOf(pageable.getPageSize()));
            params.add("page", String.valueOf(pageable.getPageNumber()));
        }

        URI uri = UriComponentsBuilder.fromHttpUrl(rococoPaintingBaseUri + "/api/painting")
                .queryParams(params)
                .build()
                .toUri();

        ResponseEntity<RestPageImpl<PaintingJson>> block = webClient.get()
                .uri(uri)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<RestPageImpl<PaintingJson>>() {
                })
                .block();

        return block.getBody().map(paintingJson -> {
            paintingJson.setArtist(getArtistById(paintingJson.getArtistId()));
            if (paintingJson.getMuseumId() != null) {
                paintingJson.setMuseum(getMuseumById(paintingJson.getMuseumId()));
            }
            return paintingJson;
        });
    }

    public @Nonnull
    Page<PaintingJson> getAllPaintingsByArtist(@Nonnull UUID artistId,@Nonnull Pageable pageable) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        params.add("size", String.valueOf(pageable.getPageSize()));
        params.add("page", String.valueOf(pageable.getPageNumber()));

        URI uri = UriComponentsBuilder.fromHttpUrl(rococoPaintingBaseUri + "/api/painting/author").path("/{artistId}").queryParams(params).build(artistId);

        ResponseEntity<RestPageImpl<PaintingJson>> block = webClient.get()
                .uri(uri)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<RestPageImpl<PaintingJson>>() {
                })
                .block();

        return block.getBody().map(paintingJson -> {
            paintingJson.setArtist(getArtistById(paintingJson.getArtistId()));
            if (paintingJson.getMuseumId() != null) {
                paintingJson.setMuseum(getMuseumById(paintingJson.getMuseumId()));
            }
            return paintingJson;
        });
    }

    public @Nonnull
    PaintingJson editPainting(@Nonnull PaintingJson museum) {
        museum.setMuseumId(museum.getMuseum().getId());
        museum.setArtistId(museum.getArtist().getId());

        return webClient.patch()
                .uri(rococoPaintingBaseUri + "/api/painting")
                .body(Mono.just(museum), PaintingJson.class)
                .retrieve()
                .bodyToMono(PaintingJson.class)
                .block();
    }

    ArtistJson getArtistById(@Nonnull UUID id) {
        URI uri = UriComponentsBuilder.fromHttpUrl(rococoArtistBaseUri + "/api/artist").path("/{id}").build(id);

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(ArtistJson.class)
                .block();
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
}
