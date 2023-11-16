package guru.qa.rococo.data.repository;

import guru.qa.rococo.data.CountryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.annotation.Nonnull;
import java.util.UUID;

public interface CountryRepository extends JpaRepository<CountryEntity, UUID> {

    CountryEntity findByName(String name);

    @Nonnull
    Page<CountryEntity> findAll(
            @Nonnull Pageable pageable
    );
}
