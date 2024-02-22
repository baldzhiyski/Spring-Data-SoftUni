import entities.Employee;

import javax.persistence.EntityManager;
import java.util.Scanner;

public class ContainsEmployee {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        final EntityManager entityManager =
                Utils.createEntityManager();

        String fullName = scanner.nextLine();

        final String isEmployeePresented = entityManager
                .createQuery("FROM Employee where concat_ws(' ', firstName, lastName) = :fullName", Employee.class)
                .setParameter("fullName", fullName)
                .getResultList()
                .isEmpty() ? "No" : "Yes";

        System.out.println(isEmployeePresented);
    }
}
