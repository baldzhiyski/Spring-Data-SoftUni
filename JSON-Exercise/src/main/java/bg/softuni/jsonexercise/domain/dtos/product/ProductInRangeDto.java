package bg.softuni.jsonexercise.domain.dtos.product;

import bg.softuni.jsonexercise.domain.entities.Product;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
public class ProductInRangeDto {
    private String name;

    private BigDecimal price;

    @SerializedName(value = "seller")
    private String sellerFullName;

    public static ProductInRangeDto getFromProduct(Product product) {
        String fullName = product.getSeller().getFirstName() + " " + product.getSeller().getLastName();

        return new ProductInRangeDto(product.getName(), product.getPrice(), fullName);
    }
}
