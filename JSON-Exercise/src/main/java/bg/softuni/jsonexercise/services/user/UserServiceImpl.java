package bg.softuni.jsonexercise.services.user;

import bg.softuni.jsonexercise.constants.Paths;
import bg.softuni.jsonexercise.constants.Utils;
import bg.softuni.jsonexercise.domain.dtos.product.ProductDto;
import bg.softuni.jsonexercise.domain.dtos.product.wrapper.ProductWrapperDto;
import bg.softuni.jsonexercise.domain.dtos.user.UserSoldProductsDto;
import bg.softuni.jsonexercise.domain.dtos.user.UserSoldProductsWithAgeDto;
import bg.softuni.jsonexercise.domain.dtos.user.wrapper.UserWrapperDto;
import bg.softuni.jsonexercise.domain.entities.Product;
import bg.softuni.jsonexercise.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl  implements UserService{
    private UserRepository userRepository;
    private ModelMapper mapper;

    public UserServiceImpl(UserRepository userRepository, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public List<UserSoldProductsDto> getAllUsersWith1SoldItemAndActiveBuyer() throws IOException {
        List<UserSoldProductsDto> users = this.userRepository.findAllBySellingProductsBuyerIsNotNullOrderBySellingProductsBuyerLastName()
                .stream()
                .map(user -> mapper.map(user, UserSoldProductsDto.class))
                .collect(Collectors.toList());

        Utils.writeJsonOnFile(users, Path.of(Paths.PATH_TO_SUCCESSFULLY_SOLD_PRODUCTS));

        return  users;
    }

    @Override
    public List<UserSoldProductsWithAgeDto> getUsersSummary() throws IOException {
        List<UserSoldProductsWithAgeDto> users = this.userRepository
                .findAllBySellingProductsBuyerIsNotNullOrderBySellingProductsBuyerLastName()
                .stream()
                .map(user -> {
                    UserSoldProductsWithAgeDto userDto = mapper.map(user, UserSoldProductsWithAgeDto.class);
                    Set<ProductDto> productDtoList = mapProductsToDto(user.getSellingProducts());
                    ProductWrapperDto productWrapperDto = ProductWrapperDto.productsSoldWithCountDto(productDtoList);
                    userDto.setSellingProducts(productWrapperDto);
                    return userDto;
                })
                .collect(Collectors.toList());

        UserWrapperDto wrapperDto = new UserWrapperDto(users);

        Utils.writeIntoJsonFile(wrapperDto, Path.of(Paths.PATH_TO_USER_AND_PRODUCTS));

        return users;
    }
    private Set<ProductDto> mapProductsToDto(Set<Product> products) {
        return products.stream()
                .map(product -> new ProductDto(product.getName(), product.getPrice()))
                .collect(Collectors.toSet());
    }
}
