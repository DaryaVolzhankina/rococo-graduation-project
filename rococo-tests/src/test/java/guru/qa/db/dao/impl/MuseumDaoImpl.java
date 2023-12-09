package guru.qa.db.dao.impl;

import guru.qa.db.DataSourceProvider;
import guru.qa.db.ServiceDb;
import guru.qa.db.dao.MuseumDao;
import guru.qa.db.model.museum.MuseumEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.UUID;

public class MuseumDaoImpl implements MuseumDao {

    private final TransactionTemplate museumTtpl;
    private final JdbcTemplate museumJdbcTemplate;

    public MuseumDaoImpl() {
        JdbcTransactionManager userdataTm = new JdbcTransactionManager(DataSourceProvider.INSTANCE.getDataSource(ServiceDb.MUSEUM));

        this.museumTtpl = new TransactionTemplate(userdataTm);
        this.museumJdbcTemplate = new JdbcTemplate(userdataTm.getDataSource());
    }

    @Override
    public int createMuseum(MuseumEntity museum) {
        KeyHolder kh = new GeneratedKeyHolder();
        museumJdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement("INSERT INTO museums (title, description, photo, geo) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, museum.getTitle());
            ps.setString(2, museum.getDescription());
            ps.setBytes(3, museum.getPhoto());
            ps.setObject(4, museum.getGeo());
            return ps;
        }, kh);
        final UUID museumId = (UUID) kh.getKeyList().get(0).get("id");
        museum.setId(museumId);
        return 1;
    }

    @Override
    public void deleteMuseum(MuseumEntity museum) {
        museumJdbcTemplate.update("DELETE FROM museums WHERE id = ?", museum.getId());
    }
}
