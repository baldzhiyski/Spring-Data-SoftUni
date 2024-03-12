package bg.softuni.jsonexercisesecondtask.services.customer;

import bg.softuni.jsonexercisesecondtask.domain.dtos.customer.CustomerDtoWithSales;

import java.io.IOException;
import java.util.List;

public interface CustomerService {
    List<CustomerDtoWithSales> getAllCustomersOrderByBirthdate() throws IOException;
}
