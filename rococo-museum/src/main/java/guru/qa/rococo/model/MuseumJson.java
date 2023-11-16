package guru.qa.rococo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class MuseumJson {

    @JsonProperty("id")
    private UUID id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("description")
    private String description;
    @JsonProperty("photo")
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
