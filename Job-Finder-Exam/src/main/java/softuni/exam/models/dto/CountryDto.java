package softuni.exam.models.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class CountryDto {
    @NotNull
    @Length(min = 2,max = 30)
    private String name;
    @NotNull
    @Length(min = 2,max = 19)
    private String countryCode;
    @NotNull
    @Length(min = 2,max = 19)
    private String currency;

    public CountryDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
