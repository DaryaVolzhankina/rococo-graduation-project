package guru.qa.niffler.model.artist;

import com.fasterxml.jackson.annotation.JsonProperty;
import guru.qa.niffler.db.model.artist.ArtistEntity;
import guru.qa.niffler.model.paintings.PaintingJson;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class ArtistJson {
    @JsonProperty("id")
    private UUID id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("biography")
    private String biography;
    @JsonProperty("photo")
    private String photo;
    private List<PaintingJson> paintings;

    public static ArtistJson fromEntity(ArtistEntity entity) {
        ArtistJson usr = new ArtistJson();
        byte[] photo = entity.getPhoto();
        usr.setId(entity.getId());
        usr.setBiography(entity.getBiography());
        usr.setName(entity.getName());
        usr.setPhoto(photo != null && photo.length > 0 ? new String(entity.getPhoto(), StandardCharsets.UTF_8) : null);
        return usr;
    }

    public static ArtistEntity toEntity(ArtistJson artistJson) {
        ArtistEntity usr = new ArtistEntity();
        String photo = artistJson.getPhoto();
        usr.setId(artistJson.getId());
        usr.setBiography(artistJson.getBiography());
        usr.setName(artistJson.getName());
        if (photo != null) usr.setPhoto(photo.getBytes(StandardCharsets.UTF_8));
        return usr;
    }
}
