import javax.persistence.EntityManager;

public class EmployeeWithSalaryOver5000 {
    public static void main(String[] args) {

        EntityManager entityManager = Utils.createEntityManager();

        entityManager.getTransaction().begin();

        entityManager.createQuery("SELECT e.firstName FROM Employee e WHERE e.salary>50000",String.class)
                .getResultList()
                .forEach(System.out::println);

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
