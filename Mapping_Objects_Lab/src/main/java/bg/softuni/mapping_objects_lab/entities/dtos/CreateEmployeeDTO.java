package bg.softuni.mapping_objects_lab.entities.dtos;


import com.google.gson.annotations.Expose;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@NoArgsConstructor
public class CreateEmployeeDTO {

    @Expose
    private String firstName;
    private String lastName;

    @Expose
    private BigDecimal salary;
    private LocalDate birthday;
    @Expose
    private AddressDTO address;

    public CreateEmployeeDTO(String firstName, String lastName, BigDecimal salary, LocalDate birthday, AddressDTO address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.birthday = birthday;
        this.address = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public AddressDTO getAddress() {
        return address;
    }
}