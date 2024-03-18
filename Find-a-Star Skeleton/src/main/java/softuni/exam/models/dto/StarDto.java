package softuni.exam.models.dto;


import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class StarDto {
    @Size(min = 2,max = 30)
    private String name;

    @Positive
    private Double lightYears;

    @Size(min = 6)
    private String description;

    private String starType;

    private Long constellation;

    public StarDto(String name, Double lightYears, String description, String starType, Long constellation) {
        this.name = name;
        this.lightYears = lightYears;
        this.description = description;
        this.starType = starType;
        this.constellation = constellation;
    }

    public StarDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLightYears() {
        return lightYears;
    }

    public void setLightYears(Double lightYears) {
        this.lightYears = lightYears;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStarType() {
        return starType;
    }

    public void setStarType(String starType) {
        this.starType = starType;
    }

    public Long getConstellation() {
        return constellation;
    }

    public void setConstellation(Long constellation) {
        this.constellation = constellation;
    }
}
