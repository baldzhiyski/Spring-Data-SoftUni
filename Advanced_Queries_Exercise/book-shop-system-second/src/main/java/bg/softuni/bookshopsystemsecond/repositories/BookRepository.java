package bg.softuni.bookshopsystemsecond.repositories;

import bg.softuni.bookshopsystemsecond.domain.entities.Book;
import bg.softuni.bookshopsystemsecond.domain.entities.enums.AgeRestriction;
import bg.softuni.bookshopsystemsecond.domain.entities.enums.EditionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository  extends JpaRepository<Book,Long> {

    Optional<List<Book>> findAllByReleaseDateAfter(LocalDate date);

    Optional<List<Book>> findAllByAuthorFirstNameAndAuthorLastNameOrderByReleaseDateDescTitleAsc(String firstName, String lastName);

    Optional<List<Book>> findAllByAuthorLastNameStartsWith(String prefix);

    @Query("Select count(b) from Book b where length(b.title)  > :length")
    Integer findAllByTitleLengthGreaterThan(Integer length);

    Optional<List<Book>> findAllByAgeRestriction(AgeRestriction ageRestriction);

    Optional<List<Book>> findAllByEditionTypeAndCopiesLessThan(EditionType type, Integer copies);

    Optional<List<Book>> findAllByPriceLessThanOrPriceGreaterThan(BigDecimal low, BigDecimal high);

    Optional<List<Book>> findAllByReleaseDateBefore(LocalDate date);

    @Query(value = "select b  from  Book  b where year(b.releaseDate) != :year")
    Optional<List<Book>> findAllByReleaseDateYearNotIn(int year);




}
