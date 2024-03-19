package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.constants.Messages;
import softuni.exam.constants.Paths;
import softuni.exam.models.dto.BorrowingRecordDto;
import softuni.exam.models.dto.RecordDto;
import softuni.exam.models.dto.wrapper.BorrowingRecordWrapper;
import softuni.exam.models.entity.Book;
import softuni.exam.models.entity.BorrowingRecord;
import softuni.exam.models.entity.LibraryMember;
import softuni.exam.models.entity.enums.Genre;
import softuni.exam.repository.BookRepository;
import softuni.exam.repository.BorrowingRecordRepository;
import softuni.exam.repository.LibraryMemberRepository;
import softuni.exam.service.BorrowingRecordsService;
import softuni.exam.util.ValidationUtils;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static softuni.exam.constants.Messages.INVALID_RECORD;
import static softuni.exam.constants.Messages.VALID_RECORD;
import static softuni.exam.constants.Paths.PATH_TO_RECORDS;

@Service
public class BorrowingRecordsServiceImpl implements BorrowingRecordsService {
    private BorrowingRecordRepository borrowingRecordRepository;

    private LibraryMemberRepository libraryMemberRepository;

    private BookRepository bookRepository;

    private ModelMapper mapper;
    private ValidationUtils validationUtils;
    private XmlParser xmlParser;

    @Autowired
    public BorrowingRecordsServiceImpl(BorrowingRecordRepository borrowingRecordRepository, LibraryMemberRepository libraryMemberRepository, BookRepository bookRepository, ModelMapper mapper, ValidationUtils validationUtils, XmlParser xmlParser) {
        this.borrowingRecordRepository = borrowingRecordRepository;
        this.libraryMemberRepository = libraryMemberRepository;
        this.bookRepository = bookRepository;
        this.mapper = mapper;
        this.validationUtils = validationUtils;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return this.borrowingRecordRepository.count() >0;
    }

    @Override
    public String readBorrowingRecordsFromFile() throws IOException {
        return Files.readString(Path.of(PATH_TO_RECORDS));
    }

    @Override
    public String importBorrowingRecords() throws IOException, JAXBException {
        StringBuilder builder = new StringBuilder();

        List<BorrowingRecordDto> recordsDtos = this.xmlParser.xmlParse(PATH_TO_RECORDS, BorrowingRecordWrapper.class)
                .getBorrowingRecordDtos()
                .stream().collect(Collectors.toList());

        recordsDtos.stream()
                .filter(recordsDto -> {
                    boolean isValid = this.validationUtils.isValid(recordsDto);
                    if(this.bookRepository.findFirstByTitle(recordsDto.getBook().getTitle()).isEmpty()){
                        isValid=false;
                    }
                    if(this.libraryMemberRepository.findFirstById(recordsDto.getMember().getId()).isEmpty()){
                        isValid=false;
                    }
                    builder.append(isValid? String.format(VALID_RECORD,recordsDto.getBook().getTitle(),recordsDto.getBorrowDate())
                                    :INVALID_RECORD)
                            .append(System.lineSeparator());
                    return isValid;
                })
                .map(recordsDto-> {
                    BorrowingRecord mappedRecord = this.mapper.map(recordsDto, BorrowingRecord.class);
                    Book book = this.bookRepository.findFirstByTitle(recordsDto.getBook().getTitle()).get();
                    LibraryMember libraryMember = this.libraryMemberRepository.findFirstById(recordsDto.getMember().getId()).get();

                    mappedRecord.setBook(book);
                    mappedRecord.setLibraryMember(libraryMember);

                    return mappedRecord;
                })
                .forEach(borrowingRecordRepository::saveAndFlush);

        return builder.toString();
    }

    @Override
    public String exportBorrowingRecords() {
        StringBuilder builder = new StringBuilder();

        List<BorrowingRecord> records = this.borrowingRecordRepository
                .findAllByBook_GenreOrderByBorrowDateDesc(Genre.SCIENCE_FICTION)
                .stream().collect(Collectors.toList());

        records.stream()
                .map(record-> this.mapper.map(record, RecordDto.class))
                .forEach(recordDto -> builder.append(recordDto.toString()).append(System.lineSeparator()));

        return builder.toString();
    }
}
