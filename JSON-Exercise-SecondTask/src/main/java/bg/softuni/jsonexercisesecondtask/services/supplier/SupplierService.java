package bg.softuni.jsonexercisesecondtask.services.supplier;

import bg.softuni.jsonexercisesecondtask.domain.dtos.supplier.SupplierWithCountDto;

import java.io.IOException;
import java.util.List;

public interface SupplierService {
    List<SupplierWithCountDto> getAllSuppliersNotImportingAbroad() throws IOException;
}
