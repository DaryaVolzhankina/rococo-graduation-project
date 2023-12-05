package guru.qa.niffler.model.paintings;

import com.fasterxml.jackson.annotation.JsonProperty;
import guru.qa.niffler.db.model.painting.PaintingEntity;

import javax.annotation.Nullable;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class PaintingJson {

    @JsonProperty("id")
    private UUID id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("description")
    private String description;
    @JsonProperty("content")
    private String content;
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

    public static PaintingJson fromEntity(PaintingEntity entity) {
        PaintingJson paintingJson = new PaintingJson();
        byte[] content = entity.getContent();
        paintingJson.setId(entity.getId());
        paintingJson.setTitle(entity.getTitle());
        paintingJson.setDescription(entity.getDescription());
        paintingJson.setMuseumId(entity.getMuseum());
        paintingJson.setArtistId(entity.getArtist());
        paintingJson.setContent(content != null && content.length > 0 ? new String(entity.getContent(), StandardCharsets.UTF_8) : null);
        return paintingJson;
    }
}
