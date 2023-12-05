package guru.qa.niffler.db.dao.impl;

import guru.qa.niffler.db.DataSourceProvider;
import guru.qa.niffler.db.ServiceDb;
import guru.qa.niffler.db.dao.GeoDao;
import guru.qa.niffler.db.mapper.CityRowMapper;
import guru.qa.niffler.db.mapper.CountryRowMapper;
import guru.qa.niffler.db.model.museum.CityEntity;
import guru.qa.niffler.db.model.museum.CountryEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

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
        return geoJdbcTemplate.update("INSERT INTO cities (city, country) VALUES (?, ?)",
                city.getCity(), city.getCountry());
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
