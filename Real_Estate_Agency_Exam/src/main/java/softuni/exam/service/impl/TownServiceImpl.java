package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.constants.Messages;
import softuni.exam.constants.Paths;
import softuni.exam.models.dto.TownDto;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.TownService;
import softuni.exam.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static softuni.exam.constants.Messages.INVALID_TOWN;
import static softuni.exam.constants.Messages.VALID_TOWN;
import static softuni.exam.constants.Paths.PATH_TO_TOWNS;

@Service
public class TownServiceImpl implements TownService {
    private Gson gson;
    private TownRepository townRepository;

    private ValidationUtils validationUtils;

    private ModelMapper mapper;

    @Autowired
    public TownServiceImpl(Gson gson, TownRepository townRepository, ValidationUtils validationUtils, ModelMapper mapper) {
        this.gson = gson;
        this.townRepository = townRepository;
        this.validationUtils = validationUtils;
        this.mapper = mapper;
    }

    @Override
    public boolean areImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return Files.readString(Path.of(PATH_TO_TOWNS));
    }

    @Override
    public String importTowns() throws IOException {
        StringBuilder builder = new StringBuilder();

        Set<TownDto> townDtoList = Arrays.stream(this.gson.fromJson(readTownsFileContent(), TownDto[].class))
                .collect(Collectors.toSet());

        Set<Town> towns= townDtoList.stream()
                .filter(townDto -> {
                    boolean isValid = this.validationUtils.isValid(townDto);

                    if (this.townRepository.findFirstByTownName(townDto.getTownName()).isPresent()) {
                        isValid = false;
                    }
                    if (isValid) {
                        builder.append(String.format(VALID_TOWN,
                                        townDto.getTownName(), townDto.getPopulation()))
                                .append(System.lineSeparator());
                    } else {
                        builder.append(INVALID_TOWN)
                                .append(System.lineSeparator());
                    }
                    return isValid;
                })
                .map(townDto -> this.mapper.map(townDto, Town.class))
                .collect(Collectors.toSet());

        this.townRepository.saveAllAndFlush(towns);

        return builder.toString();
    }
}
