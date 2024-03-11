package bg.softuni.jsonexercise.domain.dtos.user;

import bg.softuni.jsonexercise.domain.dtos.product.wrapper.ProductWrapperDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserSoldProductsWithAgeDto {
    private String firstName;

    private String lastName;

    private Integer age;

    private ProductWrapperDto sellingProducts ;
}
