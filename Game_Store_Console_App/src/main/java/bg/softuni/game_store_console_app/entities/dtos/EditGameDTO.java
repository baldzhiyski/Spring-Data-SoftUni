package bg.softuni.game_store_console_app.entities.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class EditGameDTO {
    private String title;

    private BigDecimal price;

    private float size;

    private String trailerURL;

    private String imageURL;

    private String description;

    private LocalDate releaseDate;

    public void updateFields(Map<String, String> updatedValues) {
        for (String key : updatedValues.keySet()) {
            if (Objects.equals(key, "title")) {
                setTitle(updatedValues.get(key));
            } else if (Objects.equals(key, "price")) {
                setPrice(new BigDecimal(updatedValues.get(key)));
            } else if (Objects.equals(key, "size")) {
                setSize(Float.parseFloat(updatedValues.get(key)));
            } else if (Objects.equals(key, "trailerURL")) {
                setTrailerURL(updatedValues.get(key));
            } else if (Objects.equals(key, "imageURL")) {
                setImageURL(updatedValues.get(key));
            } else if (Objects.equals(key, "description")) {
                setDescription(updatedValues.get(key));
            } else if (Objects.equals(key, "releaseDate")) {
                setReleaseDate(LocalDate.parse(updatedValues.get(key),DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            }
        }
    }
}
