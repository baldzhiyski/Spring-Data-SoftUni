package bg.softuni.bookshopsystemsecond.service;

import bg.softuni.bookshopsystemsecond.domain.entities.Book;
import bg.softuni.bookshopsystemsecond.domain.entities.enums.AgeRestriction;
import bg.softuni.bookshopsystemsecond.domain.entities.enums.EditionType;
import bg.softuni.bookshopsystemsecond.domain.entities.model.BookPrintInfo;
import bg.softuni.bookshopsystemsecond.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;

@Service
public class BookServiceImpl implements BookService{

    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    @Override
    public boolean isDataSeeded() {
        return this.bookRepository.count() > 0;
    }

    @Override
    public void seedBooks(List<Book> books) {
        this.bookRepository.saveAllAndFlush(books);
    }

    @Override
    public List<Book> getAllBooksAfterYear(LocalDate year) {
        return this.bookRepository.findAllByReleaseDateAfter(year).get();
    }

    @Override
    public List<Book> getAllByAuthorFirstNameAndAuthorLastNameOrderByReleaseDateDescTitleAsc(String firstName, String lastName) {
        return this.bookRepository.findAllByAuthorFirstNameAndAuthorLastNameOrderByReleaseDateDescTitleAsc(firstName,lastName)
                .get();
    }

    @Override
    public List<Book> getAllByAuthorLastNameStartsWith(String prefix) {
        return this.bookRepository.findAllByAuthorLastNameStartsWith(prefix)
                .orElseThrow();
    }
    @Override
    public Integer getAllByTitleLengthGreaterThan(Integer length) {
        Integer count = this.bookRepository.findAllByTitleLengthGreaterThan(length);

        System.out.println(count);

        return count;
    }

    @Override
    public List<Book> getAllByAgeRestriction(String restriction) {
        AgeRestriction ageRestriction = AgeRestriction.valueOf(restriction.toUpperCase());
        return  this.bookRepository.findAllByAgeRestriction(ageRestriction)
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<Book> getAllByGoldenEditionTypeAndCopiesNumber(String editionType, Integer copies) {
        EditionType type = EditionType.valueOf(editionType.toUpperCase());
        return this.bookRepository.findAllByEditionTypeAndCopiesLessThan(type,copies)
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<Book> getAllBooksWithPriceLessThanOrMoreThan(BigDecimal low, BigDecimal high) {
        return this.bookRepository.findAllByPriceLessThanOrPriceGreaterThan(low,high)
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<Book> getAllBooksWhereReleaseDateBefore(String date) throws ParseException {
        LocalDate parsedDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        return this.bookRepository.findAllByReleaseDateBefore(parsedDate)
                .orElseThrow();

    }

    @Override
    public List<Book> getAllBooksByYearOfReleaseDateNot(int year) {
        return this.bookRepository.findAllByReleaseDateYearNotIn(year)
                .orElseThrow();
    }

    @Override
    public List<Book> getAllWithTitleContains(String word) {
        return this.bookRepository.findAllByTitleContainingIgnoreCase(word)
                .orElseThrow();
    }

    @Override
    public Integer getCountOfBooksWithTitlesLengthMoreThan(int number) {
        return this.bookRepository.countAllByTitleGreaterThan(number);
    }

    @Override
    public BookPrintInfo getNeededInfoForGivenNameOfBook(String title) {
        return this.bookRepository.findInfoByGivenTitle(title)
                .orElseThrow();
    }

    @Override
    public int updateBooksCopiesWhereReleaseDateAfter(String inputDate, int amount) {
        LocalDate parsedDate = LocalDate.parse(inputDate,DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH));

        return this.bookRepository.increaseBooksCopiesReleasedAfter(parsedDate,amount);
    }

    @Override
    public int deleteBooksByCopiesLessThan(int number) {
        return this.bookRepository.deleteAllByCopiesLessThan(number);
    }

    @Override
    public int getBooksCountByAuthorName(String fullName) {
        String[] names = fullName.split("\\s+", 2); // Limit split to 2 parts
        if (names.length < 2) {
            throw new IllegalArgumentException("Full name must contain both first name and last name");
        }
        return this.bookRepository.getBooksCountByAuthorFirstNameAndAuthorLastName(names[0],names[1]);
    }


}
