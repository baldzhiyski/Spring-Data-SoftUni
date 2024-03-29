package bg.softuni.jsonexercise.services.seed;

import bg.softuni.jsonexercise.domain.dtos.category.CategoryDto;
import bg.softuni.jsonexercise.domain.dtos.category.wrapper.CategoryWrapperXml;
import bg.softuni.jsonexercise.domain.dtos.product.ProductDto;
import bg.softuni.jsonexercise.domain.dtos.product.wrapper.ProductNamePriceWrapperXml;
import bg.softuni.jsonexercise.domain.dtos.user.UserDto;
import bg.softuni.jsonexercise.domain.dtos.user.wrapper.UserDtoWrapper;
import bg.softuni.jsonexercise.domain.entities.Category;
import bg.softuni.jsonexercise.domain.entities.Product;
import bg.softuni.jsonexercise.domain.entities.User;
import bg.softuni.jsonexercise.repositories.CategoryRepository;
import bg.softuni.jsonexercise.repositories.ProductRepository;
import bg.softuni.jsonexercise.repositories.UserRepository;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static bg.softuni.jsonexercise.constants.Paths.*;

@Service
public class SeedServiceImpl implements SeedService {

    private Gson gson;
    private ModelMapper mapper;

    private UserRepository userRepository;

    private CategoryRepository categoryRepository;

    private ProductRepository productRepository;

    public SeedServiceImpl(Gson gson, ModelMapper mapper, UserRepository userRepository, CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.gson = gson;
        this.mapper = mapper;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void seedUsers(String type) throws FileNotFoundException, JAXBException {
        if(this.userRepository.count() == 0) {
            List<User> users = type.equalsIgnoreCase("Json")?getUsersFromJson():getUserFromXml();

            this.userRepository.saveAll(users);
        }
    }

    private List<User> getUserFromXml() throws FileNotFoundException, JAXBException {
        FileReader reader = new FileReader(PATH_TO_USERS_XML);
        Unmarshaller unmarshaller = JAXBContext.newInstance(UserDtoWrapper.class).createUnmarshaller();
        UserDtoWrapper unmarshalled = (UserDtoWrapper) unmarshaller.unmarshal(reader);

       return unmarshalled.getUsers()
                .stream()
                .map(userDto -> mapper.map(userDto,User.class))
               .collect(Collectors.toList());
    }

    private List<User> getUsersFromJson() throws FileNotFoundException {
        FileReader fileReader = new FileReader(PATH_TO_USERS);
        List<User> users = Arrays.stream(gson.fromJson(fileReader, UserDto[].class))
                .map(userDto -> mapper.map(userDto, User.class))
                .collect(Collectors.toList());
        return users;
    }

    @Override
    @Transactional
    public void seedProducts(String type) throws FileNotFoundException, JAXBException {
        if(this.productRepository.count()==0) {

            List<Product> products =type.equalsIgnoreCase("Json")? getProductsFromJson():
            getProductsFromXml();


            this.productRepository.saveAllAndFlush(products);
        }
    }

    private List<Product> getProductsFromXml() throws FileNotFoundException, JAXBException {
        FileReader reader = new FileReader(PATH_TO_PRODUCTS_XML);
        JAXBContext jaxbContext = JAXBContext.newInstance(ProductNamePriceWrapperXml.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        ProductNamePriceWrapperXml productWrapper = (ProductNamePriceWrapperXml) unmarshaller.unmarshal(reader);

       return productWrapper.getProducts()
                .stream()
                .map(productDto -> mapper.map(productDto, Product.class))
                .map(this::setRandomSeller)
                .map(this::setRandomBuyer)
                .map(this::setRandomCategories)
                .collect(Collectors.toList());
    }

    private List<Product> getProductsFromJson() throws FileNotFoundException {
        FileReader fileReader = new FileReader(PATH_TO_PRODUCTS);
        List<Product> products = Arrays.stream(gson.fromJson(fileReader, ProductDto[].class))
                .map(productDto -> mapper.map(productDto, Product.class))
                .map(this::setRandomSeller)
                .map(this::setRandomBuyer)
                .map(this::setRandomCategories)
                .collect(Collectors.toList());
        return products;
    }

    private Product setRandomSeller(Product product) {
        User seller = this.userRepository.getRandomEntity()
                .orElseThrow(NoSuchElementException::new);

        product.setSeller(seller);

        return  product;
    }

    private Product setRandomBuyer(Product product) {
        if(product.getPrice().compareTo(BigDecimal.valueOf(700)) > 0){
            Optional<User> buyer = this.userRepository.getRandomEntity();

            product.setBuyer(buyer.get());

        }
        return product;
    }


    private Product setRandomCategories(Product product) {
        Random random = new Random();

        long countOfCategories = Math.max(random.nextLong(this.categoryRepository.count()) - 2, 0);

        List<Category> allCategories = this.categoryRepository.findAll();

        Set<Category> categories = new HashSet<>();

        for (int i = 0; i < countOfCategories; i++) {
            int randomIndex = random.nextInt(allCategories.size());
            categories.add(allCategories.get(randomIndex));
        }

        product.setCategories(categories);

        return product;

    }

    @Override
    public void seedCategories(String type) throws FileNotFoundException, JAXBException {
        if(this.categoryRepository.count()==0){
            List<Category> categories = type.equalsIgnoreCase("Json") ? getCategoriesFromJson() :
                    getCategoriesFromXml();

            this.categoryRepository.saveAll(categories);
        }
    }

    private List<Category> getCategoriesFromXml() throws FileNotFoundException, JAXBException {
        FileReader fileReader = new FileReader(PATH_TO_CATEGORIES_XML);

        Unmarshaller unmarshaller = JAXBContext.newInstance(CategoryWrapperXml.class).createUnmarshaller();
        CategoryWrapperXml categoryWrapperXml = (CategoryWrapperXml) unmarshaller.unmarshal(fileReader);

        return categoryWrapperXml.getCategories()
                .stream()
                .map(categoryDto -> mapper.map(categoryDto,Category.class))
                .collect(Collectors.toList());
    }

    private List<Category> getCategoriesFromJson() throws FileNotFoundException {
        FileReader fileReader = new FileReader(PATH_TO_CATEGORIES);
        List<Category> categories = Arrays.stream(gson.fromJson(fileReader, CategoryDto[].class))
                .map(categoryDto -> mapper.map(categoryDto, Category.class))
                .collect(Collectors.toList());
        return categories;
    }
}
