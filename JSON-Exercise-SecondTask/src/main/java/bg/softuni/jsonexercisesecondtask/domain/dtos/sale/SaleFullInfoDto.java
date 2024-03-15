package bg.softuni.jsonexercisesecondtask.domain.dtos.sale;

import bg.softuni.jsonexercisesecondtask.domain.dtos.car.CarDto;
import bg.softuni.jsonexercisesecondtask.domain.entities.Sale;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "sale")
@XmlAccessorType(XmlAccessType.FIELD)
public class SaleFullInfoDto {

    @XmlElement
    private CarDto car;
    @XmlElement(name = "customer-name")
    private String customerName;

    @XmlElement
    private Double discount;

    @XmlElement
    private Double price;

    @XmlElement
    private Double priceWithDiscount;

    public  static  SaleFullInfoDto fromSaleToDto(Sale sale){
        CarDto carDto = CarDto.fromCar(sale.getCar());
        double priceWithoutDisc = sale.getCar()
                .getParts()
                .stream()
                .mapToDouble(p -> p.getPrice().doubleValue() * p.getQuantity())
                .sum();


        return new SaleFullInfoDto(carDto,sale.getCustomer().getName(),
                sale.getDiscount(),priceWithoutDisc,priceWithoutDisc*(1-sale.getDiscount()));
    }
}
