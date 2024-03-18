package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import softuni.exam.constants.Paths;
import softuni.exam.models.dto.AstronomerDto;
import softuni.exam.models.dto.wrapper.AstronomerWrapperDto;
import softuni.exam.models.entity.Astronomer;
import softuni.exam.models.entity.Star;
import softuni.exam.repository.AstronomerRepository;
import softuni.exam.repository.StarRepository;
import softuni.exam.service.AstronomerService;
import softuni.exam.util.ValidationUtils;
import softuni.exam.util.XmlParserImpl;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static softuni.exam.constants.Messages.INVALID_ASTRONOMER_MESSAGE;
import static softuni.exam.constants.Messages.VALID_ASTRONOMER_MESSAGE;
import static softuni.exam.constants.Paths.PATH_TO_ASTRONOMER;

@Service
public class AstronomerServiceImpl implements AstronomerService {

    private ModelMapper mapper;

    private ValidationUtils validator;

    private AstronomerRepository astronomerRepository;

    private StarRepository starRepository;

    private XmlParserImpl xmlParser;

    @Autowired
    public AstronomerServiceImpl(ModelMapper mapper, ValidationUtils validator, AstronomerRepository astronomerRepository, StarRepository starRepository, XmlParserImpl xmlParser) {
        this.mapper = mapper;
        this.validator = validator;
        this.astronomerRepository = astronomerRepository;
        this.starRepository = starRepository;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return this.astronomerRepository.count() > 0;
    }

    @Override
    public String readAstronomersFromFile() throws IOException {
        return Files.readString(Path.of(PATH_TO_ASTRONOMER));
    }

    @Override
    public String importAstronomers() throws IOException, JAXBException {

        StringBuilder sb = new StringBuilder();
        List<AstronomerDto> astronomers = xmlParser.xmlParse(PATH_TO_ASTRONOMER, AstronomerWrapperDto.class)
                .getAstronomerDtos();                                                                                                                                           // ТУК ПРИ ДЕБЪГ ПРОВЕРЯВАТЕ ПАРСВАТ ЛИ СЕ                                                                                                                                                                                                       ДАННИТЕ ОТ XML-A

        astronomers.stream()
                .filter(astronomerDto -> {
                    boolean isValid = validator.isValid(astronomerDto);
                    if (starRepository.findById(astronomerDto.getObservingStarId()).isEmpty()) {
                        isValid = false;
                    }
                    if (astronomerRepository.findFirstByFirstNameAndLastName(astronomerDto.getFirstName(), astronomerDto.getLastName()).isPresent()) {
                        isValid = false;
                    }
                    sb.append(isValid ? String.format("Successfully imported astronomer %s %s - %.2f",
                            astronomerDto.getFirstName(), astronomerDto.getLastName(), astronomerDto.getAverageObservationHours())
                            : "Invalid astronomer").append(System.lineSeparator());
                    return isValid;
                })
                .map(astronomerDto -> {
                    Astronomer astronomer = mapper.map(astronomerDto, Astronomer.class);
                    astronomer.setStar(starRepository.findById(astronomerDto.getObservingStarId()).get());
                    return astronomer;
                })
                .forEach(astronomerRepository::saveAndFlush);
        return sb.toString();
    }
}