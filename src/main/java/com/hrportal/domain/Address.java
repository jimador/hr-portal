package com.hrportal.domain;

import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by james on 2/29/2016.
 */
public class Address {
    @NotNull
    @Size(max = 90)
    @Field("street")
    private String street;

    @NotNull
    @Size(max = 50)
    @Field("city")
    private String city;

    @NotNull
    @Size(max = 50)
    @Field("state")
    private String state;

    @Size(max = 50)
    @Field("county")
    private String county;

    @NotNull
    @Size(max = 5)
    @Field("zip")
    private String zip;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        if (!street.equals(address.street)) return false;
        if (!city.equals(address.city)) return false;
        if (!state.equals(address.state)) return false;
        if (county != null ? !county.equals(address.county) : address.county != null) return false;
        return zip.equals(address.zip);

    }

    @Override
    public int hashCode() {
        int result = street.hashCode();
        result = 31 * result + city.hashCode();
        result = 31 * result + state.hashCode();
        result = 31 * result + (county != null ? county.hashCode() : 0);
        result = 31 * result + zip.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Address{" +
            "street='" + street + '\'' +
            ", city='" + city + '\'' +
            ", state='" + state + '\'' +
            ", county='" + county + '\'' +
            ", zip='" + zip + '\'' +
            '}';
    }
}
