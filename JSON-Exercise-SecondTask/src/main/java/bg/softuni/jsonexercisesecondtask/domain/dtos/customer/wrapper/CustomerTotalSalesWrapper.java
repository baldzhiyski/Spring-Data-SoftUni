package bg.softuni.jsonexercisesecondtask.domain.dtos.customer.wrapper;

import bg.softuni.jsonexercisesecondtask.domain.dtos.customer.CustomerWthCarsAndMoneyDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerTotalSalesWrapper {

    @XmlElement(name = "customer")
    private List<CustomerWthCarsAndMoneyDto> customers;
}
