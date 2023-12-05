package guru.qa.niffler.db.dao;

import guru.qa.niffler.db.model.artist.ArtistEntity;

public interface ArtistDao {
    int createArtist(ArtistEntity artistEntity);
    void deleteArtist(ArtistEntity artistEntity);
}
