package entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "wizards")
public class Wizard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name",length = 50)
    private String firstName;

    @Column(name = "last_name",length = 60)
    private String lastName;

    @Column(length = 1000)
    private String notes;

    @Column(nullable = false)
    private Integer age;
    @OneToOne
    @JoinColumn
    private MagicWand magicWand;

    @OneToMany
    @JoinTable(name = "wizard_deposits")
    private List<Deposit> deposits;

    public Wizard() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public MagicWand getMagicWand() {
        return magicWand;
    }

    public void setMagicWand(MagicWand magicWand) {
        this.magicWand = magicWand;
    }

    public List<Deposit> getDeposits() {
        return deposits;
    }

    public void setDeposits(List<Deposit> deposits) {
        this.deposits = deposits;
    }
}
