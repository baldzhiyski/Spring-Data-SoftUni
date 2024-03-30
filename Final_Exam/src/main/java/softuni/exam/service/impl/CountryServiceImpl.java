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
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static softuni.exam.constants.Messages.INVALID_COUNTRY;
import static softuni.exam.constants.Messages.VALID_COUNTRY;
import static softuni.exam.constants.Paths.PATH_COUNTRIES;

@Service
public class CountryServiceImpl implements CountryService {
    private ModelMapper mapper;

    private Gson gson;

    private CountryRepository countryRepository;

    private ValidationUtil validationUtil;

    @Autowired
    public CountryServiceImpl(ModelMapper mapper, Gson gson, CountryRepository countryRepository, ValidationUtil validationUtil) {
        this.mapper = mapper;
        this.gson = gson;
        this.countryRepository = countryRepository;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.countryRepository.count()>0;
    }

    @Override
    public String readCountriesFromFile() throws IOException {
        return Files.readString(Path.of(PATH_COUNTRIES));
    }

    @Override
    public String importCountries() throws IOException {
        StringBuilder builder = new StringBuilder();

        Arrays.stream(this.gson.fromJson(readCountriesFromFile(), CountryDto[].class))
                .filter(countryDto -> {
                    boolean isValid = this.validationUtil.isValid(countryDto);

                    if(this.countryRepository.findFirstByName(countryDto.getName()).isPresent()){
                        isValid=false;
                    }

                    if(isValid){
                        builder.append(String.format(VALID_COUNTRY,countryDto.getName(),countryDto.getCapital()))
                                .append(System.lineSeparator());
                    }else{
                        builder.append(INVALID_COUNTRY)
                                .append(System.lineSeparator());
                    }
                    return isValid;
                })
                .map(countryDto -> this.mapper.map(countryDto, Country.class))
                .forEach(this.countryRepository::saveAndFlush);

        return  builder.toString();
    }
}
