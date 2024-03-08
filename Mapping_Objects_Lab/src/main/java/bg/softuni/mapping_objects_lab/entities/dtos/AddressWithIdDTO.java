package bg.softuni.mapping_objects_lab.entities.dtos;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddressWithIdDTO extends AddressDTO{

    @Expose
    private Long id;
    public AddressWithIdDTO(String country, String city, Long id) {
        super(country, city);
        this.id = id;
    }


}
