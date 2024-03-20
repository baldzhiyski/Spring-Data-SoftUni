package bg.softuni.mvc_project.services.impl;

import bg.softuni.mvc_project.services.EmployeeService;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Override
    public boolean areImported() {
        return false;
    }

    @Override
    public String readEmployeesFromFile() throws IOException {
        return null;
    }

    @Override
    public String importEmployees() throws IOException {
        return null;
    }
}
