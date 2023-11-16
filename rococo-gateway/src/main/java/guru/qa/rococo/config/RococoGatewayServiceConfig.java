package guru.qa.rococo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class RococoGatewayServiceConfig {

    public static final int TWENTY_MB = 20971520;

    private final String rococoUserdataBaseUri;
    private final String rococoArtistBaseUri;

    @Autowired
    public RococoGatewayServiceConfig(@Value("${rococo-userdata.base-uri}") String rococoUserdataBaseUri,
                                      @Value("${rococo-artist.base-uri}") String rococoArtistBaseUri) {
        this.rococoUserdataBaseUri = rococoUserdataBaseUri;
        this.rococoArtistBaseUri = rococoArtistBaseUri;
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .exchangeStrategies(ExchangeStrategies.builder().codecs(
                        configurer -> configurer.defaultCodecs().maxInMemorySize(TWENTY_MB)).build())
                .build();
    }
}
