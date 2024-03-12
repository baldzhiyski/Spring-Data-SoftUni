package bg.softuni.jsonexercisesecondtask.domain.dtos.sale;

import bg.softuni.jsonexercisesecondtask.domain.dtos.car.CarDto;
import bg.softuni.jsonexercisesecondtask.domain.entities.Sale;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaleFullInfoDto {
    private CarDto car;

    private String customerName;

    private Double discount;
    private Double price;

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
