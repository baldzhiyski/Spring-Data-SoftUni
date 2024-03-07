package bg.softuni.game_store_console_app.entities.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.regex.Pattern;

import static bg.softuni.game_store_console_app.constants.ErrorMessages.*;
import static bg.softuni.game_store_console_app.constants.Patterns.EMAIL_PATTERN;
import static bg.softuni.game_store_console_app.constants.Patterns.PASSWORD_PATTERN;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class RegisterUserDTO {
    private String email;

    private String password;

    private String confirmPassword;

    private String fullName;

    public RegisterUserDTO(String email, String password, String confirmPassword, String fullName) {
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.fullName = fullName;
        validate();
    }

    private void validate() {
        if(!Pattern.matches(EMAIL_PATTERN,this.email)){
            throw new IllegalArgumentException(INVALID_EMAIL);
        }

        if(!Pattern.matches(PASSWORD_PATTERN,this.password)){
            throw new IllegalArgumentException(INVALID_PASSWORD);
        }
        if(!this.confirmPassword.equals(this.password)){
            throw new IllegalArgumentException(NOT_MATCHING_PASSWORDS);
        }

    }
}
