package guru.qa.db.dao.impl;

import guru.qa.db.DataSourceProvider;
import guru.qa.db.ServiceDb;
import guru.qa.db.model.painting.PaintingEntity;
import guru.qa.db.dao.PaintingDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.PreparedStatement;
import java.sql.Statement;
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
        KeyHolder kh = new GeneratedKeyHolder();
        paintingJdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement("INSERT INTO paintings (title, description, content, museum, artist) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, paintingEntity.getTitle());
            ps.setString(2, paintingEntity.getDescription());
            ps.setBytes(3, paintingEntity.getContent());
            ps.setObject(4, paintingEntity.getMuseum());
            ps.setObject(5, paintingEntity.getArtist());
            return ps;
        }, kh);
        final UUID artistId = (UUID) kh.getKeyList().get(0).get("id");
        paintingEntity.setId(artistId);
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
