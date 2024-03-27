package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.constants.Messages;
import softuni.exam.constants.Paths;
import softuni.exam.models.dto.CountryDto;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CountryService;
import softuni.exam.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static softuni.exam.constants.Paths.PATH_COUNTRIES;

@Service
public class CountryServiceImpl implements CountryService {

    private CountryRepository countryRepository;

    private ModelMapper mapper;

    private ValidationUtils validationUtils;

    private Gson gson;

    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository, ModelMapper mapper, ValidationUtils validationUtils, Gson gson) {
        this.countryRepository = countryRepository;
        this.mapper = mapper;
        this.validationUtils = validationUtils;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return this.countryRepository.count()>0;
    }

    @Override
    public String readCountriesFileContent() throws IOException {
        return Files.readString(Path.of(PATH_COUNTRIES));
    }

    @Override
    public String importCountries() throws IOException {
        StringBuilder builder = new StringBuilder();

        Set<CountryDto> dtos = Arrays.stream(this.gson.fromJson(readCountriesFileContent(), CountryDto[].class))
                .collect(Collectors.toSet());

        dtos.stream()
                .filter(countryDto -> {
                    boolean isValid = this.validationUtils.isValid(countryDto);
                    String toAppend = isValid ? String.format(Messages.VALID_COUNTRY,countryDto.getName(),countryDto.getCountryCode())
                            : Messages.INVALID_COUNTRY;
                     builder.append(toAppend).append(System.lineSeparator());
                     return isValid;
                })
                .map(countryDto -> this.mapper.map(countryDto, Country.class))
                .forEach(this.countryRepository::saveAndFlush);
        return builder.toString();
    }
}
