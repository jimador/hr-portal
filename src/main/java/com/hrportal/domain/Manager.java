package com.hrportal.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Manager.
 */

@Document(collection = "manager")
public class Manager implements Serializable {

    @Id
    private String id;

    @NotNull
    @Size(max = 50)
    @Field("first_name")
    private String firstName;

    @NotNull
    @Size(max = 50)
    @Field("last_name")
    private String lastName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Manager manager = (Manager) o;
        if(manager.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, manager.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Manager{" +
            "id=" + id +
            ", firstName='" + firstName + "'" +
            ", lastName='" + lastName + "'" +
            '}';
    }
}
