package bg.softuni.jsonexercise.services.user;

import bg.softuni.jsonexercise.domain.dtos.user.UserSoldProductsDto;
import bg.softuni.jsonexercise.domain.dtos.user.UserSoldProductsWithAgeDto;

import java.io.IOException;
import java.util.List;

public interface UserService {

    List<UserSoldProductsDto> getAllUsersWith1SoldItemAndActiveBuyer() throws IOException;

    List<UserSoldProductsWithAgeDto> getUsersSummary() throws IOException;
}
