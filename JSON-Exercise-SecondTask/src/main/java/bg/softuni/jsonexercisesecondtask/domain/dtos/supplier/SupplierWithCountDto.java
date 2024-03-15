package bg.softuni.jsonexercisesecondtask.domain.dtos.supplier;

import bg.softuni.jsonexercisesecondtask.domain.entities.Supplier;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "supplier")
@XmlAccessorType(XmlAccessType.FIELD)
public class SupplierWithCountDto {

    @XmlAttribute
    private Long id;
    @XmlAttribute
    private String name;
    @XmlAttribute(name = "part-count")
    private int partsCount;


    public static SupplierWithCountDto fromSupplier(Supplier supplier){
        return new SupplierWithCountDto(supplier.getId(),supplier.getName(),
                supplier.getParts().size());
    }
}
