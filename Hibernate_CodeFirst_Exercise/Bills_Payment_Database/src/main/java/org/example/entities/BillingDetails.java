package org.example.entities;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class BillingDetails {
    @Id
    @Column(unique = true, length = 30)
    private String number;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;

    public BillingDetails() {
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
