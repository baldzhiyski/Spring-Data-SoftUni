package bg.softuni.jsonexercise.domain.dtos.user.wrapper;

import bg.softuni.jsonexercise.domain.dtos.user.UserSoldProductsWithAgeDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserWrapperDto {

    private int usersCount;

    private List<UserSoldProductsWithAgeDto> users;

    public UserWrapperDto( List<UserSoldProductsWithAgeDto> users) {
        this.usersCount = users.size();
        this.users = users;
    }

}
