package bg.softuni.jsonexercisesecondtask.domain.dtos.supplier.wrapper;

import bg.softuni.jsonexercisesecondtask.domain.dtos.supplier.SupplierWithCountDto;
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
@XmlRootElement(name = "suppliers")
@XmlAccessorType(XmlAccessType.FIELD)
public class SupplierWithCountWrapper {
    @XmlElement(name = "supplier")
    private List<SupplierWithCountDto> suppliers;

}
