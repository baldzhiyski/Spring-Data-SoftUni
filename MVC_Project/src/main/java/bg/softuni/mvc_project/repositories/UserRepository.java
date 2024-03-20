package bg.softuni.mvc_project.repositories;

import bg.softuni.mvc_project.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findFirstByEmail(String email);

    Optional<User> findFirstByUsername(String username);
}
