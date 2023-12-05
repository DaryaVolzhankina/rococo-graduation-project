package guru.qa.niffler.db.dao.impl;

import guru.qa.niffler.db.DataSourceProvider;
import guru.qa.niffler.db.ServiceDb;
import guru.qa.niffler.db.dao.PaintingDao;
import guru.qa.niffler.db.model.painting.PaintingEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.UUID;

public class PaintingDaoImpl implements PaintingDao {

    private final TransactionTemplate paintingTtpl;
    private final JdbcTemplate paintingJdbcTemplate;

    public PaintingDaoImpl() {
        JdbcTransactionManager paintingTm = new JdbcTransactionManager(DataSourceProvider.INSTANCE.getDataSource(ServiceDb.PAINTING));

        this.paintingTtpl = new TransactionTemplate(paintingTm);
        this.paintingJdbcTemplate = new JdbcTemplate(paintingTm.getDataSource());
    }


    @Override
    public void createPainting(PaintingEntity paintingEntity) {
        paintingJdbcTemplate.update("INSERT INTO paintings (title, description, content, museum, artist) VALUES (?, ?, ?, ?, ?)", paintingEntity.getTitle(), paintingEntity.getDescription(),
                paintingEntity.getContent(), paintingEntity.getMuseum(), paintingEntity.getArtist());
    }

    @Override
    public void deletePainting(PaintingEntity paintingEntity) {
        paintingJdbcTemplate.update("DELETE FROM paintings WHERE id = ?", paintingEntity.getId());
    }

    @Override
    public void deleteAllArtistsPaintingsById(UUID artistId) {
        paintingJdbcTemplate.update("DELETE FROM paintings WHERE artist = ?", artistId);
    }
}
