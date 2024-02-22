import entities.Employee;

import javax.persistence.EntityManager;

public class EmployeesFromDepartment {
    public static void main(String[] args) {
        EntityManager entityManager =
                Utils.createEntityManager();


        entityManager
                .createQuery(" FROM Employee e WHERE department.name = :dName" +
                        " ORDER BY e.salary, e.id" , Employee.class)
                .setParameter("dName","Research and Development")
                .getResultList()
                .forEach(Employee::printFullNameDepNameAndSalary);
    }
}
