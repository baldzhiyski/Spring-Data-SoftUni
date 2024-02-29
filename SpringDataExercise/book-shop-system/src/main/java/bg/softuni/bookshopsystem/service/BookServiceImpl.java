package bg.softuni.bookshopsystem.service;

import bg.softuni.bookshopsystem.domain.entities.Book;
import bg.softuni.bookshopsystem.domain.entities.enums.AgeRestriction;
import bg.softuni.bookshopsystem.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

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
    public List<Book> getAllByAgeRestriction(String restriction) {
        return this.bookRepository.findAllByAgeRestriction(AgeRestriction.valueOf(restriction.toUpperCase()))
                .get();
    }

    @Override
    public List<Book> getAllByAuthorLastNameStartsWith(String prefix) {
        final List<Book> books = this.bookRepository.findAllByAuthorLastNameStartsWith(prefix).get();

        books.forEach(b -> System.out.println(b.getTitle()));

        return books;
    }
    @Override
    public void increaseCopiesForBookReleasedAfter(Integer addedCopies, LocalDate dateAfter) {
        final List<Book> books = this.bookRepository.findAllByReleaseDateAfter(dateAfter).get();

        books.forEach(book -> book.setCopies(book.getCopies() + addedCopies));

        this.bookRepository.saveAllAndFlush(books);

        System.out.println(addedCopies * books.size());
    }

    @Override
    public int deleteAllByCopiesLessThan(Integer copies) {
        return this.bookRepository.deleteAllByCopiesLessThan(copies);
    }

    @Override
    public int getBooksCountByAuthorFirstNameAndAuthorLastName(String firstName) {
        return this.bookRepository.getBooksCountByAuthorFirstNameAndAuthorLastName(firstName);
    }

    @Override
    public Integer getAllByTitleLengthGreaterThan(Integer length) {
        Integer count = this.bookRepository.findAllByTitleLengthGreaterThan(length);

        System.out.println(count);

        return count;
    }

    @Override
    public List<Book> getAllBooksBeforeYear(LocalDate date) {
        return this.bookRepository.findAllByReleaseDateBefore(date).get();
    }

    @Override
    public List<Book> getAllByPriceNotBetween(BigDecimal lowBoundary, BigDecimal upperBoundary) {
        return this.bookRepository.findAllByPriceLessThanOrPriceGreaterThan(lowBoundary,upperBoundary).get();
    }


    @Override
    public List<Book> getAllBetweenTwoYears(LocalDate date1, LocalDate date2) {
        return this.bookRepository.findAllByReleaseDateBeforeAndReleaseDateAfter(date1, date2).get();
    }
}
