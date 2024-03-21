package softuni.exam.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TownDto {

    @NotBlank
    @Length(min = 2)
    private String townName;

    @Positive
    @NotNull
    private Long population;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TownDto townDto = (TownDto) o;
        return Objects.equals(townName, townDto.townName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(townName);
    }
}
