package bg.softuni.mapping_objects_lab.entities.dtos;

import java.math.BigDecimal;

public interface EmployeeNameAndSalaryDTO {
    String getFirstName();

    BigDecimal getSalary();
}