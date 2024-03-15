package bg.softuni.jsonexercisesecondtask.services.sale;

import bg.softuni.jsonexercisesecondtask.domain.dtos.sale.SaleFullInfoDto;
import bg.softuni.jsonexercisesecondtask.domain.dtos.sale.wrapper.SaleWrapperDto;
import bg.softuni.jsonexercisesecondtask.repositories.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import static bg.softuni.jsonexercisesecondtask.constants.Paths.PATH_SIXTH_EX;
import static bg.softuni.jsonexercisesecondtask.constants.Paths.PATH_SIXTH_EX_XML;
import static bg.softuni.jsonexercisesecondtask.constants.Utils.writeIntoXmlFile;
import static bg.softuni.jsonexercisesecondtask.constants.Utils.writeJsonOnFile;

@Service
public class SaleServiceImpl  implements  SaleService{
    private SaleRepository saleRepository;

    @Autowired
    public SaleServiceImpl(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    @Override
    public List<SaleFullInfoDto> getAllSales() throws IOException, JAXBException {
        List<SaleFullInfoDto> sales = this.saleRepository.findAll()
                .stream()
                .map(SaleFullInfoDto::fromSaleToDto)
                .collect(Collectors.toList());

        SaleWrapperDto wrapperDto = new SaleWrapperDto(sales);

        writeJsonOnFile(sales, Path.of(PATH_SIXTH_EX));
        writeIntoXmlFile(wrapperDto,Path.of(PATH_SIXTH_EX_XML));
        return sales;
    }
}
