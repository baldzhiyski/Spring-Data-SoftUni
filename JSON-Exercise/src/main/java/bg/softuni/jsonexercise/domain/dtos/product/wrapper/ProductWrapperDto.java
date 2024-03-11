package bg.softuni.jsonexercise.domain.dtos.product.wrapper;

import bg.softuni.jsonexercise.domain.dtos.product.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductWrapperDto {
    private int count;

    private Set<ProductDto> sellingProducts;

    public static ProductWrapperDto productsSoldWithCountDto(Set<ProductDto> products) {
        return new ProductWrapperDto(products.size(), products);
    }
}
