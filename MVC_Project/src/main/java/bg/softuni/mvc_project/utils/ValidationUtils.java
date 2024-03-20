package bg.softuni.mvc_project.utils;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.springframework.stereotype.Component;



@Component
public class ValidationUtils {
    private final Validator validator;

    public ValidationUtils() {
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }
    public <E> boolean isValid(E entity) {
        return this.validator.validate(entity).isEmpty();
    }
}
