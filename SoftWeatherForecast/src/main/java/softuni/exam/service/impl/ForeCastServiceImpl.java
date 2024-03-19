package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ForeCastFullInfoDto;
import softuni.exam.models.dto.ForeCastImportDto;
import softuni.exam.models.dto.wrapper.ForeCastWrapper;
import softuni.exam.models.entity.City;
import softuni.exam.models.entity.Country;
import softuni.exam.models.entity.ForeCast;
import softuni.exam.models.entity.enums.DayOfWeek;
import softuni.exam.repository.CityRepository;
import softuni.exam.repository.ForecastRepository;
import softuni.exam.service.ForecastService;
import softuni.exam.util.Messages;
import softuni.exam.util.Paths;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static softuni.exam.util.Messages.*;
import static softuni.exam.util.Paths.PATH_TO_FORECASTS;

@Service
public class ForeCastServiceImpl implements ForecastService {
    private static final String PATH_TO_FORECAST_SECOND = "src/main/resources/files/xml/forecasts.xml";
    private ModelMapper mapper;

    private ForecastRepository forecastRepository;

    private ValidationUtil validationUtil;

    private CityRepository cityRepository;

    private XmlParser xmlParser;

    @Autowired
    public ForeCastServiceImpl(ModelMapper mapper, ForecastRepository forecastRepository, ValidationUtil validationUtil, CityRepository cityRepository, XmlParser xmlParser) {
        this.mapper = mapper;
        this.forecastRepository = forecastRepository;
        this.validationUtil = validationUtil;
        this.cityRepository = cityRepository;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return this.forecastRepository.count()>0;
    }

    @Override
    public String readForecastsFromFile() throws IOException {
        return Files
                .readString(Path.of(PATH_TO_FORECAST_SECOND));
    }

    @Override
    public String importForecasts() throws IOException, JAXBException {
        ForeCastWrapper foreCastWrapper = xmlParser.fromFile(PATH_TO_FORECASTS, ForeCastWrapper.class);
        StringBuilder builder = new StringBuilder();

        List<ForeCastImportDto> forecasts = foreCastWrapper.getForecasts();

        for (ForeCastImportDto forecast : forecasts) {
            boolean isValid = validationUtil.isValid(forecast);

            if (isValid) {

                if (cityRepository.findFirstById(forecast.getCity()).isPresent()) {

                    City city = cityRepository.findFirstById(forecast.getCity()).get();

                    ForeCast mappedForeCast = this.mapper.map(forecast, ForeCast.class);

                    mappedForeCast.setCity(city);

                    builder.append(String.format(
                            VALID_FORECAST,forecast.getDayOfWeek(),
                            forecast.getMaxTemperature()
                    )).append(System.lineSeparator());
                    this.forecastRepository.saveAndFlush(mappedForeCast);
                }

            }else{
                builder.append(INVALID_FORECAST_MESSAGE)
                        .append(System.lineSeparator());
            }
        }
        return builder.toString();
    }

    @Override
    public String exportForecasts() {
        StringBuilder builder = new StringBuilder();
        Optional<List<ForeCast>> forecasts = this.forecastRepository.findAllByDayOfWeekAndCity_PopulationLessThan(DayOfWeek.FRIDAY, 150000L);

        forecasts.get()
                .stream()
                .map(foreCast -> mapper.map(foreCast, ForeCastFullInfoDto.class))
                .forEach(foreCastFullInfoDto -> builder.append(foreCastFullInfoDto.toString())
                        .append(System.lineSeparator()));

        return builder.toString();
    }
}
