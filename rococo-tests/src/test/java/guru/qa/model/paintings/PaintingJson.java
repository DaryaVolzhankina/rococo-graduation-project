package guru.qa.model.paintings;

import com.fasterxml.jackson.annotation.JsonProperty;
import guru.qa.db.model.painting.PaintingEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.annotation.Nullable;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
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
