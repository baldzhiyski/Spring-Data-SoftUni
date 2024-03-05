package bg.softuni.bookshopsystemsecond.domain.entities;


import bg.softuni.bookshopsystemsecond.domain.entities.enums.AgeRestriction;
import bg.softuni.bookshopsystemsecond.domain.entities.enums.EditionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "books")
public class Book extends BaseEntity {

    @Column(nullable = false,length = 50)
    private String title;

    @Column(length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    private EditionType editionType;

    @Column
    private BigDecimal price;


    @Column(nullable = false)
    private Integer copies;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Enumerated(EnumType.STRING)
    private AgeRestriction ageRestriction;

    @ManyToOne
    private Author author;

    @ManyToMany
    @JoinTable(name = "books_categories",
    joinColumns = @JoinColumn(name = "book_id",referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "category_id",referencedColumnName = "id"))
    private Set<Category> categories;

    public String getBookTitleReleaseDateCopiesFormat() {
        return this.title + " " + this.releaseDate + " " + this.copies;
    }

    public String getBookTitleAndPriceFormat() {
        return this.title + " - $" + this.price;
    }

    public String getBookTitleEditionTypeAndPrice() {
        return this.title + " " + this.editionType.name() + " " + this.price;
    }
    public static class Builder {
        private String title;
        private String description;
        private EditionType editionType;
        private BigDecimal price;
        private Integer copies;
        private LocalDate releaseDate;
        private AgeRestriction ageRestriction;
        private Author author;
        private Set<Category> categories;

        public Builder() {}

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder editionType(EditionType editionType) {
            this.editionType = editionType;
            return this;
        }

        public Builder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public Builder copies(Integer copies) {
            this.copies = copies;
            return this;
        }

        public Builder releaseDate(LocalDate releaseDate) {
            this.releaseDate = releaseDate;
            return this;
        }

        public Builder ageRestriction(AgeRestriction ageRestriction) {
            this.ageRestriction = ageRestriction;
            return this;
        }

        public Builder author(Author author) {
            this.author = author;
            return this;
        }

        public Builder categories(Set<Category> categories) {
            this.categories = categories;
            return this;
        }

        public Book build() {
            Book book = new Book();
            book.setTitle(this.title);
            book.setDescription(this.description);
            book.setEditionType(this.editionType);
            book.setPrice(this.price);
            book.setCopies(this.copies);
            book.setReleaseDate(this.releaseDate);
            book.setAgeRestriction(this.ageRestriction);
            book.setAuthor(this.author);
            book.setCategories(this.categories);
            return book;
        }
    }

}
