package bg.softuni.game_store_console_app.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "shopping_cards")
public class ShoppingCard extends BaseEntity{
    @OneToOne(mappedBy = "shoppingCard",cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Game> games;

    public ShoppingCard(){
        this.games = new HashSet<>();
    }

}
