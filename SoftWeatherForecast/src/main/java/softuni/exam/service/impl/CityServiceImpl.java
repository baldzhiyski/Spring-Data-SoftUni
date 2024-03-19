package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CityImportDto;
import softuni.exam.models.entity.City;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CityRepository;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CityService;
import softuni.exam.util.Messages;
import softuni.exam.util.Paths;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static softuni.exam.util.Messages.INVALID_CITY_MESSAGE;
import static softuni.exam.util.Messages.VALID_CITY_MESSAGE;
import static softuni.exam.util.Paths.PATH_TO_CITIES;

@Service
public class CityServiceImpl implements CityService {
    private CityRepository cityRepository;

    private CountryRepository countryRepository;

    private ModelMapper mapper;

    private ValidationUtil validationUtil;
    private Gson gson;


    @Autowired
    public CityServiceImpl(CityRepository cityRepository, CountryRepository countryRepository, ModelMapper mapper, ValidationUtil validationUtil, Gson gson) {
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
        this.mapper = mapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return this.cityRepository.count() > 0;
    }

    @Override
    public String readCitiesFileContent() throws IOException {
        return Files.readString(Path.of(PATH_TO_CITIES));
    }

    @Override
    public String importCities() throws IOException {
        StringBuilder builder = new StringBuilder();
        List<CityImportDto> cityImportsDtos = Arrays.stream(this.gson.fromJson(readCitiesFileContent(), CityImportDto[].class))
                .collect(Collectors.toList());


        for (CityImportDto cityImportsDto : cityImportsDtos) {
            boolean isValid = this.validationUtil.isValid(cityImportsDto);
            if (this.cityRepository.findFirstByCityName(cityImportsDto.getCityName()).isPresent()) {
                isValid=false;
            }

            if(isValid){
                Optional<Country> firstById = this.countryRepository.findFirstById(cityImportsDto.getCountry());
                if (firstById.isPresent()) {
                    builder.append(String.format(VALID_CITY_MESSAGE,cityImportsDto.getCityName(),
                                    cityImportsDto.getPopulation()))
                            .append(System.lineSeparator());
                    City city = this.mapper.map(cityImportsDto, City.class);

                    city.setCountry(firstById.get());
                    this.cityRepository.saveAndFlush(city);
                }
            }else{
                builder.append(INVALID_CITY_MESSAGE)
                        .append(System.lineSeparator());
            }
        }

        return builder.toString();
    }
}
