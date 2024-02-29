package bg.softuni.bookshopsystem.repositories;

import bg.softuni.bookshopsystem.domain.entities.Book;
import bg.softuni.bookshopsystem.domain.entities.enums.AgeRestriction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository  extends JpaRepository<Book,Long> {

    Optional<List<Book>> findAllByReleaseDateAfter(LocalDate date);

    Optional<List<Book>> findAllByReleaseDateBefore(LocalDate date);

    Optional<List<Book>> findAllByAuthorFirstNameAndAuthorLastNameOrderByReleaseDateDescTitleAsc(String firstName, String lastName);

    Optional<List<Book>> findAllByAgeRestriction(AgeRestriction ageRestriction);

    Optional<List<Book>> findAllByAuthorLastNameStartsWith(String prefix);

    int deleteAllByCopiesLessThan(Integer copies);

    Optional<List<Book>> findAllByPriceLessThanOrPriceGreaterThan(BigDecimal lowBoundary, BigDecimal upperBoundary);

    Optional<List<Book>> findAllByReleaseDateBeforeAndReleaseDateAfter(LocalDate date1,LocalDate date2);

    Optional<List<Book>> findAllByReleaseDateIsNot(LocalDate date);

    @Query("Select count(b) from Book b where length(b.title)  > :length")
    Integer findAllByTitleLengthGreaterThan(Integer length);

    @Modifying
    @Transactional
    @Query("update Book b set b.copies = b.copies + :copiesAdded where b.releaseDate > :date")
    int updateBooksCopies(Integer copiesAdded, LocalDate date);

    @Procedure(value = "usp_get_book_written_by")
    int getBooksCountByAuthorFirstNameAndAuthorLastName(String fullName);



}
