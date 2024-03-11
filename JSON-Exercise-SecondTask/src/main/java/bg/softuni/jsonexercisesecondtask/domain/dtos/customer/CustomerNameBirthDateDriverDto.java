package bg.softuni.jsonexercisesecondtask.domain.dtos.customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerNameBirthDateDriverDto {
    private String name;
    private Date birthDate;

    private Boolean isYoungDriver;
}
