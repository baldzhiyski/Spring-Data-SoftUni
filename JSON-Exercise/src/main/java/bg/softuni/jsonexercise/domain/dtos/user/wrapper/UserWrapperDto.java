package bg.softuni.jsonexercise.domain.dtos.user.wrapper;

import bg.softuni.jsonexercise.domain.dtos.user.UserSoldProductsWithAgeDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserWrapperDto {

    @XmlAttribute(name = "count")
    private int usersCount;

    @XmlElement(name = "user")
    private List<UserSoldProductsWithAgeDto> users;

    public UserWrapperDto( List<UserSoldProductsWithAgeDto> users) {
        this.usersCount = users.size();
        this.users = users;
    }

}
