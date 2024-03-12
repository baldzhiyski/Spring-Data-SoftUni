package bg.softuni.jsonexercisesecondtask.domain.dtos.supplier;

import bg.softuni.jsonexercisesecondtask.domain.entities.Supplier;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SupplierWithCountDto {

    private Long id;

    private String name;

    private int partsCount;


    public static SupplierWithCountDto fromSupplier(Supplier supplier){
        return new SupplierWithCountDto(supplier.getId(),supplier.getName(),
                supplier.getParts().size());
    }
}
