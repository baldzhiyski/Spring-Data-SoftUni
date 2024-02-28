package bg.softuni.springdatademo.exceptions;

public class EntityMissingException extends RuntimeException {
    public EntityMissingException(String message) {
        super(message);
    }
}
