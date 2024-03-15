package bg.softuni.jsonexercisesecondtask.services.supplier;

import bg.softuni.jsonexercisesecondtask.constants.Paths;
import bg.softuni.jsonexercisesecondtask.constants.Utils;
import bg.softuni.jsonexercisesecondtask.domain.dtos.supplier.SupplierWithCountDto;
import bg.softuni.jsonexercisesecondtask.domain.dtos.supplier.wrapper.SupplierWithCountWrapper;
import bg.softuni.jsonexercisesecondtask.repositories.SupplierRepository;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
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
    public List<SupplierWithCountDto> getAllSuppliersNotImportingAbroad() throws IOException, JAXBException {
        List<SupplierWithCountDto> suppliers = this.supplierRepository.findAllByIsImporterIsFalse()
                .stream()
                .map(SupplierWithCountDto::fromSupplier)
                .collect(Collectors.toList());

        SupplierWithCountWrapper wrapper = new SupplierWithCountWrapper(suppliers);

        Utils.writeJsonOnFile(suppliers, Path.of(Paths.PATH_THIRD_EX));
        Utils.writeIntoXmlFile(wrapper, Path.of(Paths.PATH_THIRD_EX_XML));

        return suppliers;
    }
}
