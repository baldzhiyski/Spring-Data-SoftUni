package bg.softuni.game_store_console_app.entities.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

import static bg.softuni.game_store_console_app.constants.ErrorMessages.*;

@Setter
@Getter
public class AddGameDTO {
    private String title;

    private BigDecimal price;

    private float size;

    private String trailerURL;

    private String imageURL;

    private String description;

    private LocalDate releaseDate;

    public AddGameDTO(String title, BigDecimal price, float size, String trailerURL, String imageURL, String description, LocalDate releaseDate) {
        this.title = title;
        this.price = price;
        this.size = size;
        this.trailerURL = trailerURL;
        this.imageURL = imageURL;
        this.description = description;
        this.releaseDate = releaseDate;
        validate();
    }

    private void validate() {
        if(!Character.isUpperCase(this.title.charAt(0))
        || (this.title.length()<=3 || this.title.length()>=100)){
            throw new IllegalArgumentException(INVALID_GAME_TITLE);
        }

        if(this.price.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException(INVALID_PRICE);
        }
        if(this.trailerURL.length() != 11){
            throw new IllegalArgumentException(INVALID_TRAILER_URL_LENGTH);
        }
        if (!this.imageURL.startsWith("http://") && !this.imageURL.startsWith("https://")) {
            throw new IllegalArgumentException(INVALID_IMAGE_NAME);
        }
        if(this.description.length()<20){
            throw new IllegalArgumentException(TOO_SMALL_DESCRIPTION);
        }
    }
}
