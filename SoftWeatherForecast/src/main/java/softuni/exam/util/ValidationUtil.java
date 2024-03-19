package softuni.exam.util;

import org.springframework.stereotype.Component;
import javax.validation.Validation;
import javax.validation.Validator;
@Component
public class ValidationUtil {
    private final Validator validator;

    public ValidationUtil() {
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }
    public <E> boolean isValid(E entity) {
        return this.validator.validate(entity).isEmpty();
    }
}
