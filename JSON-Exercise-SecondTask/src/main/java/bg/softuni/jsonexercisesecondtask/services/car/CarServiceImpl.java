package bg.softuni.jsonexercisesecondtask.services.car;

import bg.softuni.jsonexercisesecondtask.constants.Paths;
import bg.softuni.jsonexercisesecondtask.constants.Utils;
import bg.softuni.jsonexercisesecondtask.domain.dtos.car.CarDto;
import bg.softuni.jsonexercisesecondtask.domain.dtos.car.CarDtoWithParts;
import bg.softuni.jsonexercisesecondtask.repositories.CarRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService{
    private CarRepository carRepository;
    private ModelMapper mapper;

    public CarServiceImpl(CarRepository carRepository, ModelMapper mapper) {
        this.carRepository = carRepository;
        this.mapper = mapper;
    }

    @Override
    public List<CarDto> getAllCarsMadeFromToyota(String make) throws IOException {
        List<CarDto> cars = this.carRepository
                .findDistinctByMakeOrderByModelAscTravelledDistanceDesc(make)
                .stream().map(car -> mapper.map(car, CarDto.class))
                .collect(Collectors.toList());

        Utils.writeJsonOnFile(cars, Path.of(Paths.PATH_SECOND_EX));

        return cars;

    }

    @Override
    public List<CarDtoWithParts> getAllCarsWithInfoForTheParts() throws IOException {
        List<CarDtoWithParts> cars = this.carRepository.findAll()
                .stream()
                .map(CarDtoWithParts::fromCar)
                .collect(Collectors.toList());

        Utils.writeJsonOnFile(cars, Path.of(Paths.PATH_FORTH_EX));

        return  cars;
    }
}
