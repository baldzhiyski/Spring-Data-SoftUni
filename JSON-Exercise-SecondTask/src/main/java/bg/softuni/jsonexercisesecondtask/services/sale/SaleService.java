package bg.softuni.jsonexercisesecondtask.services.sale;

import bg.softuni.jsonexercisesecondtask.domain.dtos.sale.SaleFullInfoDto;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

public interface SaleService {
    List<SaleFullInfoDto> getAllSales() throws IOException, JAXBException;
}
