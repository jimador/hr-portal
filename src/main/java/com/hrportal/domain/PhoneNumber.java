package com.hrportal.domain;

import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by james on 2/29/2016.
 */
public class PhoneNumber {
    @NotNull
    @Size(max = 10)
    @Pattern(regexp = "^(\\+0?1\\s)?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4}$")
    @Field("phone_number")
    private String phoneNumber;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PhoneNumber that = (PhoneNumber) o;

        return phoneNumber.equals(that.phoneNumber);

    }

    @Override
    public int hashCode() {
        return phoneNumber.hashCode();
    }

    @Override
    public String toString() {
        return "PhoneNumber{" +
            "phoneNumber='" + phoneNumber + '\'' +
            '}';
    }
}
