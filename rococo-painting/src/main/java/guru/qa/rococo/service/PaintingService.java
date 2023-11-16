package guru.qa.rococo.service;

import guru.qa.rococo.data.PaintingEntity;
import guru.qa.rococo.data.repository.PaintingRepository;
import guru.qa.rococo.ex.NotFoundException;
import guru.qa.rococo.model.PaintingJson;
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
public class PaintingService {

    private final PaintingRepository paintingRepository;

    @Autowired
    public PaintingService(PaintingRepository paintingRepository) {
        this.paintingRepository = paintingRepository;
    }

    public @Nonnull
    PaintingJson getPaintingById(@Nonnull UUID uuid) {
        return PaintingJson.fromEntity(getRequiredPainting(uuid));
    }

    public @Nonnull
    PaintingJson addPainting(@Nonnull PaintingJson paintingJson) {
        PaintingEntity paintingEntity = new PaintingEntity();
        paintingEntity.setTitle(paintingJson.getTitle());
        paintingEntity.setDescription(paintingJson.getDescription());
        paintingEntity.setContent(paintingJson.getContent() != null ? paintingJson.getContent().getBytes(StandardCharsets.UTF_8) : null);
        paintingEntity.setMuseum(paintingJson.getMuseumId());
        paintingEntity.setArtist(paintingJson.getArtistId());
        PaintingEntity saved = paintingRepository.save(paintingEntity);
        return PaintingJson.fromEntity(saved);
    }

    public @Nonnull
    PaintingJson editPainting(@Nonnull PaintingJson paintingJson) {
        PaintingEntity paintingEntity = getRequiredPainting(paintingJson.getId());
        paintingEntity.setTitle(paintingJson.getTitle());
        paintingEntity.setDescription(paintingJson.getDescription());
        paintingEntity.setContent(paintingJson.getContent() != null ? paintingJson.getContent().getBytes(StandardCharsets.UTF_8) : null);
        paintingEntity.setMuseum(paintingJson.getMuseumId());
        paintingEntity.setArtist(paintingJson.getArtistId());
        PaintingEntity saved = paintingRepository.save(paintingEntity);
        return PaintingJson.fromEntity(saved);
    }

    public @Nonnull
    Page<PaintingJson> getAll(@Nullable String title, @Nonnull Pageable pageable) {
        Page<PaintingEntity> painting = (title == null)
                ? paintingRepository.findAll(pageable)
                : paintingRepository.findAllByTitleContainsIgnoreCase(URLDecoder.decode(title, StandardCharsets.UTF_8), pageable);
        return painting.map(PaintingJson::fromEntity);
    }

    public @Nonnull
    Page<PaintingJson> getAllPaintingsByArtist(@Nonnull UUID id, @Nonnull Pageable pageable) {
        Page<PaintingEntity> painting = paintingRepository.findAllByArtist(id, pageable);
        return painting.map(PaintingJson::fromEntity);
    }

    @Nonnull
    PaintingEntity getRequiredPainting(@Nonnull UUID uuid) {
        return paintingRepository.findById(uuid).orElseThrow(NotFoundException::new);
    }
}
