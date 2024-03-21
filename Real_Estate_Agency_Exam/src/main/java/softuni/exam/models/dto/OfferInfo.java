package softuni.exam.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OfferInfo {

    private Long id;
    private String agentFirstName;

    private String agentLastName;

    private Double apartmentArea;

    private String apartmentTownName;

    private BigDecimal price;

    @Override
    public String toString() {
        return String.format("Agent %s %s with offer No %d\n" +
                "-Apartment area: %.2f\n" +
                "--Town: %s\n" +
                "---Price: %.2f$",agentFirstName,agentLastName,id,
                apartmentArea,apartmentTownName,price);
    }
}
