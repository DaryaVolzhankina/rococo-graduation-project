package guru.qa.db.dao;

import guru.qa.db.model.artist.ArtistEntity;

public interface ArtistDao {
    int createArtist(ArtistEntity artistEntity);
    void deleteArtist(ArtistEntity artistEntity);
}
