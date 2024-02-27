package org.example.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "patients")
public class Patient extends BaseEntity{

    @Column(name = "first_name",length = 50,nullable = false)
    private String firstName;

    @Column(name = "last_name",length = 50,nullable = false)
    private String lastName;

    @Column()
    private String address;
    @Column()
    private String email;
    @Column(nullable = false)
    private Boolean medicalInsurance;

    @ManyToOne
    @JoinColumn(name = "dooctor_id")
    private Doctor doctor;

    public Patient() {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getMedicalInsurance() {
        return medicalInsurance;
    }

    public void setMedicalInsurance(Boolean medicalInsurance) {
        this.medicalInsurance = medicalInsurance;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {

    }
}
