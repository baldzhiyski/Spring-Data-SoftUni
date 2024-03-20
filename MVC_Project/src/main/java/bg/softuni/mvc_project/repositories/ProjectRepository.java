package bg.softuni.mvc_project.repositories;

import bg.softuni.mvc_project.domain.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository  extends JpaRepository<Project,Long> {
    Optional<Project> findFirstByName(String name);

    Optional<List<Project>> findAllByIsFinishedTrue();
}
