package guru.qa.db.dao.impl;

import guru.qa.db.DataSourceProvider;
import guru.qa.db.ServiceDb;
import guru.qa.db.dao.ArtistDao;
import guru.qa.db.model.artist.ArtistEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.UUID;

public class ArtistDaoImpl implements ArtistDao {
    private final TransactionTemplate artistTtpl;
    private final JdbcTemplate artistJdbcTemplate;

    public ArtistDaoImpl() {
        JdbcTransactionManager museumTm = new JdbcTransactionManager(DataSourceProvider.INSTANCE.getDataSource(ServiceDb.ARTIST));

        this.artistTtpl = new TransactionTemplate(museumTm);
        this.artistJdbcTemplate = new JdbcTemplate(museumTm.getDataSource());
    }

    @Override
    public int createArtist(ArtistEntity artistEntity) {
        KeyHolder kh = new GeneratedKeyHolder();
        artistJdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement("INSERT INTO artists (name, biography, photo) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, artistEntity.getName());
            ps.setString(2, artistEntity.getBiography());
            ps.setBytes(3, artistEntity.getPhoto());
            return ps;
        }, kh);
        final UUID artistId = (UUID) kh.getKeyList().get(0).get("id");
        artistEntity.setId(artistId);
        return 1;
    }

    @Override
    public void deleteArtist(ArtistEntity artistEntity) {
        artistJdbcTemplate.update("DELETE FROM artists WHERE id = ?", artistEntity.getId());
    }
}
