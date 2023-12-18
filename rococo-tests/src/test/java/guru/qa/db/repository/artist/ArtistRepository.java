package guru.qa.db.repository.artist;

import guru.qa.db.model.artist.ArtistEntity;

public interface ArtistRepository {

    void createArtist(ArtistEntity artistEntity);
    void deleteArtist(ArtistEntity artistEntity);
}
