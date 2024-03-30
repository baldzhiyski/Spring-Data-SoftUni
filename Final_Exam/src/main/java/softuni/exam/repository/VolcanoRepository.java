package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.Volcano;

import java.util.List;
import java.util.Optional;

@Repository
public interface VolcanoRepository extends JpaRepository<Volcano,Long> {

    Optional<Volcano> findFirstByName(String name);

    Optional<Volcano> findFirstById(Long id);

    List<Volcano> findAllByIsActiveAndElevationGreaterThanAndLastEruptionNotNullOrderByElevationDesc(Boolean isActive , Integer elevation);

}
