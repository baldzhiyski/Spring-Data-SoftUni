package bg.softuni.mapping_objects_lab;

import bg.softuni.mapping_objects_lab.services.AddressService;
import bg.softuni.mapping_objects_lab.services.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;

import java.util.Scanner;


public class ModelMapperApp implements CommandLineRunner {
    private final AddressService addressService;
    private final EmployeeService employeeService;
    private final Scanner scanner;

    private final ModelMapper mapper;


    public ModelMapperApp(AddressService addressService, EmployeeService employeeService, Scanner scanner, ModelMapper mapper) {
        this.addressService = addressService;
        this.employeeService = employeeService;
        this.scanner = scanner;
        this.mapper = mapper;
    }
    @Override
    public void run(String... args) throws Exception {

//        Address address = new Address("Bulgaria", "Sofia");
//
//        Employee employee = new Employee("Nikolay", BigDecimal.TEN,address);
//
//        TypeMap<Employee, EmployeeDTO> typeMap = mapper.createTypeMap(Employee.class, EmployeeDTO.class);
//
//        typeMap.addMappings(mapping-> mapping.
//                map(source->
//                        source.getAddress().getCity(), EmployeeDTO::setCity));
//
//        System.out.println(typeMap.map(employee));
    }
}
