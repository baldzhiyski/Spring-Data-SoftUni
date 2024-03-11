package bg.softuni.jsonexercise.domain.dtos.user;

import bg.softuni.jsonexercise.domain.dtos.product.ProductWithBuyerNames;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserSoldProductsDto {
    private String firstName;

    private String lastName;

    private List<ProductWithBuyerNames> sellingProducts ;
}
