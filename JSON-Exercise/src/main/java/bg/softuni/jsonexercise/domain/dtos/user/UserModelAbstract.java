package bg.softuni.jsonexercise.domain.dtos.user;

import bg.softuni.jsonexercise.domain.dtos.product.ProductDto;
import bg.softuni.jsonexercise.domain.dtos.product.wrapper.ProductWrapperDto;
import bg.softuni.jsonexercise.domain.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class UserModelAbstract {
    private String firstName;


    private String lastName;

    private Integer age;

    private Set<Product> sellingProducts;

    private Set<Product> boughtProducts;

    public UserSoldProductsWithAgeDto toUserWithProductsDto() {
        return new UserSoldProductsWithAgeDto(firstName,lastName,age,toProductsSoldWithCountDto());
    }

    private ProductWrapperDto toProductsSoldWithCountDto() {
        return ProductWrapperDto.productsSoldWithCountDto(
                sellingProducts.stream()
                        .map(this::toProductBasicInfo)
                        .collect(Collectors.toSet())
        );
    }

    private ProductDto toProductBasicInfo(Product product) {
        return new ProductDto(product.getName(),product.getPrice());
    }
}
