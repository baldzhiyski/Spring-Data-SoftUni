package bg.softuni.jsonexercise.services.product;

import bg.softuni.jsonexercise.domain.dtos.product.ProductInRangeDto;
import bg.softuni.jsonexercise.domain.dtos.product.wrapper.ProductsInRangeWrapperDto;
import bg.softuni.jsonexercise.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import static bg.softuni.jsonexercise.constants.Paths.PATH_TO_PRODUCTS_IN_RANGE;
import static bg.softuni.jsonexercise.constants.Paths.PATH_TO_PRODUCTS_IN_RANGE_XML;
import static bg.softuni.jsonexercise.constants.Utils.writeIntoXmlFile;
import static bg.softuni.jsonexercise.constants.Utils.writeJsonOnFile;

@Service
public class ProductServiceImpl implements ProductService{
    private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository, ModelMapper mapper) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductInRangeDto> getAllInSpecificRange(BigDecimal low, BigDecimal high) throws IOException, JAXBException {
        List<ProductInRangeDto> products = this.productRepository
                .findAllByPriceBetweenAndBuyerIsNullOrderByPrice(low, high)
                .stream()
                .map(ProductInRangeDto::getFromProduct)
                .collect(Collectors.toList());

        ProductsInRangeWrapperDto productsInRangeWrapperDto = new ProductsInRangeWrapperDto(products);

        writeJsonOnFile(products, Path.of(PATH_TO_PRODUCTS_IN_RANGE));
        writeIntoXmlFile(productsInRangeWrapperDto,Path.of(PATH_TO_PRODUCTS_IN_RANGE_XML));

        return products;
    }
}
