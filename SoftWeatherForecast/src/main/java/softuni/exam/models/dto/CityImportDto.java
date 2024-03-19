package softuni.exam.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CityImportDto {

    @Size(min = 2,max = 60)
    private String cityName;

    @Length(min = 2)
    private String description;

    @Min(value =500)
    private Long population;

    private Long country;
}
