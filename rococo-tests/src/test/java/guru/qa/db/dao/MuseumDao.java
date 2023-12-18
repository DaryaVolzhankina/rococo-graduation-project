package guru.qa.db.dao;

import guru.qa.db.model.museum.MuseumEntity;

public interface MuseumDao {

    int createMuseum(MuseumEntity museum);
    void deleteMuseum(MuseumEntity museum);
}
