package softuni.exam.models.dto.wrapper;

import softuni.exam.models.dto.CompanyDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "companies")
@XmlAccessorType(XmlAccessType.FIELD)
public class CompanyWrapper {

    @XmlElement(name = "company")
    private List<CompanyDto> companyDtoList;

    public CompanyWrapper() {
    }

    public List<CompanyDto> getCompanyDtoList() {
        return companyDtoList;
    }

    public void setCompanyDtoList(List<CompanyDto> companyDtoList) {
        this.companyDtoList = companyDtoList;
    }
}
