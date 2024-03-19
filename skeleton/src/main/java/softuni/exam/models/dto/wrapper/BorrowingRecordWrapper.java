package softuni.exam.models.dto.wrapper;

import softuni.exam.models.dto.BorrowingRecordDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "borrowing_records")
@XmlAccessorType(XmlAccessType.FIELD)
public class BorrowingRecordWrapper {
    @XmlElement(name = "borrowing_record")
    private List<BorrowingRecordDto> borrowingRecordDtos;

    public BorrowingRecordWrapper(List<BorrowingRecordDto> borrowingRecordDtos) {
        this.borrowingRecordDtos = borrowingRecordDtos;
    }

    public BorrowingRecordWrapper() {
    }

    public List<BorrowingRecordDto> getBorrowingRecordDtos() {
        return borrowingRecordDtos;
    }

    public void setBorrowingRecordDtos(List<BorrowingRecordDto> borrowingRecordDtos) {
        this.borrowingRecordDtos = borrowingRecordDtos;
    }
}
