package guru.qa.db.repository.artist;

import guru.qa.db.dao.ArtistDao;
import guru.qa.db.model.artist.ArtistEntity;
import guru.qa.db.dao.impl.ArtistDaoImpl;

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
