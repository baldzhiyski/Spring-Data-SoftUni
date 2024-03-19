package softuni.exam.models.entity;

import softuni.exam.models.entity.enums.Genre;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Entity
@Table(name = "books")
public class Book extends BaseEntity{

    @Column(nullable = false,unique = true)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false,columnDefinition = "TEXT")
    @Size(min = 5)
    private String description;

    @Column(nullable = false)
    private Boolean available;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Genre genre;

    @Column(nullable = false)
    @Positive
    private Double rating;

    public Book() {
    }

    public Book(String title, String author, String description, Boolean available, Genre genre, Double rating) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.available = available;
        this.genre = genre;
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
