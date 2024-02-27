package org.example.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.Set;

@Entity
@Table(name = "doctors")
public class Doctor extends BaseEntity{

    @OneToMany
    @JoinTable(name = "doctor_visitations")
    private Set<Visitation> visitations;

    @OneToMany
    @JoinTable(name = "doctor_diagnoses")
    private Set<Diagnoses> diagnoses;

    @OneToMany
    @JoinTable(name = "doctor_pills_prescribed")
    private Set<Pill> prescribedPills;

    public Doctor() {
    }

    public Set<Visitation> getVisitations() {
        return visitations;
    }

    public void setVisitations(Set<Visitation> visitations) {
        this.visitations = visitations;
    }

    public Set<Diagnoses> getDiagnoses() {
        return diagnoses;
    }

    public void setDiagnoses(Set<Diagnoses> diagnoses) {
        this.diagnoses = diagnoses;
    }

    public Set<Pill> getPrescribedPills() {
        return prescribedPills;
    }

    public void setPrescribedPills(Set<Pill> prescribedPills) {
        this.prescribedPills = prescribedPills;
    }
}
