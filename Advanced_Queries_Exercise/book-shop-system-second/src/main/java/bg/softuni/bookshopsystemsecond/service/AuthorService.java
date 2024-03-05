package bg.softuni.bookshopsystemsecond.service;

import bg.softuni.bookshopsystemsecond.domain.entities.Author;

import java.util.List;

public interface AuthorService {
    boolean isDataSeeded();

    void seedAuthors(List<Author> authors);

    Author getRandomAuthor();


    List<Author> getAllAuthorsOrderByBooksDesc();

    List<Author> getAllByFirstNameEndingWith(String suffix);

    List<String> getInfoAboutAuthorsAndTheirCopies();
}
