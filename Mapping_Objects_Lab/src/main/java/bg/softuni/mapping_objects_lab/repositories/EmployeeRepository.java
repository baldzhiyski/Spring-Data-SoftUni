package bg.softuni.mapping_objects_lab.repositories;

import bg.softuni.mapping_objects_lab.entities.Employee;
import bg.softuni.mapping_objects_lab.entities.dtos.EmployeeNameAndSalaryDTO;
import bg.softuni.mapping_objects_lab.entities.dtos.EmployeeNamesDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    @Query("SELECT new bg.softuni.mapping_objects_lab.entities.dtos.EmployeeNamesDTO(e.firstName, e.lastName) " +
            " FROM Employee  e WHERE e.id = :id")
    EmployeeNamesDTO findNamesById(long id);

    EmployeeNameAndSalaryDTO findFirstNameAndSalaryById(long id);
}
