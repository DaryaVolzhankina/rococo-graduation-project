package guru.qa.model.museum;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class CityJson {

    @JsonProperty("id")
    private UUID id;
    @JsonProperty("city")
    private String city;
    @JsonProperty("country")
    private CountryJson country;
}
