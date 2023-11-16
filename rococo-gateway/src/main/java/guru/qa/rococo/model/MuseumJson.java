package guru.qa.rococo.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import guru.qa.rococo.config.RococoGatewayServiceConfig;
import jakarta.validation.constraints.Size;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MuseumJson {

    @JsonProperty("id")
    private UUID id;
    @JsonProperty("title")
    @Size(max = 50, message = "Title can`t be longer than 50 characters")
    private String title;
    @JsonProperty("description")
    @Size(max = 255, message = "Name can`t be longer than 50 characters")
    private String description;
    @JsonProperty("photo")
    @Size(max = RococoGatewayServiceConfig.TWENTY_MB)
    private String photo;
    @JsonProperty("geo")
    private CityJson geo;

    public MuseumJson() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public CityJson getGeo() {
        return geo;
    }

    public void setGeo(CityJson geo) {
        this.geo = geo;
    }
}
