package bg.softuni.game_store_console_app.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "shopping_cards")
public class ShoppingCard extends BaseEntity{
    @OneToOne(mappedBy = "shoppingCard",cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Game> games;

}
