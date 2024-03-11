package bg.softuni.jsonexercisesecondtask.services.seed;

import bg.softuni.jsonexercisesecondtask.domain.dtos.car.CarMakeModelDistanceDto;
import bg.softuni.jsonexercisesecondtask.domain.dtos.customer.CustomerNameBirthDateDriverDto;
import bg.softuni.jsonexercisesecondtask.domain.dtos.part.PartDto;
import bg.softuni.jsonexercisesecondtask.domain.dtos.supplier.SupplierNameImporterDto;
import bg.softuni.jsonexercisesecondtask.domain.entities.*;
import bg.softuni.jsonexercisesecondtask.repositories.*;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

import static bg.softuni.jsonexercisesecondtask.constants.Discounts.DISCOUNTS;
import static bg.softuni.jsonexercisesecondtask.constants.Paths.*;

@Service
public class SeedServiceImpl  implements SeedService{
    private Gson gson ;
    private ModelMapper mapper;
    private SupplierRepository supplierRepository;

    private PartsRepository partsRepository;

    private CarRepository carRepository;

    private CustomerRepository customerRepository;

    private SaleRepository saleRepository;

    @Autowired
    public SeedServiceImpl(Gson gson, ModelMapper mapper, SupplierRepository supplierRepository, PartsRepository partsRepository, CarRepository carRepository, CustomerRepository customerRepository, SaleRepository saleRepository) {
        this.gson = gson;
        this.mapper = mapper;
        this.supplierRepository = supplierRepository;
        this.partsRepository = partsRepository;
        this.carRepository = carRepository;
        this.customerRepository = customerRepository;
        this.saleRepository = saleRepository;
    }

    @Override
    public void seedSuppliers() throws FileNotFoundException {
        if(this.supplierRepository.count()> 0) return;

        FileReader fileReader = new FileReader(PATH_TO_SUPPLIERS_INPUT);
        List<Supplier> suppliers = Arrays.stream(gson.fromJson(fileReader, SupplierNameImporterDto[].class))
                .map(supplierNameImporterDto -> mapper.map(supplierNameImporterDto, Supplier.class))
                .collect(Collectors.toList());

        this.supplierRepository.saveAllAndFlush(suppliers);
    }

    @Override
    public void seedParts() throws FileNotFoundException {
        if(this.partsRepository.count()> 0) return;

        FileReader fileReader = new FileReader(PATH_TO_PARTS_INPUT);

        List<Part> parts = Arrays.stream(gson.fromJson(fileReader, PartDto[].class))
                .map(partDto -> mapper.map(partDto, Part.class))
                .map(this::setRandomSupplier)
                .collect(Collectors.toList());

        this.partsRepository.saveAllAndFlush(parts);
    }

    private Part setRandomSupplier(Part part) {
        Supplier supplier = this.supplierRepository.getARandomEntity()
                .orElseThrow(NoSuchElementException::new);

        part.setSupplier(supplier);

        return part;
    }

    @Override
    public void seedCars() throws FileNotFoundException {
        if(this.carRepository.count() > 0 ) return;

        FileReader fileReader = new FileReader(PATH_TO_CARS_INPUT);

        List<Car> cars = Arrays.stream(gson.fromJson(fileReader, CarMakeModelDistanceDto[].class))
                .map(carMakeModelDistanceDto -> mapper.map(carMakeModelDistanceDto, Car.class))
                .map(this::setBetweenThreeToFiveRandomParts)
                .collect(Collectors.toList());

        this.carRepository.saveAllAndFlush(cars);
    }

    private Car setBetweenThreeToFiveRandomParts(Car car) {
        int requiredParts = car.getModel().length() < 5 ? 5 : 3;

        Set<Part> parts = new LinkedHashSet<>(); // Use LinkedHashSet to preserve insertion order
        while (parts.size() < requiredParts) {
            Part part = this.partsRepository.getRandomEntity().orElseThrow(NoSuchElementException::new);
            if (car.getParts() != null) {
                if (!car.getParts().contains(part)) {
                    parts.add(part);
                }
            } else {
                parts.add(part);
            }
        }

        car.setParts(parts);

        return car;
    }

    @Override
    public void seedCustomers() throws FileNotFoundException {
        if(this.customerRepository.count() > 0) return;

        FileReader fileReader = new FileReader(PATH_TO_CUSTOMERS_INPUT);

        List<Customer> customers = Arrays.stream(gson.fromJson(fileReader, CustomerNameBirthDateDriverDto[].class))
                .map(customerNameBirthDateDriverDto -> mapper.map(customerNameBirthDateDriverDto, Customer.class))
                .collect(Collectors.toList());

        this.customerRepository.saveAllAndFlush(customers);
    }

    @Override
    public void seedSalesRecords() throws FileNotFoundException {
        if (saleRepository.count() > 0) return;
        Random random = new Random();
        Set<Sale> sales = new HashSet<>();
        List<Car> availableCars = this.carRepository.findAll();
        Collections.shuffle(availableCars);
        for (int i = 0; i < 10 && i < availableCars.size(); i++) {
            int randomIndex = random.nextInt(DISCOUNTS.length);
            Car randomCar = availableCars.get(i);
            Customer randomCustomer = this.customerRepository.getRandomEntity()
                    .orElseThrow(NoSuchElementException::new);
            Sale sale = new Sale(DISCOUNTS[randomIndex], randomCar, randomCustomer);
            sales.add(sale);
        }
        this.saleRepository.saveAllAndFlush(sales);
    }

}
