package guru.qa.db.dao.impl;

import guru.qa.db.DataSourceProvider;
import guru.qa.db.ServiceDb;
import guru.qa.db.dao.GeoDao;
import guru.qa.db.mapper.CountryRowMapper;
import guru.qa.db.model.museum.CityEntity;
import guru.qa.db.model.museum.CountryEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.UUID;

public class GeoDaoImpl implements GeoDao {
    private final TransactionTemplate geoTtpl;
    private final JdbcTemplate geoJdbcTemplate;

    public GeoDaoImpl() {
        JdbcTransactionManager geoTm = new JdbcTransactionManager(DataSourceProvider.INSTANCE.getDataSource(ServiceDb.MUSEUM));

        this.geoTtpl = new TransactionTemplate(geoTm);
        this.geoJdbcTemplate = new JdbcTemplate(geoTm.getDataSource());
    }

    @Override
    public int createCity(CityEntity city) {
        KeyHolder kh = new GeneratedKeyHolder();
        geoJdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement("INSERT INTO cities (city, country) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, city.getCity());
            ps.setObject(2, city.getCountry());
            return ps;
        }, kh);
        final UUID cityId = (UUID) kh.getKeyList().get(0).get("id");
        city.setId(cityId);
        return 1;
    }

    @Override
    public void deleteCityById(UUID id) {
        geoJdbcTemplate.update("DELETE FROM cities WHERE id = ?", id);
    }

    @Override
    public CountryEntity getCountryByName(String name) {
        return geoJdbcTemplate.queryForObject("SELECT * FROM countries WHERE name = ?", CountryRowMapper.INSTANCE, name);
    }
}
