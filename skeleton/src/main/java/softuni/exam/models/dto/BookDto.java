package softuni.exam.models.dto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class BookDto {
    @Size(min = 3,max = 40)
    private String author;
    private Boolean available;
    @Size(min = 5)
    private String description;
    private String genre;
    @Size(min = 3,max = 40)
    private String title;
    @Positive
    private Double rating;

    public BookDto() {
    }

    public BookDto(String author, Boolean available, String description, String genre, String title, Double rating) {
        this.author = author;
        this.available = available;
        this.description = description;
        this.genre = genre;
        this.title = title;
        this.rating = rating;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
