package inheritance;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

public class Utils {
    private static final String PERSISTENCE_UNIT_NAME = "inheritance";
    static EntityManager createEntityManager() {
        return Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME)
                .createEntityManager();
    }
}
