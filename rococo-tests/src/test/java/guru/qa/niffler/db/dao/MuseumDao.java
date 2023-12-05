package guru.qa.niffler.db.dao;

import guru.qa.niffler.db.model.museum.MuseumEntity;

public interface MuseumDao {

    int createMuseum(MuseumEntity museum);
    void deleteMuseum(MuseumEntity museum);
}
