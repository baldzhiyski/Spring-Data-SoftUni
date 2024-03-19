package softuni.exam.models.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "borrowing_records")
public class BorrowingRecord extends BaseEntity {

    @Column(nullable = false,name = "borrow_date")
    private LocalDate borrowDate;

    @Column(nullable = false,name = "return_date")
    private LocalDate returnDate;

    @Column
    private String remarks;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private LibraryMember libraryMember;

    public BorrowingRecord() {
    }

    public BorrowingRecord(LocalDate borrowDate, LocalDate returnDate, String remarks, Book book, LibraryMember libraryMember) {
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.remarks = remarks;
        this.book = book;
        this.libraryMember = libraryMember;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LibraryMember getLibraryMember() {
        return libraryMember;
    }

    public void setLibraryMember(LibraryMember libraryMember) {
        this.libraryMember = libraryMember;
    }
}
