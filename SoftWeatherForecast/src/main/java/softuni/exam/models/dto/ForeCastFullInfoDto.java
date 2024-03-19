package softuni.exam.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ForeCastFullInfoDto {

    @Size(min = 2,max = 60)
    private String cityName;

    @DecimalMin(value = "-20", message = "Value must be greater than or equal to -20")
    @DecimalMax(value = "60", message = "Value must be less than or equal to 60")
    private Double maxTemperature;

    @DecimalMin(value = "-50")
    @DecimalMax(value = "40")
    private Double minTemperature;

    @NotNull
    private LocalTime sunrise;

    @NotNull
    private LocalTime sunset;

    @Override
    public String toString() {
        return String.format("City: %s\n"+
                "-min temperature : %.2f\n" +
                "-max temperature : %.2f\n" +
                "---sunrise : %s" +
                "----sunset : %s",this.cityName,
                this.maxTemperature,
                this.minTemperature,
                this.sunrise,this.sunset);
    }
}
