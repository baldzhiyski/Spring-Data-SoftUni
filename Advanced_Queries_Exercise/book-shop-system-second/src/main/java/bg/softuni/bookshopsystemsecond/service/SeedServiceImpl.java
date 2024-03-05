package bg.softuni.bookshopsystemsecond.service;

import bg.softuni.bookshopsystemsecond.domain.entities.Author;
import bg.softuni.bookshopsystemsecond.domain.entities.Book;
import bg.softuni.bookshopsystemsecond.domain.entities.Category;
import bg.softuni.bookshopsystemsecond.domain.entities.enums.AgeRestriction;
import bg.softuni.bookshopsystemsecond.domain.entities.enums.EditionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static bg.softuni.bookshopsystemsecond.constants.FilePath.*;

@Service
public class SeedServiceImpl implements SeedService{
    private final AuthorService authorService;
    private final BookService bookService;

    private final CategoryService categoryService;

    @Autowired
    public SeedServiceImpl(AuthorService authorService, BookService bookService, CategoryService categoryService) {
        this.authorService = authorService;
        this.bookService = bookService;
        this.categoryService = categoryService;
    }

    @Override
    public void seedAuthors() throws IOException {
        if(this.authorService.isDataSeeded()) return;

        this.authorService.seedAuthors(
                Files.readAllLines(Path.of(RESOURCE_URL + AUTHORS_FILE_NAME))
                        .stream()
                        .map(fullName -> new Author.Builder()
                                .firstName(fullName.split(" ")[0])
                                .lastName(fullName.split(" ")[1])
                                .build())
                        .toList());
    }

    @Override
    public void seedBooks() throws IOException {
        final List<Book> books = Files.readAllLines(Path.of(RESOURCE_URL + BOOK_FILE_NAME))
                .stream()
                .filter(s -> !s.isBlank())
                .map(row -> {
                    String[] args = row.split("\\s+");
                    String title = Arrays.stream(args)
                            .skip(5)
                            .collect(Collectors.joining(" "));

                    return  new Book.Builder()
                            .author(this.authorService.getRandomAuthor())
                            .categories(this.categoryService.getRandomCategories())
                            .title(title)
                            .editionType(EditionType.values()[Integer.parseInt(args[0])])
                            .ageRestriction(AgeRestriction.values()[Integer.parseInt(args[4])])
                            .releaseDate(LocalDate.parse(args[1], DateTimeFormatter.ofPattern("d/M/yyyy")))
                            .copies(Integer.parseInt(args[2]))
                            .price(new BigDecimal(args[3]))
                            .build();
                }).toList();

        this.bookService.seedBooks(books);

    }

    @Override
    public void seedCategories() throws IOException {
        if (this.categoryService.isDataSeeded()) return;

        this.categoryService.seedCategories(
                Files.readAllLines(Path.of(RESOURCE_URL + CATEGORY_FILE_NAME))
                        .stream()
                        .filter(s -> !s.isBlank())
                        .map(Category::new)
                        .toList());

    }
}
