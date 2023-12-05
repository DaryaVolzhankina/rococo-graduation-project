package guru.qa.niffler.db.repository.painting;

import guru.qa.niffler.db.dao.PaintingDao;
import guru.qa.niffler.db.dao.impl.PaintingDaoImpl;
import guru.qa.niffler.db.model.painting.PaintingEntity;

import java.util.UUID;

public class PaintingRepositorySpringJdbc implements PaintingRepository {

    private final PaintingDao paintingDao;

    public PaintingRepositorySpringJdbc() {
        this.paintingDao = new PaintingDaoImpl();
    }


    @Override
    public void createPainting(PaintingEntity paintingEntity) {
        paintingDao.createPainting(paintingEntity);
    }

    @Override
    public void deletePainting(PaintingEntity paintingEntity) {
        paintingDao.deletePainting(paintingEntity);
    }

    @Override
    public void deleteAllArtistsPaintingsById(UUID artistId) {
        paintingDao.deleteAllArtistsPaintingsById(artistId);
    }
}
