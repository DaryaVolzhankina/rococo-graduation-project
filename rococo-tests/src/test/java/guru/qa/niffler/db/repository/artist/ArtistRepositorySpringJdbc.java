package guru.qa.niffler.db.repository.artist;

import guru.qa.niffler.db.dao.ArtistDao;
import guru.qa.niffler.db.dao.impl.ArtistDaoImpl;
import guru.qa.niffler.db.model.artist.ArtistEntity;

public class ArtistRepositorySpringJdbc implements ArtistRepository{

    private final ArtistDao artistDao;

    public ArtistRepositorySpringJdbc() {
        this.artistDao = new ArtistDaoImpl();
    }

    @Override
    public void createArtist(ArtistEntity artistEntity) {
        artistDao.createArtist(artistEntity);
    }

    @Override
    public void deleteArtist(ArtistEntity artistEntity) {
        artistDao.deleteArtist(artistEntity);
    }
}
