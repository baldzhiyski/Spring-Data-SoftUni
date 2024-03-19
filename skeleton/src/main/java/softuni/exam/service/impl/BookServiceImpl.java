package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.constants.Messages;
import softuni.exam.constants.Paths;
import softuni.exam.models.dto.BookDto;
import softuni.exam.models.entity.Book;
import softuni.exam.repository.BookRepository;
import softuni.exam.service.BookService;
import softuni.exam.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static softuni.exam.constants.Messages.INVALID_BOOK;
import static softuni.exam.constants.Messages.VALID_BOOK;
import static softuni.exam.constants.Paths.PATH_TO_BOOKS;

@Service
public class BookServiceImpl implements BookService {
    private BookRepository bookRepository;

    private Gson gson;

    private ModelMapper mapper;

    private ValidationUtils validationUtils;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, Gson gson, ModelMapper mapper, ValidationUtils validationUtils) {
        this.bookRepository = bookRepository;
        this.gson = gson;
        this.mapper = mapper;
        this.validationUtils = validationUtils;
    }

    @Override
    public boolean areImported() {
        return this.bookRepository.count()>0;
    }

    @Override
    public String readBooksFromFile() throws IOException {
        return Files.readString(Path.of(PATH_TO_BOOKS));
    }

    @Override
    public String importBooks() throws IOException {
        StringBuilder builder = new StringBuilder();

        Arrays.stream(this.gson.fromJson(readBooksFromFile(), BookDto[].class))
                .filter(bookDto -> {
                    boolean isValid = this.validationUtils.isValid(bookDto);
                    if(this.bookRepository.findFirstByTitle(bookDto.getTitle()).isPresent()){
                        isValid=false;
                    }

                    builder.append(isValid ? String.format(VALID_BOOK,bookDto.getAuthor(),bookDto.getTitle()) :
                            INVALID_BOOK ). append(System.lineSeparator());
                    return isValid;
                })
                .map(bookDto -> this.mapper.map(bookDto, Book.class))
                .forEach(bookRepository::saveAndFlush);
        return builder.toString();
    }
}
