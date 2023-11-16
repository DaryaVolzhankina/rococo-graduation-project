package guru.qa.rococo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import guru.qa.rococo.config.RococoGatewayServiceConfig;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public class ArtistJson {
    @JsonProperty("id")
    private UUID id;
    @JsonProperty("name")
    @Size(max = 50, message = "Name can`t be longer than 50 characters")
    private String name;
    @JsonProperty("biography")
    @Size(max = 255, message = "Biography can`t be longer than 255 characters")
    private String biography;
    @JsonProperty("photo")
    @Size(max = RococoGatewayServiceConfig.TWENTY_MB)
    private String photo;

    public ArtistJson() {
    }

    public ArtistJson(UUID id, String name, String biography, String photo) {
        this.id = id;
        this.name = name;
        this.biography = biography;
        this.photo = photo;
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

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
