package guru.qa.niffler.db.dao;

import guru.qa.niffler.db.model.painting.PaintingEntity;

import java.util.UUID;

public interface PaintingDao {
    void createPainting(PaintingEntity paintingEntity);
    void deletePainting(PaintingEntity paintingEntity);
    void deleteAllArtistsPaintingsById(UUID artistId);
}
