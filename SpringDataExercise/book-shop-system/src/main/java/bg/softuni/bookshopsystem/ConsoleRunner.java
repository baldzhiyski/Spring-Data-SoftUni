package bg.softuni.bookshopsystem;

import bg.softuni.bookshopsystem.domain.entities.Book;
import bg.softuni.bookshopsystem.service.AuthorService;
import bg.softuni.bookshopsystem.service.BookService;
import bg.softuni.bookshopsystem.service.SeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Scanner;
import java.util.stream.Collectors;

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
            this.seedService.seedAllData();

            int taskNumber = scanner.nextInt();

            while (taskNumber != 100) {
                switch (taskNumber) {
                    case 1:
                        printAllBooksAfterYear();
                        break;
                    case 2:
                        printAllBooksByAuthorName();
                        break;
                    case 3:
                        printAllBooksWithReleaseDateBetween();
                    default:
                        return;
                }

                taskNumber = scanner.nextInt();
            }
        }

    private void printAllBooksWithReleaseDateBetween() {
        System.out.println("Enter the date after :");
        final LocalDate dateAfter = LocalDate.parse(this.scanner.nextLine());

        System.out.println("Enter the date until :");
        final LocalDate dateUntil = LocalDate.parse(this.scanner.nextLine());

        String result = this.bookService.getAllBetweenTwoYears(dateAfter, dateUntil)
                .stream()
                .map(Book::getBookTitleAndPriceFormat)
                .collect(Collectors.joining(", "));

        System.out.println(result);

    }

    private void printAllBooksByAuthorName () {
            System.out.println("Enter Author full name");
            final String[] inputName = this.scanner.nextLine().split(" ");

            this.bookService
                    .getAllByAuthorFirstNameAndAuthorLastNameOrderByReleaseDateDescTitleAsc(inputName[0], inputName[1])
                    .forEach(b -> System.out.println(b.getBookTitleReleaseDateCopiesFormat()));
        }

        private void printAllBooksAfterYear () {
            System.out.println(this.bookService.getAllBooksAfterYear(LocalDate.of(1999, 1, 1))
                    .stream()
                    .map(Book::getTitle)
                    .collect(Collectors.joining("\n")));
        }
}
