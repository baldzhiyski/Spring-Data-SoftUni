package bg.softuni.game_store_console_app.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.util.List;

import static bg.softuni.game_store_console_app.constants.Patterns.EMAIL_PATTERN;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User extends BaseEntity{

    @Column(nullable = false,unique = true)
    @Email(regexp = EMAIL_PATTERN)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "full_name",nullable = false)
    private String fullName;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
    @JoinTable
    private List<Game> games;

    @OneToMany(targetEntity = Order.class, mappedBy = "user", fetch = FetchType.EAGER)
    private List<Order> orders;

    @Column(name = "is_admin")
    private Boolean isAdmin;

}
