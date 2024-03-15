package bg.softuni.jsonexercisesecondtask.domain.dtos.sale.wrapper;

import bg.softuni.jsonexercisesecondtask.domain.dtos.sale.SaleFullInfoDto;
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
@XmlRootElement(name = "sales")
@XmlAccessorType(XmlAccessType.FIELD)
public class SaleWrapperDto {

    @XmlElement(name = "sale")
    private List<SaleFullInfoDto> sales;
}
