package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softuni.exam.constants.Messages;
import softuni.exam.constants.Paths;
import softuni.exam.models.dto.VolcanologistDto;
import softuni.exam.models.dto.wrapper.VolcanologistWrapper;
import softuni.exam.models.entity.Volcano;
import softuni.exam.models.entity.Volcanologist;
import softuni.exam.repository.VolcanoRepository;
import softuni.exam.repository.VolcanologistRepository;
import softuni.exam.service.VolcanologistService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import static softuni.exam.constants.Messages.INVALID_VOLCANOLOGIST;
import static softuni.exam.constants.Messages.VALID_VOLCANOLOGIST;
import static softuni.exam.constants.Paths.PATH_VOLCANOLOGISTS;

@Service
public class VolcanologistServiceImpl implements VolcanologistService {
    private ModelMapper mapper;
    private XmlParser xmlParser;
    private VolcanologistRepository volcanologistRepository;
    private ValidationUtil validationUtil;

    private VolcanoRepository volcanoRepository;

    @Autowired
    public VolcanologistServiceImpl(ModelMapper mapper, XmlParser xmlParser, VolcanologistRepository volcanologistRepository, ValidationUtil validationUtil, VolcanoRepository volcanoRepository) {
        this.mapper = mapper;
        this.xmlParser = xmlParser;
        this.volcanologistRepository = volcanologistRepository;
        this.validationUtil = validationUtil;
        this.volcanoRepository = volcanoRepository;
    }

    @Override
    public boolean areImported() {
        return this.volcanologistRepository.count()>0;
    }

    @Override
    public String readVolcanologistsFromFile() throws IOException {
        return Files.readString(Path.of(PATH_VOLCANOLOGISTS));
    }

    @Override
    @Transactional
    public String importVolcanologists() throws IOException, JAXBException {
        StringBuilder builder = new StringBuilder();

        List<VolcanologistDto> volcanologistDtos = this.xmlParser.fromFile(PATH_VOLCANOLOGISTS, VolcanologistWrapper.class)
                .getVolcanologistDtos().stream().collect(Collectors.toList());

        volcanologistDtos.stream()
                .filter(volcanologistDto -> {
                    boolean isValid = this.validationUtil.isValid(volcanologistDto);
                    if (this.volcanologistRepository.findFirstByFirstNameAndLastName(volcanologistDto.getFirstName(),volcanologistDto.getLastName()).isPresent()) {
                        isValid=false;
                    }
                    if (this.volcanoRepository.findFirstById(volcanologistDto.getVolcano()).isEmpty()) {
                        isValid=false;
                    }

                    String toAppend = isValid ? String.format(VALID_VOLCANOLOGIST,volcanologistDto.getFirstName(),volcanologistDto.getLastName())
                            : INVALID_VOLCANOLOGIST;
                    builder.append(toAppend)
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(volcanologistDto -> {
                    Volcanologist volcanologist = this.mapper.map(volcanologistDto, Volcanologist.class);
                    Volcano volcano = this.volcanoRepository.getById(volcanologistDto.getVolcano());

                    volcanologist.setVolcano(volcano);

                    return volcanologist;
                })
                .forEach(this.volcanologistRepository::saveAndFlush);
        return builder.toString();
    }
}