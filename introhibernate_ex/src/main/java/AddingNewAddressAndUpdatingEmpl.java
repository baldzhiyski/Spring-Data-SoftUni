import entities.Address;
import entities.Employee;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Scanner;

public class AddingNewAddressAndUpdatingEmpl {
    public static void main(String[] args) {
        EntityManager entityManager = Utils.createEntityManager();


        Address address = new Address();
        address.setText("Vitoshka 15");

        String givenLastNameOfEmpl = new Scanner(System.in).nextLine();

        entityManager.getTransaction().begin();
        entityManager.persist(address);
        List<Employee> employees = entityManager
                .createQuery("FROM Employee e WHERE e.lastName = :ln", Employee.class)
                .setParameter("ln", givenLastNameOfEmpl)
                .getResultList();

        employees.forEach(e->e.setAddress(address));

        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
