package softuni.exam.models.dto;

import java.time.LocalDate;

public class VolcanoExtractInfoDto {
    private String  name;
    private String countryName;

    private Integer elevation;

    private LocalDate lastEruption;

    public VolcanoExtractInfoDto(String name, String countryName, Integer elevation, LocalDate lastEruption) {
        this.name = name;
        this.countryName = countryName;
        this.elevation = elevation;
        this.lastEruption = lastEruption;
    }

     public VolcanoExtractInfoDto (){}

    public String getCountryName() {
        return countryName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Integer getElevation() {
        return elevation;
    }

    public void setElevation(Integer elevation) {
        this.elevation = elevation;
    }

    public LocalDate getLastEruption() {
        return lastEruption;
    }

    public void setLastEruption(LocalDate lastEruption) {
        this.lastEruption = lastEruption;
    }

    @Override
    public String toString() {
        return String.format("Volcano: %s\n" +
                "   *Located in: %s\n" +
                "   **Elevation: %d\n" +
                "   ***Last eruption on: %s",name,countryName,elevation,lastEruption);
    }
}
