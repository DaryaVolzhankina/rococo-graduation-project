package guru.qa.db.mapper;

import guru.qa.db.model.museum.CityEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class CityRowMapper implements RowMapper<CityEntity> {
    public static final CityRowMapper INSTANCE = new CityRowMapper();

    @Override
    public CityEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        CityEntity city = new CityEntity();
        city.setId((UUID) rs.getObject("id"));
        city.setCountry((UUID) rs.getObject("country"));
        city.setCity(rs.getString("city"));
        return city;
    }
}
