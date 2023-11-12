package guru.qa.rococo.service.api;

import guru.qa.rococo.model.UserJson;
import jakarta.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
public class UserDataService {
    private static final Logger LOG = LoggerFactory.getLogger(UserDataService.class);

    private final WebClient webClient;
    private final String rococoUserdataBaseUri;

    @Autowired
    public UserDataService(WebClient webClient,
                           @Value("${rococo-userdata.base-uri}") String rococoUserdataBaseUri) {
        this.webClient = webClient;
        this.rococoUserdataBaseUri = rococoUserdataBaseUri;
    }

    public @Nonnull
    UserJson updateUser(@Nonnull UserJson user) {
        return webClient.patch()
                .uri(rococoUserdataBaseUri + "/api/user")
                .body(Mono.just(user), UserJson.class)
                .retrieve()
                .bodyToMono(UserJson.class)
                .block();
    }

    public @Nonnull
    UserJson getUser(@Nonnull String username) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("username", username);
        URI uri = UriComponentsBuilder.fromHttpUrl(rococoUserdataBaseUri + "/api/user").queryParams(params).build().toUri();

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(UserJson.class)
                .block();
    }
}
