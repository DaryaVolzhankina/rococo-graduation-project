package guru.qa.niffler.db.repository.artist;

import guru.qa.niffler.db.model.artist.ArtistEntity;

public interface ArtistRepository {

    void createArtist(ArtistEntity artistEntity);
    void deleteArtist(ArtistEntity artistEntity);
}
