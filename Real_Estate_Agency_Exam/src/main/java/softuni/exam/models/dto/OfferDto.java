package softuni.exam.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.xml.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "offer")
@XmlAccessorType(XmlAccessType.FIELD)
public class OfferDto {

    @Positive
    @XmlElement
    private BigDecimal price;

    @XmlElement(name = "agent")
    private AgentNameDto agent;

    @XmlElement(name = "apartment")
    private ApartmentIdDto apartment;

    @XmlElement
    @NotNull
    private String publishedOn;
}
