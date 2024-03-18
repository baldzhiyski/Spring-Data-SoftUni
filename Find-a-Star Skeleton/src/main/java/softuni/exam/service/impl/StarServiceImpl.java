package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softuni.exam.models.dto.StarDto;
import softuni.exam.models.dto.StarExportDto;
import softuni.exam.models.entity.Constellation;
import softuni.exam.models.entity.Star;
import softuni.exam.models.entity.enums.StarType;
import softuni.exam.repository.ConstellationRepository;
import softuni.exam.repository.StarRepository;
import softuni.exam.service.StarService;
import softuni.exam.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static softuni.exam.constants.Messages.INVALID_STAR_MESSAGE;
import static softuni.exam.constants.Messages.VALID_STAR_MESSAGE;
import static softuni.exam.constants.Paths.PATH_TO_STARS;

@Service
public class StarServiceImpl implements StarService {
    private StarRepository starRepository;
    private Gson gson;

    private ModelMapper mapper;
    private ConstellationRepository constellationRepository;

    private ValidationUtils validator;

    @Autowired
    public StarServiceImpl(StarRepository starRepository, Gson gson, ModelMapper mapper, ConstellationRepository constellationRepository, ValidationUtils validator) {
        this.starRepository = starRepository;
        this.gson = gson;
        this.mapper = mapper;
        this.constellationRepository = constellationRepository;
        this.validator = validator;
    }

    @Override
    public boolean areImported() {
        return this.starRepository.count()>0;
    }

    @Override
    public String readStarsFileContent() throws IOException {
        return Files.readString(Path.of(PATH_TO_STARS));
    }

    @Override
    @Transactional
    public String importStars() throws IOException {
        StringBuilder builder = new StringBuilder();

        List<StarDto> dtos = Arrays.stream(this.gson.fromJson(readStarsFileContent(), StarDto[].class))
                .collect(Collectors.toList());

        for (StarDto starDto : dtos) {
            boolean isValid = this.validator.isValid(starDto);
            if(this.starRepository.findFirstByName(starDto.getName()).isPresent()){
                isValid=false;
            }

            if(isValid){
                builder.append(String.format(VALID_STAR_MESSAGE,
                                starDto.getName(),
                                starDto.getLightYears()))
                        .append(System.lineSeparator());

                Constellation byId = this.constellationRepository.getById(starDto.getConstellation());
                Star mapped = this.mapper.map(starDto, Star.class);
                mapped.setConstellation(byId);
                this.starRepository.saveAndFlush(mapped);

            }else{
                builder.append(INVALID_STAR_MESSAGE)
                        .append(System.lineSeparator());
            }
        }

        return builder.toString();
    }

    @Override
    public String exportStars() {
        StringBuilder builder = new StringBuilder();
        Set<Star> stars = this.starRepository.findAllByStarTypeAndAstronomersIsNullOrderByLightYears(StarType.RED_GIANT).orElseThrow();

        List<StarExportDto> dtos = stars.stream().map(star -> mapper.map(star, StarExportDto.class)).collect(Collectors.toList());

        dtos.forEach(dto->builder.append(dto.toString()).append(System.lineSeparator()));

        return builder.toString();
    }
}
