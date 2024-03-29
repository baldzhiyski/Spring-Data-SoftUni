import entities.Employee;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class IncreaseSalaries {
    private static final List<String> DEPARTMENTS_NAMES_TO_INCREASE_SALARIES =
            List.of("Engineering",
                    "Tool Design",
                    "Marketing",
                    "Information Services");
    public static void main(String[] args) {
        EntityManager entityManager =
                Utils.createEntityManager();

        entityManager.getTransaction().begin();

        List<Employee> employees = entityManager.createQuery("FROM Employee WHERE department.name in (:dep)", Employee.class)
                .setParameter("dep", DEPARTMENTS_NAMES_TO_INCREASE_SALARIES)
                .getResultList();

        employees.forEach(employee -> employee.setSalary(employee.getSalary().multiply(BigDecimal.valueOf(1.12))));

        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();

        printResults(employees);
    }

    private static void printResults(List<Employee> employees) {
        employees.forEach(employee -> System.out.printf("%s %s ($%s)%n",
                employee.getFirstName(),
                employee.getLastName(),
                employee.getSalary()));
    }
}
