package guru.qa.db.repository.painting;

import guru.qa.db.model.painting.PaintingEntity;

import java.util.UUID;

public interface PaintingRepository {

    void createPainting(PaintingEntity paintingEntity);
    void deletePainting(PaintingEntity paintingEntity);
    void deleteAllArtistsPaintingsById(UUID artistId);
}
