package bg.softuni.jsonexercisesecondtask.domain.dtos.part;

import bg.softuni.jsonexercisesecondtask.domain.entities.Part;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "part")
@XmlAccessorType(XmlAccessType.FIELD)
public class PartDto {
    @XmlAttribute
    private String name;

    @XmlAttribute
    private BigDecimal price;

    @XmlAttribute
    private Integer quantity;

    public static PartDto fromPart(Part part) {
        return new PartDto(part.getName(),part.getPrice(),
                part.getQuantity());
    }
}
