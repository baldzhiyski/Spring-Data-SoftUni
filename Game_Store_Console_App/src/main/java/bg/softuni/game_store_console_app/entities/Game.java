package bg.softuni.game_store_console_app.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "games")
public class Game extends BaseEntity{

    @Column(nullable = false)
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
}
