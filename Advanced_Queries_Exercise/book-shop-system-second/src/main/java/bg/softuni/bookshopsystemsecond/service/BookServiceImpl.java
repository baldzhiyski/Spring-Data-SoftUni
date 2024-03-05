package bg.softuni.bookshopsystemsecond.service;

import bg.softuni.bookshopsystemsecond.domain.entities.Book;
import bg.softuni.bookshopsystemsecond.domain.entities.enums.AgeRestriction;
import bg.softuni.bookshopsystemsecond.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
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
        final List<Book> books = this.bookRepository.findAllByAuthorLastNameStartsWith(prefix).get();

        books.forEach(b -> System.out.println(b.getTitle()));

        return books;
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


}
