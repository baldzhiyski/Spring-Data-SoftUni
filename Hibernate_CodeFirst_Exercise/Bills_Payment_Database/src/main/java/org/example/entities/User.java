package org.example.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name",length = 50,nullable = false)
    private String firstName;

    @Column(name = "last_name",length = 50,nullable = false)
    private String lastName;

    @Column(length = 50,nullable = false)
    private String email;

    @Column(length = 50,nullable = false)
    private String password;

    @OneToMany(mappedBy = "owner")
    private Set<BillingDetails> details;

    public User() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<BillingDetails> getDetails() {
        return details;
    }

    public void setDetails(Set<BillingDetails> details) {
        this.details = details;
    }
}