package softuni.exam.models.dto;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "borrowing_record")
@XmlAccessorType(XmlAccessType.FIELD)
public class BorrowingRecordDto {
    @XmlElement(name = "borrow_date")
    @NotNull
    private String borrowDate;
    @XmlElement(name = "return_date")
    @NotNull
    private String returnDate;

    @XmlElement(name = "book")
    private BookTitleDto book;
    @XmlElement(name = "member")
    private MemberIdDto member;
    @XmlElement(name = "remarks")
    @Size(min = 3,max = 100)
    private String remarks;

    public BorrowingRecordDto(String borrowDate, String returnDate, BookTitleDto book, MemberIdDto member, String remarks) {
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.book = book;
        this.member = member;
        this.remarks = remarks;
    }

    public BorrowingRecordDto() {

    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(String borrowDate) {
        this.borrowDate = borrowDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public BookTitleDto getBook() {
        return book;
    }

    public void setBook(BookTitleDto book) {
        this.book = book;
    }

    public MemberIdDto getMember() {
        return member;
    }

    public void setMember(MemberIdDto member) {
        this.member = member;
    }
}
