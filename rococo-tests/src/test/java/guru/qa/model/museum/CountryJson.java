package guru.qa.model.museum;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class CountryJson {
    @JsonProperty("id")
    private UUID id;
    @JsonProperty("name")
    private String name;
}
