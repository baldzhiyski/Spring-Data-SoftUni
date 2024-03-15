package bg.softuni.jsonexercisesecondtask.services.car;

import bg.softuni.jsonexercisesecondtask.constants.Paths;
import bg.softuni.jsonexercisesecondtask.constants.Utils;
import bg.softuni.jsonexercisesecondtask.domain.dtos.car.CarDto;
import bg.softuni.jsonexercisesecondtask.domain.dtos.car.CarDtoWithParts;
import bg.softuni.jsonexercisesecondtask.domain.dtos.car.wrapper.CarWithPartsWrapper;
import bg.softuni.jsonexercisesecondtask.domain.dtos.car.wrapper.CarWrapperWithIdInfo;
import bg.softuni.jsonexercisesecondtask.repositories.CarRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
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
    public List<CarDto> getAllCarsMadeFromToyota(String make) throws IOException, JAXBException {
        List<CarDto> cars = this.carRepository
                .findDistinctByMakeOrderByModelAscTravelledDistanceDesc(make)
                .stream().map(car -> mapper.map(car, CarDto.class))
                .collect(Collectors.toList());

        CarWrapperWithIdInfo carWrapper = new CarWrapperWithIdInfo(cars);

        Utils.writeJsonOnFile(cars, Path.of(Paths.PATH_SECOND_EX));
        Utils.writeIntoXmlFile(carWrapper, Path.of(Paths.PATH_SECOND_EX_XML));

        return cars;

    }

    @Override
    public List<CarDtoWithParts> getAllCarsWithInfoForTheParts() throws IOException, JAXBException {
        List<CarDtoWithParts> cars = this.carRepository.findAll()
                .stream()
                .map(CarDtoWithParts::fromCar)
                .collect(Collectors.toList());

        Utils.writeJsonOnFile(cars, Path.of(Paths.PATH_FORTH_EX));
        Utils.writeIntoXmlFile(new CarWithPartsWrapper(cars), Path.of(Paths.PATH_FORTH_EX_XML));


        return  cars;
    }
}
