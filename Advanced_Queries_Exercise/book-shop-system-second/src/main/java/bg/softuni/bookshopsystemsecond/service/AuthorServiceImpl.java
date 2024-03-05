package bg.softuni.bookshopsystemsecond.service;

import bg.softuni.bookshopsystemsecond.domain.entities.Author;
import bg.softuni.bookshopsystemsecond.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService{
    private AuthorRepository authorRepository;
    private final BookService bookService;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, BookService bookService) {
        this.authorRepository = authorRepository;
        this.bookService = bookService;
    }

    @Override
    public boolean isDataSeeded() {
        return this.authorRepository.count()>0;
    }

    @Override
    public void seedAuthors(List<Author> authors) {
        this.authorRepository.saveAllAndFlush(authors);
    }

    @Override
    public Author getRandomAuthor() {
        long count = this.authorRepository.count();

        if(count!=0){
            long randomId = new Random().nextLong(1l,count)+1;
            return this.authorRepository.findById(randomId)
                    .orElseThrow(NoSuchElementException::new);
        }
        throw new RuntimeException();
    }


    @Override
    public List<Author> getAllAuthorsOrderByBooksDesc() {
        final List<Author> authors = this.authorRepository
                .findAllDistinctOrderByBooks();

        System.out.println(authors.stream()
                .map(Author::getAuthorFullNameAndCountOfBooks)
                .collect(Collectors.joining("\n")));

        return authors;
    }

    @Override
    public List<Author> getAllByFirstNameEndingWith(String suffix) {
        return this.authorRepository
                .findAllByFirstNameEndingWith(suffix);
    }

    @Override
    public List<String> getInfoAboutAuthorsAndTheirCopies() {
        return this.authorRepository.findAuthorsCopiesCountForEveryBook();
    }
}
