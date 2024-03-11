package bg.softuni.jsonexercise.services.user;

import bg.softuni.jsonexercise.domain.dtos.user.UserSoldProductsDto;

import java.io.IOException;
import java.util.List;

public interface UserService {

    List<UserSoldProductsDto> getAllUsersWith1SoldItemAndActiveBuyer() throws IOException;
}
