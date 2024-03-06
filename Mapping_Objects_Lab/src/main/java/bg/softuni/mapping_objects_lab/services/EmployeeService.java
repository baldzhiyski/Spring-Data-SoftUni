package bg.softuni.mapping_objects_lab.services;

import bg.softuni.mapping_objects_lab.entities.Employee;
import bg.softuni.mapping_objects_lab.entities.dtos.CreateEmployeeDTO;
import bg.softuni.mapping_objects_lab.entities.dtos.EmployeeDTO;
import bg.softuni.mapping_objects_lab.entities.dtos.EmployeeNameAndSalaryDTO;
import bg.softuni.mapping_objects_lab.entities.dtos.EmployeeNamesDTO;

import java.util.List;

public interface EmployeeService {
    Employee create(CreateEmployeeDTO employeeDTO);

    List<EmployeeDTO> findAll();
    EmployeeNamesDTO findNamesById(long id);

    EmployeeNameAndSalaryDTO findNameAndSalaryById(long id);
}
