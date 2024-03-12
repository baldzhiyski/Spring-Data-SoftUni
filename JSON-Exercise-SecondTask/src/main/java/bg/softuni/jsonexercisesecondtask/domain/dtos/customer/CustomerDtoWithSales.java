package bg.softuni.jsonexercisesecondtask.domain.dtos.customer;

import bg.softuni.jsonexercisesecondtask.constants.Utils;
import bg.softuni.jsonexercisesecondtask.domain.dtos.sale.SaleDto;
import bg.softuni.jsonexercisesecondtask.domain.entities.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static bg.softuni.jsonexercisesecondtask.constants.Utils.MAPPER;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDtoWithSales {
    private String name;
    private Date birthDate;

    private Boolean isYoungDriver;

    private List<SaleDto> sales;

    public static CustomerDtoWithSales mapFromCustomer(Customer customer) {
        CustomerDtoWithSales dto = MAPPER.map(customer, CustomerDtoWithSales.class);
        List<SaleDto> saleDtos = customer.getSales().stream()
                .map(sale -> SaleDto.mapFromSale(sale))
                .collect(Collectors.toList());
        dto.setSales(saleDtos);
        return dto;
    }

}
