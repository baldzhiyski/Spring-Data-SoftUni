package bg.softuni.jsonexercisesecondtask.services.customer;

import bg.softuni.jsonexercisesecondtask.domain.dtos.customer.CustomerDto;
import bg.softuni.jsonexercisesecondtask.domain.dtos.customer.CustomerDtoWithSales;
import bg.softuni.jsonexercisesecondtask.domain.dtos.customer.CustomerWthCarsAndMoneyDto;
import bg.softuni.jsonexercisesecondtask.domain.dtos.customer.wrapper.CustomerFullInfoWrapper;
import bg.softuni.jsonexercisesecondtask.repositories.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import static bg.softuni.jsonexercisesecondtask.constants.Paths.*;
import static bg.softuni.jsonexercisesecondtask.constants.Utils.writeIntoXmlFile;
import static bg.softuni.jsonexercisesecondtask.constants.Utils.writeJsonOnFile;

@Service
public class CustomerServiceImpl implements CustomerService {
    private CustomerRepository customerRepository;
    private ModelMapper mapper;


    public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper mapper) {
        this.customerRepository = customerRepository;
        this.mapper = mapper;
    }

    @Override
    public List<CustomerDtoWithSales> getAllCustomersOrderByBirthdate() throws IOException, JAXBException {
        List<CustomerDtoWithSales> customers = this.customerRepository
                .findAllOrderByBirthdate()
                .stream()
                .map(CustomerDtoWithSales::mapFromCustomer)
                .collect(Collectors.toList());

        writeJsonOnFile(customers, Path.of(PATH_FIRST_EX));

        List<CustomerDto> customersDtos = this.customerRepository
                .findAllOrderByBirthdate()
                .stream()
                .map(customer -> mapper.map(customer, CustomerDto.class))
                .collect(Collectors.toList());

        writeIntoXmlFile(new CustomerFullInfoWrapper(customersDtos),Path.of(PATH_FIRST_EX_XML));


        return customers;
    }

    @Override
    public List<CustomerWthCarsAndMoneyDto> getAllCustomerWithSpentMoney() throws IOException {
       List<CustomerWthCarsAndMoneyDto> customers = this.customerRepository.findAllByCountOfCars();

        writeJsonOnFile(customers, Path.of(PATH_FIFTH_EX));

        return customers;
    }
}
