package bg.softuni.bookshopsystem.service;

import bg.softuni.bookshopsystem.domain.entities.Book;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public interface BookService {
    boolean isDataSeeded();

    void seedBooks(List<Book> books);

    List<Book> getAllBooksAfterYear(LocalDate year);

    List<Book> getAllByAuthorFirstNameAndAuthorLastNameOrderByReleaseDateDescTitleAsc(String firstName, String lastName);

    List<Book> getAllByAgeRestriction(String restriction);

    List<Book> getAllByAuthorLastNameStartsWith(String prefix);
    int deleteAllByCopiesLessThan(Integer copies);

    int getBooksCountByAuthorFirstNameAndAuthorLastName(String firstName);

    void increaseCopiesForBookReleasedAfter(Integer addedCopies, LocalDate dateAfter);

    Integer getAllByTitleLengthGreaterThan(Integer length);

    List<Book> getAllBooksBeforeYear(LocalDate date);

    List<Book> getAllByPriceNotBetween(BigDecimal lowBoundary, BigDecimal upperBoundary);

    List<Book> getAllBetweenTwoYears(LocalDate date1, LocalDate date2);
}
