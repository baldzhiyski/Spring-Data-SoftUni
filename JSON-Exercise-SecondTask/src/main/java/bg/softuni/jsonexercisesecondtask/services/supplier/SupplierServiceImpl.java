package bg.softuni.jsonexercisesecondtask.services.supplier;

import bg.softuni.jsonexercisesecondtask.constants.Paths;
import bg.softuni.jsonexercisesecondtask.constants.Utils;
import bg.softuni.jsonexercisesecondtask.domain.dtos.supplier.SupplierWithCountDto;
import bg.softuni.jsonexercisesecondtask.repositories.SupplierRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierServiceImpl implements SupplierService{
    private SupplierRepository supplierRepository;

    public SupplierServiceImpl(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }


    @Override
    public List<SupplierWithCountDto> getAllSuppliersNotImportingAbroad() throws IOException {
        List<SupplierWithCountDto> suppliers = this.supplierRepository.findAllByIsImporterIsFalse()
                .stream()
                .map(SupplierWithCountDto::fromSupplier)
                .collect(Collectors.toList());

        Utils.writeJsonOnFile(suppliers, Path.of(Paths.PATH_THIRD_EX));

        return suppliers;
    }
}
