import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class Utils {
    private static final String DATABASE_NAME = "soft_uni";
    static EntityManager createEntityManager() {
        return Persistence.createEntityManagerFactory(DATABASE_NAME)
                .createEntityManager();
    }
}
