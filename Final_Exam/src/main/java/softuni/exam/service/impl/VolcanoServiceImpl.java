package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softuni.exam.models.dto.VolcanoDto;
import softuni.exam.models.dto.VolcanoExtractInfoDto;
import softuni.exam.models.entity.Country;
import softuni.exam.models.entity.Volcano;
import softuni.exam.repository.CountryRepository;
import softuni.exam.repository.VolcanoRepository;
import softuni.exam.service.VolcanoService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static softuni.exam.constants.Messages.INVALID_VOLCANO;
import static softuni.exam.constants.Messages.VALID_VOLCANO;
import static softuni.exam.constants.Paths.PATH_VOLCANOES;

@Service
public class VolcanoServiceImpl implements VolcanoService {
    private ModelMapper mapper;
    private Gson gson;
    private VolcanoRepository volcanoRepository;

    private ValidationUtil validationUtil;

    private CountryRepository countryRepository;

    @Autowired
    public VolcanoServiceImpl(ModelMapper mapper, Gson gson, VolcanoRepository volcanoRepository, ValidationUtil validationUtil, CountryRepository countryRepository) {
        this.mapper = mapper;
        this.gson = gson;
        this.volcanoRepository = volcanoRepository;
        this.validationUtil = validationUtil;
        this.countryRepository = countryRepository;
    }

    @Override
    public boolean areImported() {
        return this.volcanoRepository.count()>0;
    }

    @Override
    public String readVolcanoesFileContent() throws IOException {
        return Files.readString(Path.of(PATH_VOLCANOES));
    }

    @Override
    @Transactional
    public String importVolcanoes() throws IOException {
        StringBuilder builder = new StringBuilder();
        Arrays.stream(this.gson.fromJson(readVolcanoesFileContent(), VolcanoDto[].class))
                .filter(volcanoDto -> {
                    boolean isValid = this.validationUtil.isValid(volcanoDto);

                    if (this.volcanoRepository.findFirstByName(volcanoDto.getName()).isPresent()) {
                        isValid=false;
                    }
                    String resultToAppend = isValid ? String.format(VALID_VOLCANO,volcanoDto.getName(),
                            volcanoDto.getVolcanoType().toString()) : INVALID_VOLCANO;
                    builder.append(resultToAppend).append(System.lineSeparator());

                    return isValid;
                })
                .map(volcanoDto -> {
                    Volcano volcano = this.mapper.map(volcanoDto, Volcano.class);
                    Country country = this.countryRepository.getById(volcanoDto.getCountry());

                    volcano.setCountry(country);
                    return volcano;
                })
                .forEach(this.volcanoRepository::saveAndFlush);

        return builder.toString();
    }

    @Override
    public String exportVolcanoes() {
        StringBuilder builder = new StringBuilder();

        this.volcanoRepository.findAllByIsActiveAndElevationGreaterThanAndLastEruptionNotNullOrderByElevationDesc(true,3000)
                .stream().map(volcano -> this.mapper.map(volcano, VolcanoExtractInfoDto.class))
                .forEach(volcanoExtractInfoDto -> builder.append(volcanoExtractInfoDto.toString())
                        .append(System.lineSeparator()));

        return builder.toString();
    }
}