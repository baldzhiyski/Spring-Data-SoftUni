package org.example.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Student extends Person{
    @Column(name = "avg_grade")
    private Double averageGrade;

    @Column
    private long attendance;

    @ManyToMany
    @JoinTable(name = "students_courses",
    joinColumns = @JoinColumn(name = "student_id",referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "course_id",referencedColumnName = "id"))
    private Set<Course> courses;

    public Student() {
    }

    public Double getAverageGrade() {
        return averageGrade;
    }

    public void setAverageGrade(Double averageGrade) {
        this.averageGrade = averageGrade;
    }

    public long getAttendance() {
        return attendance;
    }

    public void setAttendance(long attendance) {
        this.attendance = attendance;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }
}
