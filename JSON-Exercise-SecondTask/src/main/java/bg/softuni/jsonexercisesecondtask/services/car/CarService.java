package bg.softuni.jsonexercisesecondtask.services.car;

import bg.softuni.jsonexercisesecondtask.domain.dtos.car.CarDto;
import bg.softuni.jsonexercisesecondtask.domain.dtos.car.CarDtoWithParts;

import java.io.IOException;
import java.util.List;

public interface CarService {
    List<CarDto> getAllCarsMadeFromToyota(String make) throws IOException;

    List<CarDtoWithParts> getAllCarsWithInfoForTheParts() throws IOException;
}
