package softuni.exam.models.dto;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;

@XmlRootElement(name = "company")
@XmlAccessorType(XmlAccessType.FIELD)
public class CompanyDto {
    @Size(min = 2,max = 40)
    @XmlElement(name = "companyName")
    @NotNull
    private String name;

    @XmlElement
    private String dateEstablished;

    @Size(min = 2,max = 30)
    @XmlElement
    private String website;

    @XmlElement
    private Long countryId;

    public CompanyDto() {
    }

    public CompanyDto(String name, String dateEstablished, String website, Long countryId) {
        this.name = name;
        this.dateEstablished = dateEstablished;
        this.website = website;
        this.countryId = countryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateEstablished() {
        return dateEstablished;
    }

    public void setDateEstablished(String dateEstablished) {
        this.dateEstablished = dateEstablished;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }
}
