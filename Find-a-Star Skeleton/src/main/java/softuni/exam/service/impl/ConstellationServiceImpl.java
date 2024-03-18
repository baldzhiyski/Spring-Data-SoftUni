package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.constants.Messages;
import softuni.exam.constants.Paths;
import softuni.exam.models.dto.ConstellationDto;
import softuni.exam.models.entity.Constellation;
import softuni.exam.repository.ConstellationRepository;
import softuni.exam.service.ConstellationService;
import softuni.exam.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static softuni.exam.constants.Messages.INVALID_CONSTELLATION_MESSAGE;
import static softuni.exam.constants.Messages.VALID_CONSTELLATION_MESSAGE;
import static softuni.exam.constants.Paths.PATH_TO_CONSTELLATION;

@Service
public class ConstellationServiceImpl implements ConstellationService {

    private ConstellationRepository constellationRepository;

    private Gson gson;

    private ModelMapper mapper;

    private ValidationUtils validator;

    @Autowired
    public ConstellationServiceImpl(ConstellationRepository constellationRepository, Gson gson, ModelMapper mapper, ValidationUtils validator) {
        this.constellationRepository = constellationRepository;
        this.gson = gson;
        this.mapper = mapper;
        this.validator = validator;
    }

    @Override
    public boolean areImported() {
        return this.constellationRepository.count()>0;
    }

    @Override
    public String readConstellationsFromFile() throws IOException {
        return Files.readString(Path.of(PATH_TO_CONSTELLATION));
    }

    @Override
    public String importConstellations() throws IOException {
        StringBuilder builder = new StringBuilder();
        List<ConstellationDto> dtos = Arrays.stream(this.gson.fromJson(readConstellationsFromFile(), ConstellationDto[].class))
                .collect(Collectors.toList());

        for (ConstellationDto dto : dtos) {
            Optional<Constellation> firstByName = this.constellationRepository.findFirstByName(dto.getName());
            boolean isValid = this.validator.isValid(dto);
            if (firstByName.isPresent()){
                isValid= false;
            }
            if(isValid){
                builder.append(String.format(VALID_CONSTELLATION_MESSAGE,
                                dto.getName(),dto.getDescription()))
                        .append(System.lineSeparator());

                Constellation toBePersisted = this.mapper.map(dto, Constellation.class);

                this.constellationRepository.saveAndFlush(toBePersisted);

            }else{
                builder.append(INVALID_CONSTELLATION_MESSAGE)
                        .append(System.lineSeparator());
            }
        }

        return builder.toString();
    }
}
