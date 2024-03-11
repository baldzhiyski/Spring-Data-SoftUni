package bg.softuni.jsonexercise.services.product;

import bg.softuni.jsonexercise.domain.dtos.product.ProductInRangeDto;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    List<ProductInRangeDto> getAllInSpecificRange(BigDecimal low,BigDecimal high) throws IOException;

}
