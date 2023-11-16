package guru.qa.rococo.data.repository;

import guru.qa.rococo.data.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CityRepository extends JpaRepository<CityEntity, UUID> {

    CityEntity findCityEntityByCityAndCountry(String city, UUID id);
}
