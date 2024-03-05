package bg.softuni.bookshopsystemsecond.repositories;

import bg.softuni.bookshopsystemsecond.domain.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
}
