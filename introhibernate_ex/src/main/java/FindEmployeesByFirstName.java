import entities.Employee;

import java.util.Scanner;

public class FindEmployeesByFirstName {
    public static void main(String[] args) {
        String pattern = new Scanner(System.in).nextLine();

        Utils.createEntityManager()
                .createQuery("FROM Employee WHERE firstName LIKE   CONCAT(:letters, '%')", Employee.class)
                .setParameter("letters",pattern)
                .getResultList()
                .forEach(Employee::printNameTitleSal);
    }
}

