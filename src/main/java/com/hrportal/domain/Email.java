package com.hrportal.domain;

import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by james on 2/29/2016.
 */
public class Email{

    @NotNull
    @Size(max = 90)
    @Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?")
    @Field("email")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Email email1 = (Email) o;

        return email.equals(email1.email);

    }

    @Override
    public int hashCode() {
        return email.hashCode();
    }

    @Override
    public String toString() {
        return "Email{" +
            "email='" + email + '\'' +
            '}';
    }
}
