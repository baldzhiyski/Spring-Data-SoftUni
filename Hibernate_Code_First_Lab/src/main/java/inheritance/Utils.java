package inheritance;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

public class Utils {
    private static final String DATABASE_NAME = "inheritance";
    static EntityManager createEntityManager() {
        return Persistence.createEntityManagerFactory(DATABASE_NAME)
                .createEntityManager();
    }
}
