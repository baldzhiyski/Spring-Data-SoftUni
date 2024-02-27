package org.example.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import java.math.BigInteger;

@Entity
public class Teacher extends Person {
    @Column(name = "email_address",length = 50)
    private String emailAddress;

    @Column(name = "salary_per_hour")
    private BigInteger salaryPerHour;

    public Teacher() {
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public BigInteger getSalaryPerHour() {
        return salaryPerHour;
    }

    public void setSalaryPerHour(BigInteger salaryPerHour) {
        this.salaryPerHour = salaryPerHour;
    }
}
