package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CountryImportDto;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CountryService;
import softuni.exam.util.ValidationUtil;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static softuni.exam.util.Messages.INVALID_COUNTRY_MESSAGE;
import static softuni.exam.util.Messages.VALID_COUNTRY_MESSAGE;
import static softuni.exam.util.Paths.PATH_TO_COUNTRIES;

@Service
public class CountryServiceImpl implements CountryService {

    private CountryRepository countryRepository;
    private ModelMapper mapper;

    private Gson gson;

    private ValidationUtil validationUtil;

    public CountryServiceImpl(CountryRepository countryRepository, ModelMapper mapper, Gson gson, ValidationUtil validationUtil) {
        this.countryRepository = countryRepository;
        this.mapper = mapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.countryRepository.count() > 0;
    }

    @Override
    public String readCountriesFromFile() throws IOException {
        return Files.readString(PATH_TO_COUNTRIES);

    }

    @Override
    public String importCountries() throws IOException {
        StringBuilder builder = new StringBuilder();
        Arrays.stream(this.gson.fromJson(readCountriesFromFile(), CountryImportDto[].class))
                .filter(countryImportDto -> {
                    boolean isValid = validationUtil.isValid(countryImportDto);

                    if (this.countryRepository.findFirstByCountryName(countryImportDto.getCountryName()).isPresent()) {
                        isValid=false;
                    }

                    if(isValid){
                        builder.append(String.format(VALID_COUNTRY_MESSAGE,
                                        countryImportDto.getCountryName(),countryImportDto.getCurrency()))
                                .append(System.lineSeparator());

                        Country mapped = this.mapper.map(countryImportDto, Country.class);
                        this.countryRepository.saveAndFlush(mapped);
                    }else{
                        builder.append(INVALID_COUNTRY_MESSAGE)
                                .append(System.lineSeparator());
                    }
                    return isValid;
                })
                .forEach(countryImportDto -> {});
        return builder.toString();
    }
}
