package bg.softuni.mvc_project.repositories;

import bg.softuni.mvc_project.domain.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company,Long> {

    Optional<Company> findFirstByName(String name);
}
