import entities.Employee;

import java.util.Scanner;

public class GetEmployeesWithProject {
    public static void main(String[] args) {
        int idOfEmployee = new Scanner(System.in).nextInt();

        Utils.createEntityManager()
                .createQuery("FROM Employee WHERE id = :givenId", Employee.class)
                .setParameter("givenId",idOfEmployee)
                .getSingleResult()
                .printSomeNames();
    }
}
