package bg.softuni.jsonexercise.domain.dtos.product.wrapper;

import bg.softuni.jsonexercise.domain.dtos.product.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "sold-products")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductWrapperDto {
    @XmlAttribute
    private int count;

    @XmlElement(name = "product")
    private Set<ProductDto> sellingProducts;

    public static ProductWrapperDto productsSoldWithCountDto(Set<ProductDto> products) {
        return new ProductWrapperDto(products.size(), products);
    }
}
