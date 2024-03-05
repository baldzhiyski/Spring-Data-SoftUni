package bg.softuni.bookshopsystemsecond.service;

import bg.softuni.bookshopsystemsecond.domain.entities.Book;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

@Service
public interface BookService {
    boolean isDataSeeded();

    void seedBooks(List<Book> books);

    List<Book> getAllBooksAfterYear(LocalDate year);
    List<Book> getAllByAuthorFirstNameAndAuthorLastNameOrderByReleaseDateDescTitleAsc(String firstName, String lastName);


    List<Book> getAllByAuthorLastNameStartsWith(String prefix);

    Integer getAllByTitleLengthGreaterThan(Integer length);

    List<Book> getAllByAgeRestriction(String restriction);

    List<Book> getAllByGoldenEditionTypeAndCopiesNumber(String editionType , Integer copies);

    List<Book> getAllBooksWithPriceLessThanOrMoreThan(BigDecimal low, BigDecimal high);

    List<Book> getAllBooksWhereReleaseDateBefore(String date) throws ParseException;

    List<Book> getAllBooksByYearOfReleaseDateNot(int year);

}
