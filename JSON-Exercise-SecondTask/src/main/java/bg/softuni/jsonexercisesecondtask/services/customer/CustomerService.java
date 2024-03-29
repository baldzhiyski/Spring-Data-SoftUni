package bg.softuni.jsonexercisesecondtask.services.customer;

import bg.softuni.jsonexercisesecondtask.domain.dtos.customer.CustomerDtoWithSales;
import bg.softuni.jsonexercisesecondtask.domain.dtos.customer.CustomerWthCarsAndMoneyDto;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

public interface CustomerService {
    List<CustomerDtoWithSales> getAllCustomersOrderByBirthdate() throws IOException, JAXBException;

    List<CustomerWthCarsAndMoneyDto> getAllCustomerWithSpentMoney() throws IOException, JAXBException;
}
