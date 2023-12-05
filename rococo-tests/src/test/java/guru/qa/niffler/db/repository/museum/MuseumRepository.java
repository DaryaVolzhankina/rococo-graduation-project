package guru.qa.niffler.db.repository.museum;

import guru.qa.niffler.db.model.museum.CityEntity;
import guru.qa.niffler.db.model.museum.CountryEntity;
import guru.qa.niffler.db.model.museum.MuseumEntity;

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
