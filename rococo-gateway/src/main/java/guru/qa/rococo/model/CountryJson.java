package guru.qa.rococo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public class CountryJson {
    @JsonProperty("id")
    private UUID id;
    @JsonProperty("name")
    @Size(max = 50, message = "Name can`t be longer than 50 characters")
    private String name;

    public CountryJson() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
