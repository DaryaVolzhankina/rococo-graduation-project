package guru.qa.rococo.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import guru.qa.rococo.config.RococoGatewayServiceConfig;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Size;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaintingJson {

    @JsonProperty("id")
    private UUID id;
    @JsonProperty("title")
    @Size(max = 50, message = "Title can`t be longer than 50 characters")
    private String title;
    @JsonProperty("description")
    @Size(max = 255, message = "Title can`t be longer than 50 characters")
    private String description;
    @JsonProperty("content")
    @Size(max = RococoGatewayServiceConfig.TWENTY_MB)
    private String content;
    @JsonProperty("museum")
    @Nullable
    private MuseumJson museum;
    @JsonProperty("artist")
    private ArtistJson artist;
    @JsonProperty("museumId")
    @Nullable
    private UUID museumId;
    @JsonProperty("artistId")
    private UUID artistId;

    public PaintingJson() {
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MuseumJson getMuseum() {
        return museum;
    }

    public void setMuseum(MuseumJson museum) {
        this.museum = museum;
    }

    public ArtistJson getArtist() {
        return artist;
    }

    public void setArtist(ArtistJson artist) {
        this.artist = artist;
    }

    public UUID getMuseumId() {
        return museumId;
    }

    public void setMuseumId(UUID museumId) {
        this.museumId = museumId;
    }

    public UUID getArtistId() {
        return artistId;
    }

    public void setArtistId(UUID artistId) {
        this.artistId = artistId;
    }
}
