package bg.softuni.mvc_project.services;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface EmployeeService {
    boolean areImported();

    String readEmployeesFromFile() throws IOException;

    void importEmployees() throws IOException, JAXBException;
}
