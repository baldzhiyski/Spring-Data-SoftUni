package bg.softuni.jsonexercise.domain.dtos.product.wrapper;

import bg.softuni.jsonexercise.domain.dtos.product.ProductInRangeDto;
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
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "products")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductsInRangeWrapperDto {

    @XmlElement
    private List<ProductInRangeDto> products;
}
