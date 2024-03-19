package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.ForeCast;
import softuni.exam.models.entity.enums.DayOfWeek;

import java.util.List;
import java.util.Optional;

@Repository
public interface ForecastRepository extends JpaRepository<ForeCast,Long> {

    Optional<List<ForeCast>> findAllByDayOfWeekAndCity_PopulationLessThan(DayOfWeek dayOfWeek, Long lessThan);
}
