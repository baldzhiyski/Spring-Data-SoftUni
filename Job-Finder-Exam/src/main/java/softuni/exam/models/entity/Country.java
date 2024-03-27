package softuni.exam.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "countries")
public class Country extends BaseEntity {

    @Column(unique = true,nullable = false)
    private String name;

    @Column(unique = true,nullable = false)
    private String countryCode;
    @Column(nullable = false)
    private String currency;

    public Country() {
    }

    public String getName() {
        return name;
    }

    public Country(String name, String code, String currency) {
        this.name = name;
        this.countryCode = code;
        this.currency = currency;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return Objects.equals(name, country.name) && Objects.equals(countryCode, country.countryCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, countryCode);
    }
}
