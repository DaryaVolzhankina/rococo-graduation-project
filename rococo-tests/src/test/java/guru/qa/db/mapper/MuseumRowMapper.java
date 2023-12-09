package guru.qa.db.mapper;

import guru.qa.db.model.museum.MuseumEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class MuseumRowMapper implements RowMapper<MuseumEntity> {

    public static final MuseumRowMapper INSTANCE = new MuseumRowMapper();

    @Override
    public MuseumEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        MuseumEntity museum = new MuseumEntity();
        museum.setId((UUID) rs.getObject("id"));
        museum.setTitle(rs.getString("title"));
        museum.setDescription(rs.getString("description"));
        museum.setPhoto(rs.getBytes("photo"));
        museum.setGeo((UUID) rs.getObject("geo"));
        return museum;
    }
}
