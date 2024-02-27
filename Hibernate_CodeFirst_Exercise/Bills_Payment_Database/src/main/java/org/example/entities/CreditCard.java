package org.example.entities;

import jakarta.persistence.*;
import org.example.entities.enums.CreditCardType;

@Entity
@Table(name = "credit_cards")
public class CreditCard  extends BillingDetails{

    @Enumerated(EnumType.STRING)
    private CreditCardType type;

    @Column(name = "exp_month",nullable = false)
    private String expirationMonth;


    @Column(name = "exp_year",nullable = false,length = 4)
    private String expirationYear;

    public CreditCard() {
    }

    public CreditCardType getType() {
        return type;
    }

    public void setType(CreditCardType type) {
        this.type = type;
    }

    public String getExpirationMonth() {
        return expirationMonth;
    }

    public void setExpirationMonth(String expirationMonth) {
        this.expirationMonth = expirationMonth;
    }

    public String getExpirationYear() {
        return expirationYear;
    }

    public void setExpirationYear(String expirationYear) {
        this.expirationYear = expirationYear;
    }
}
