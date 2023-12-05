package guru.qa.niffler.db.mapper;

import guru.qa.niffler.db.model.artist.ArtistEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class ArtistRowMapper implements RowMapper<ArtistEntity> {

    public static final ArtistRowMapper INSTANCE = new ArtistRowMapper();

    @Override
    public ArtistEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        ArtistEntity artist = new ArtistEntity();
        artist.setId((UUID) rs.getObject("id"));
        artist.setName(rs.getString("name"));
        artist.setBiography(rs.getString("biography"));
        artist.setPhoto(rs.getBytes("photo"));
        return artist;
    }
}
