package bg.softuni.jsonexercisesecondtask.domain.dtos.supplier;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SupplierNameImporterDto {
    private String name;

    private Boolean isImporter;

}
