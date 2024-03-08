package bg.softuni.mapping_objects_lab.entities.dtos;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddressDTO {
    @Expose
    private String country;

    @Expose
    private String city;

    public AddressDTO(String country, String city) {
        this.country = country;
        this.city = city;
    }

    public AddressDTO() {
    }

}
