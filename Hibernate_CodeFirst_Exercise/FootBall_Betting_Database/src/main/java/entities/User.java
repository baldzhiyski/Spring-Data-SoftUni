package entities;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "username",nullable = false)
    private String userName;

    @Column(nullable = false)
    private String password;

    @Column
    private String email;
    @Column(name = "full_name",nullable = false)
    private String fullName;
    @Column
    private BigDecimal balance;

}
