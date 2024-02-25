import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        Persistence.createEntityManagerFactory("football_db")
                .createEntityManager();
    }
}
