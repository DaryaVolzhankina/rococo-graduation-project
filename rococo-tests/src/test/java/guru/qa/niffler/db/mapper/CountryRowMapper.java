package guru.qa.niffler.db.mapper;

import guru.qa.niffler.db.model.museum.CityEntity;
import guru.qa.niffler.db.model.museum.CountryEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class CountryRowMapper implements RowMapper<CountryEntity> {
    public static final CountryRowMapper INSTANCE = new CountryRowMapper();

    @Override
    public CountryEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        CountryEntity country = new CountryEntity();
        country.setId((UUID) rs.getObject("id"));
        country.setName(rs.getString("name"));
        return country;
    }
}
