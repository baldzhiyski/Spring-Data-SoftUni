package bg.softuni.jsonexercise.domain.dtos.user;

import bg.softuni.jsonexercise.domain.dtos.product.wrapper.ProductWrapperDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserSoldProductsWithAgeDto {

    @XmlAttribute(name = "first_name")
    private String firstName;

    @XmlAttribute(name = "last-name")
    private String lastName;

    @XmlAttribute(name = "age")
    private Integer age;

    @XmlElement(name = "sold-products")
    private ProductWrapperDto sellingProducts ;

}
