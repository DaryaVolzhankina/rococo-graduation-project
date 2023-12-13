package guru.qa.db.dao;

import guru.qa.db.model.painting.PaintingEntity;

import java.util.UUID;

public interface PaintingDao {
    void createPainting(PaintingEntity paintingEntity);
    void deletePainting(PaintingEntity paintingEntity);
    void deleteAllArtistsPaintingsById(UUID artistId);
    void deleteAllPaintingsByMuseumId(UUID museumId);
}
