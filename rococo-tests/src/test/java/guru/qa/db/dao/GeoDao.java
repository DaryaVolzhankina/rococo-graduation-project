package guru.qa.db.dao;

import guru.qa.db.model.museum.CityEntity;
import guru.qa.db.model.museum.CountryEntity;

import java.util.UUID;

public interface GeoDao {
    int createCity(CityEntity city);
    void deleteCityById(UUID id);
    CountryEntity getCountryByName(String name);
}
