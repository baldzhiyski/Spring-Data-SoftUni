package bg.softuni.jsonexercisesecondtask.domain.dtos.supplier.wrapper;

import bg.softuni.jsonexercisesecondtask.domain.dtos.supplier.SupplierNameImporterDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "suppliers")
@XmlAccessorType(XmlAccessType.FIELD)
public class SupplierNameWrapperDto {

    @XmlElement(name = "supplier")
    private List<SupplierNameImporterDto> suppliers;
}
