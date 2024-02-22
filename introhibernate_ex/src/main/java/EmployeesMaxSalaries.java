import entities.models.Result;

public class EmployeesMaxSalaries {
    private static final String GET_DEPARTMENT_NAME_AND_COUNT_EMPLOYEES = "SELECT department.name, max(salary) " +
            "FROM Employee " +
            "GROUP BY department.name " +
            "HAVING MAX(salary) NOT BETWEEN 30000 AND 70000 ";
    public static void main(String[] args) {

        // first solution
        Utils.createEntityManager()
                .createQuery(GET_DEPARTMENT_NAME_AND_COUNT_EMPLOYEES, Object[].class)
                .getResultList()
                .forEach(obj -> System.out.println(obj[0] + " " + obj[1]));

        // second solution with custom POJO
        Utils.createEntityManager()
                .createQuery("SELECT NEW entities.models.Result(department.name, MAX(salary))" +
                        " FROM Employee" +
                        " GROUP BY department.name" +
                        " HAVING MAX(salary) NOT BETWEEN 30000 AND 70000", Result.class)
                .getResultList()
                .forEach(System.out::println);
    }
}

