package bg.softuni.mapping_objects_lab;

import bg.softuni.mapping_objects_lab.entities.Address;
import bg.softuni.mapping_objects_lab.entities.Employee;
import bg.softuni.mapping_objects_lab.entities.dtos.AddressDTO;
import bg.softuni.mapping_objects_lab.entities.dtos.CreateEmployeeDTO;
import bg.softuni.mapping_objects_lab.entities.dtos.EmployeeNameAndSalaryDTO;
import bg.softuni.mapping_objects_lab.services.AddressService;
import bg.softuni.mapping_objects_lab.services.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

@Component
public class AppMain implements CommandLineRunner {
    private final AddressService addressService;
    private final EmployeeService employeeService;
    private final Scanner scanner;

    private final ModelMapper mapper;

    @Autowired
    public AppMain(AddressService addressService, EmployeeService employeeService, Scanner scanner, ModelMapper mapper) {
        this.addressService = addressService;
        this.employeeService = employeeService;
        this.scanner = scanner;
        this.mapper = mapper;
    }

    @Override
    public void run(String... args) throws Exception {
       printEmployeeNames();

    }
    private void printEmployeeNameAndSalary() {
        EmployeeNameAndSalaryDTO result = this.employeeService.findNameAndSalaryById(1L);

        System.out.println(result.getSalary());
    }

    private void printEmployeeNames() {
        System.out.println(this.employeeService.findNamesById(1L));
    }

    private void printAllEmployees() {
        this.employeeService.findAll()
                .forEach(System.out::println);
    }

    private void createEmployee() {
        String firstName = scanner.nextLine();
        BigDecimal salary = new BigDecimal(scanner.nextLine());
        LocalDate birthday = LocalDate.parse(scanner.nextLine());

//        long addressId = Long.parseLong(scanner.nextLine());

        String country = scanner.nextLine();
        String city = scanner.nextLine();

        AddressDTO address = new AddressDTO(country, city);

        CreateEmployeeDTO employeeDTO =
                new CreateEmployeeDTO(firstName, null, salary, birthday, address);

        Employee employee = this.employeeService.create(employeeDTO);

        System.out.println(employee);

    }

    private void createAddress() {
        String country = scanner.nextLine();
        String city = scanner.nextLine();

        AddressDTO data = new AddressDTO(country,city);
        Address address = addressService.create(data);

        System.out.println(address);
    }
}
