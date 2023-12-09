package guru.qa.db.repository.museum;

import com.github.javafaker.Faker;
import guru.qa.db.dao.MuseumDao;
import guru.qa.db.dao.impl.MuseumDaoImpl;
import guru.qa.db.dao.GeoDao;
import guru.qa.db.dao.impl.GeoDaoImpl;
import guru.qa.db.model.museum.CityEntity;
import guru.qa.db.model.museum.CountryEntity;
import guru.qa.db.model.museum.MuseumEntity;

import java.util.UUID;

public class MuseumRepositorySpringJdbc implements MuseumRepository {

    private final MuseumDao museumDao;
    private final GeoDao geoDao;

    public MuseumRepositorySpringJdbc() {
        this.museumDao = new MuseumDaoImpl();
        this.geoDao = new GeoDaoImpl();
    }

    @Override
    public void createMuseum(MuseumEntity museum) {
         museumDao.createMuseum(museum);
    }

    @Override
    public void deleteMuseum(MuseumEntity museum) {
        museumDao.deleteMuseum(museum);
    }

    @Override
    public void deleteMuseumAndCity(MuseumEntity museum) {
        museumDao.deleteMuseum(museum);
        geoDao.deleteCityById(museum.getGeo());
    }

    @Override
    public void createCity(CityEntity city) {
        geoDao.createCity(city);
    }

    @Override
    public void deleteCityById(UUID id) {
        geoDao.deleteCityById(id);
    }

    @Override
    public CountryEntity getCountryByName(String name) {
       return geoDao.getCountryByName(name);
    }

    public CityEntity createRandomCity(){
        CityEntity city = new CityEntity();
        city.setCity(new Faker().address().city());
        city.setCountry(getCountryByName("Russia").getId());
        geoDao.createCity(city);
        return city;
    }
}
