package bg.softuni.mvc_project.repositories;

import bg.softuni.mvc_project.domain.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository  extends JpaRepository<Employee,Long> {

    Optional<List<Employee>> findAllByAgeGreaterThan(Integer age);
}
