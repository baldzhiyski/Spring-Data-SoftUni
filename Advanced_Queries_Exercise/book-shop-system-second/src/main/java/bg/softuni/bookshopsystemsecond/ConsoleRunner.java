package bg.softuni.bookshopsystemsecond;

import bg.softuni.bookshopsystemsecond.domain.entities.Author;
import bg.softuni.bookshopsystemsecond.domain.entities.Book;
import bg.softuni.bookshopsystemsecond.service.AuthorService;
import bg.softuni.bookshopsystemsecond.service.BookService;
import bg.softuni.bookshopsystemsecond.service.SeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Scanner;

@Component
public class ConsoleRunner implements CommandLineRunner {
        private final SeedService seedService;
        private final BookService bookService;
        private final AuthorService authorService;
        private final Scanner scanner;

    @Autowired
    public ConsoleRunner(SeedService seedService, BookService bookService, AuthorService authorService) {
            this.seedService = seedService;
            this.bookService = bookService;
            this.authorService = authorService;
            this.scanner = new Scanner(System.in);
        }


        @Override
        public void run (String...args) throws Exception {
//            String input = scanner.nextLine();

        }

    private void printAuthorFullNameWithFullAmountCopiesOfBooks() {
        this.authorService.getInfoAboutAuthorsAndTheirCopies()
                .forEach(System.out::println);
    }

    private void printBooksTitlesWithAuthorLastNameStartsWith(String prefixForLastNameOfAuthor) {
        this.bookService.getAllByAuthorLastNameStartsWith(prefixForLastNameOfAuthor)
                .stream()
                .map(Book::getTitle)
                .forEach(System.out::println);
    }

    private void printAllBooksTitlesContaining(String input) {
        this.bookService.getAllWithTitleContains(input)
                .stream()
                .map(Book::getTitle)
                .forEach(System.out::println);
    }

    private void printAllAuthorsWithFirstNameEndingWith(String input) {
        this.authorService.getAllByFirstNameEndingWith(input)
                .stream().map(Author::getAuthorFullName)
                .forEach(System.out::println);
    }

    private void printAllBooksWhereYearOfReleaseNot(String yearOfRel) {
        this.bookService.getAllBooksByYearOfReleaseDateNot(Integer.parseInt(yearOfRel))
                .stream()
                .map(Book::getTitle)
                .forEach(System.out::println);
    }

    private void printBooksReleaseDateBefore(String date) throws ParseException {
        this.bookService.getAllBooksWhereReleaseDateBefore(date)
                .stream()
                .map(Book::getBookTitleEditionTypeAndPrice)
                .forEach(System.out::println);
    }

    private void printBooksWithPriceLowerThan5OrGreaterThan40() {
        this.bookService.getAllBooksWithPriceLessThanOrMoreThan(BigDecimal.valueOf(5L),BigDecimal.valueOf(40L))
                .stream()
                .map(Book::getBookTitleAndPriceFormat)
                .forEach(System.out::println);
    }

    private void printAllBookTitleByGoldEditionAndLessThan5000Copies() {
        this.bookService.getAllByGoldenEditionTypeAndCopiesNumber("gold",5000)
                .stream()
                .map(Book::getTitle)
                .forEach(System.out::println);
    }

    private void printBooksTitleWithGivenAgeRestriction(String input) {
        this.bookService.getAllByAgeRestriction(input)
                .stream().map(Book::getTitle)
                .forEach(System.out::println);
    }
}
