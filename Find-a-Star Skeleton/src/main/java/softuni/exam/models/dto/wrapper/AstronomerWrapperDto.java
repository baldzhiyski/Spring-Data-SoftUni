package softuni.exam.models.dto.wrapper;

import softuni.exam.models.dto.AstronomerDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "astronomers")
@XmlAccessorType(XmlAccessType.FIELD)
public class AstronomerWrapperDto {

    @XmlElement(name = "astronomer")
    private List<AstronomerDto> astronomerDtos;

    public AstronomerWrapperDto(List<AstronomerDto> astronomerDtos) {
        this.astronomerDtos = astronomerDtos;
    }

    public AstronomerWrapperDto() {
    }

    public List<AstronomerDto> getAstronomerDtos() {
        return astronomerDtos;
    }

    public void setAstronomerDtos(List<AstronomerDto> astronomerDtos) {
        this.astronomerDtos = astronomerDtos;
    }
}
