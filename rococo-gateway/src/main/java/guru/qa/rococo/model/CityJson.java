package guru.qa.rococo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public class CityJson {

    @JsonProperty("id")
    private UUID id;
    @JsonProperty("city")
    @Size(max = 50, message = "City name can`t be longer than 50 characters")
    private String city;
    @JsonProperty("country")
    @Size(max = 50, message = "Country name can`t be longer than 50 characters")
    private CountryJson country;


    public CityJson() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public CountryJson getCountry() {
        return country;
    }

    public void setCountry(CountryJson country) {
        this.country = country;
    }


}
