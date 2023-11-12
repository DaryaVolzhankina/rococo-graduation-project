package guru.qa.rococo.service;

import guru.qa.rococo.data.ArtistEntity;
import guru.qa.rococo.data.repository.ArtistRepository;
import guru.qa.rococo.ex.NotFoundException;
import guru.qa.rococo.model.ArtistJson;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Component
public class ArtistService {

    private static final Logger LOG = LoggerFactory.getLogger(ArtistService.class);

    private final ArtistRepository artistRepository;

    @Autowired
    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    public @Nonnull
    ArtistJson getArtistById(UUID uuid) {
        return ArtistJson.fromEntity(getRequiredArtist(uuid));
    }

    public @Nonnull
    ArtistJson addArtist(ArtistJson artist) {
        ArtistEntity artistEntity = new ArtistEntity();
        artistEntity.setName(artist.getName());
        artistEntity.setBiography(artist.getBiography());
        artistEntity.setPhoto(artist.getPhoto() != null ? artist.getPhoto().getBytes(StandardCharsets.UTF_8) : null);
        ArtistEntity saved = artistRepository.save(artistEntity);
        return ArtistJson.fromEntity(saved);
    }

    public @Nonnull
    ArtistJson editArtist(ArtistJson artist) {
        ArtistEntity artistEntity = getRequiredArtist(artist.getId());
        artistEntity.setName(artist.getName());
        artistEntity.setBiography(artist.getBiography());
        artistEntity.setPhoto(artist.getPhoto() != null ? artist.getPhoto().getBytes(StandardCharsets.UTF_8) : null);
        ArtistEntity saved = artistRepository.save(artistEntity);
        return ArtistJson.fromEntity(saved);
    }

    public @Nonnull
    Page<ArtistJson> getAll(@Nullable String name, @Nonnull Pageable pageable) {
        Page<ArtistEntity> artists = (name == null)
                ? artistRepository.findAll(pageable)
                : artistRepository.findAllByNameContainsIgnoreCase(URLDecoder.decode(name, StandardCharsets.UTF_8), pageable);
        return artists.map(ArtistJson::fromEntity);
    }

    @Nonnull
    ArtistEntity getRequiredArtist(@Nonnull UUID uuid) {
        ArtistEntity user = artistRepository.findArtistEntityById(uuid);
        if (user == null) {
            throw new NotFoundException("Can`t find user by uuid: " + uuid.toString());
        }
        return user;
    }
}
