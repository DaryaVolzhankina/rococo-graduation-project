package guru.qa.rococo.data.repository;

import guru.qa.rococo.data.MuseumEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.annotation.Nonnull;
import java.util.UUID;

public interface MuseumRepository extends JpaRepository<MuseumEntity, UUID> {

    @Nonnull
    Page<MuseumEntity> findAllByTitleContainsIgnoreCase(
            @Nonnull String title,
            @Nonnull Pageable pageable
    );
}

