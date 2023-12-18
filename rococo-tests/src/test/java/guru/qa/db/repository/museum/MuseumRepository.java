package guru.qa.db.repository.museum;

import guru.qa.db.model.museum.CityEntity;
import guru.qa.db.model.museum.CountryEntity;
import guru.qa.db.model.museum.MuseumEntity;

import java.util.UUID;

public interface MuseumRepository {
    void createMuseum(MuseumEntity museum);
    void deleteMuseum(MuseumEntity museum);
    void deleteMuseumAndCity(MuseumEntity museum);
    void createCity(CityEntity city);
    void deleteCityById(UUID id);
    CountryEntity getCountryByName(String name);
    CityEntity createRandomCity();
}
