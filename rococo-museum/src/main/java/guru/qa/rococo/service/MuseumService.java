package guru.qa.rococo.service;

import guru.qa.rococo.data.CityEntity;
import guru.qa.rococo.data.CountryEntity;
import guru.qa.rococo.data.MuseumEntity;
import guru.qa.rococo.data.repository.CityRepository;
import guru.qa.rococo.data.repository.CountryRepository;
import guru.qa.rococo.data.repository.MuseumRepository;
import guru.qa.rococo.ex.NotFoundException;
import guru.qa.rococo.model.CityJson;
import guru.qa.rococo.model.CountryJson;
import guru.qa.rococo.model.MuseumJson;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Component
public class MuseumService {

    private final CountryRepository countryRepository;
    private final CityRepository cityRepository;
    private final MuseumRepository museumRepository;


    @Autowired
    public MuseumService(CountryRepository countryRepository, CityRepository cityRepository, MuseumRepository museumRepository) {
        this.countryRepository = countryRepository;
        this.cityRepository = cityRepository;
        this.museumRepository = museumRepository;
    }

    public @Nonnull
    MuseumJson addMuseum(MuseumJson museumJson) {
        MuseumEntity museumEntity = new MuseumEntity();
        museumEntity.setDescription(museumJson.getDescription());
        museumEntity.setTitle(museumJson.getTitle());
        museumEntity.setPhoto(museumJson.getPhoto() != null ? museumJson.getPhoto().getBytes(StandardCharsets.UTF_8) : null);
        museumEntity.setGeo(checkAndAddCity(museumJson.getGeo().getCity(), museumJson.getGeo().getCountry().getId()).getId());
        MuseumEntity saved = museumRepository.save(museumEntity);
        return fromMuseumEntity(saved);
    }

    public @Nonnull
    MuseumJson editMuseum(MuseumJson museumJson) {
        MuseumEntity museumEntity = getRequiredMuseum(museumJson.getId());
        museumEntity.setDescription(museumJson.getDescription());
        museumEntity.setTitle(museumJson.getTitle());
        museumEntity.setPhoto(museumJson.getPhoto() != null ? museumJson.getPhoto().getBytes(StandardCharsets.UTF_8) : null);
        museumEntity.setGeo(checkAndAddCity(museumJson.getGeo().getCity(), museumJson.getGeo().getCountry().getId()).getId());
        MuseumEntity saved = museumRepository.save(museumEntity);
        return fromMuseumEntity(saved);
    }

    public @Nonnull
    MuseumJson getMuseumById(UUID id) {
        return fromMuseumEntity(getRequiredMuseum(id));
    }

    public @Nonnull
    Page<MuseumJson> getAllMuseums(@Nullable String title, @Nonnull Pageable pageable) {
        Page<MuseumEntity> artists = (title == null)
                ? museumRepository.findAll(pageable)
                : museumRepository.findAllByTitleContainsIgnoreCase(URLDecoder.decode(title, StandardCharsets.UTF_8), pageable);
        return artists.map(this::fromMuseumEntity);
    }

    public @Nonnull
    Page<CountryJson> getAllCountries(@Nonnull Pageable pageable) {
        Page<CountryEntity> countries = countryRepository.findAll(pageable);
        return countries.map(this::fromCountryEntity);
    }

    @Nonnull
    CityJson checkAndAddCity(String city, UUID countryId) {
        CityEntity requiredCity = cityRepository.findCityEntityByCityAndCountry(city, countryId);
        if (requiredCity == null) {
            CityEntity cityEntity = new CityEntity();
            cityEntity.setCity(city);
            cityEntity.setCountry(countryId);
            CityEntity saved = cityRepository.save(cityEntity);
            return fromCityEntity(saved);
        } else {
            return fromCityEntity(requiredCity);
        }
    }

    @Nonnull
    MuseumEntity getRequiredMuseum(UUID id) {
        return museumRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Nonnull
    CountryEntity getRequiredCountry(UUID country) {
        return countryRepository.findById(country).orElseThrow(NotFoundException::new);
    }

    @Nonnull
    CityEntity getRequiredCity(UUID id) {
        return cityRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public CountryJson fromCountryEntity(CountryEntity entity) {
        CountryJson countryJson = new CountryJson();
        countryJson.setId(entity.getId());
        countryJson.setName(entity.getName());
        return countryJson;
    }

    public CityJson fromCityEntity(CityEntity entity) {
        CityJson cityJson = new CityJson();
        cityJson.setId(entity.getId());
        cityJson.setCity(entity.getCity());
        cityJson.setCountry(fromCountryEntity(getRequiredCountry(entity.getCountry())));
        return cityJson;
    }

    public MuseumJson fromMuseumEntity(MuseumEntity entity) {
        MuseumJson museumJson = new MuseumJson();
        byte[] photo = entity.getPhoto();
        museumJson.setId(entity.getId());
        museumJson.setTitle(entity.getTitle());
        museumJson.setDescription(entity.getDescription());
        museumJson.setPhoto(photo != null && photo.length > 0 ? new String(entity.getPhoto(), StandardCharsets.UTF_8) : null);
        museumJson.setGeo(fromCityEntity(getRequiredCity(entity.getGeo())));
        return museumJson;
    }
}
