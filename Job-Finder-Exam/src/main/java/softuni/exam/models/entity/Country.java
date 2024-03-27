package softuni.exam.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "countries")
public class Country extends BaseEntity {

    @Column(unique = true,nullable = false)
    private String name;

    @Column(unique = true,nullable = false)
    private String code;
    @Column(nullable = false)
    private String currency;

    public Country() {
    }

    public String getName() {
        return name;
    }

    public Country(String name, String code, String currency) {
        this.name = name;
        this.code = code;
        this.currency = currency;
    }

    public String getCode() {
        return code;
    }

    public String getCurrency() {
        return currency;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
