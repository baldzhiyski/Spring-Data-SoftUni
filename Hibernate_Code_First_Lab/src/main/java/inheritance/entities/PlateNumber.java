package inheritance.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "plate_number")
public class PlateNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String number;


    public PlateNumber(String number) {
        this.number = number;
    }

    public PlateNumber() {

    }
}
