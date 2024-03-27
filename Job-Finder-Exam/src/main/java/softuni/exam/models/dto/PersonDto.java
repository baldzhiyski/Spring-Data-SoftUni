package softuni.exam.models.dto;

import org.hibernate.validator.constraints.Length;
import softuni.exam.models.entity.StatusType;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class PersonDto {

    @NotNull
    @Email
    private String email;

    @NotNull
    @Length(min = 2,max = 30)
    private String firstName;

    @NotNull
    @Length(min = 2,max = 30)
    private String lastName;

    @Length(min = 2,max = 13)
    private String phone;
    private StatusType statusType;
    private Long country;

    public PersonDto() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public StatusType getStatusType() {
        return statusType;
    }

    public void setStatusType(StatusType statusType) {
        this.statusType = statusType;
    }

    public Long getCountry() {
        return country;
    }

    public void setCountry(Long country) {
        this.country = country;
    }

}
