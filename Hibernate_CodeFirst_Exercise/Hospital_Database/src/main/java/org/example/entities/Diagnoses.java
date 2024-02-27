package org.example.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "diagnoses")
public class Diagnoses  extends  BaseEntity{

    @Column(length = 100,nullable = false)
    private String name;

    @Column
    private String comment;

    public Diagnoses() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
