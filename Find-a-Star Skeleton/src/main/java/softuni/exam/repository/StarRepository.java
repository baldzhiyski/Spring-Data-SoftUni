package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.Star;
import softuni.exam.models.entity.enums.StarType;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface StarRepository extends JpaRepository<Star,Long> {

    Optional<Star> findFirstByName(String name);

    Optional<Star> findFirstById(Long id);

    Optional<Set<Star>> findAllByStarTypeAndAstronomersIsNullOrderByLightYears(StarType type);
}
