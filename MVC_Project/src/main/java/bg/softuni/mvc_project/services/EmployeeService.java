package bg.softuni.mvc_project.services;

import java.io.IOException;

public interface EmployeeService {
    boolean areImported();

    String readEmployeesFromFile() throws IOException;

    String importEmployees() throws IOException;
}
