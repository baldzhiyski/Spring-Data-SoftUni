package softuni.exam.models.dto.wrapper;

import softuni.exam.models.dto.VolcanologistDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "volcanologists")
@XmlAccessorType(XmlAccessType.FIELD)
public class VolcanologistWrapper {

    @XmlElement(name = "volcanologist")
    private List<VolcanologistDto> volcanologistDtos;

    public VolcanologistWrapper() {
    }

    public List<VolcanologistDto> getVolcanologistDtos() {
        return volcanologistDtos;
    }

    public void setVolcanologistDtos(List<VolcanologistDto> volcanologistDtos) {
        this.volcanologistDtos = volcanologistDtos;
    }
}
