package bg.softuni.game_store_console_app.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "games")
public class Game extends BaseEntity{

    @Column(nullable = false,unique = true)
    private String title;

    @Column
    private String trailerURL;

    @Column
    private String imageURL;

    @Column
    private float size;

    @Column(nullable = false)
    private BigDecimal price;

    @Column
    private String description;

    @Column(name = "realease_date")
    private LocalDate releaseDate;

    @Override
    public String toString() {
        return String.format("%s %.2f",this.title,this.price);
    }

    public String detailedDescription(){
        StringBuilder builder = new StringBuilder();

        builder.append("Title: ").append(title).append(System.lineSeparator());
        builder.append("Price: ").append(price).append(System.lineSeparator());
        builder.append("Description ").append(description).append(System.lineSeparator());
        builder.append("Release date: ").append(releaseDate).append(System.lineSeparator());

        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return Objects.equals(title, game.title) && Objects.equals(releaseDate, game.releaseDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, releaseDate);
    }
}
