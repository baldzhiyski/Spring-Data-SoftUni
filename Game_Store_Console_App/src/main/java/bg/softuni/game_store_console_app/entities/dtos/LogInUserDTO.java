package bg.softuni.game_store_console_app.entities.dtos;

import lombok.Getter;
import lombok.Setter;

import static bg.softuni.game_store_console_app.constants.ErrorMessages.INVALID_PASSWORD;

@Getter
@Setter
public class LogInUserDTO {
    private String email;

    private String password;

    public LogInUserDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }
    public void validate(String realPassword) {
        if (!this.password.equals(realPassword)) {
            throw new IllegalArgumentException(INVALID_PASSWORD);
        }
    }
}
