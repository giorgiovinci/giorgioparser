package nl.elmar.persistence;

import nl.elmar.model.Accommodation;

public interface PersistService {

    Accommodation save(Accommodation accommodation);
    
}
