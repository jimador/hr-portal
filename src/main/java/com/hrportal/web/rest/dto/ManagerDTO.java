package com.hrportal.web.rest.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the Manager entity.
 */
public class ManagerDTO implements Serializable {

    private String id;

    @NotNull
    @Size(max = 50)
    private String firstName;


    @NotNull
    @Size(max = 50)
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

        ManagerDTO managerDTO = (ManagerDTO) o;

        if ( ! Objects.equals(id, managerDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ManagerDTO{" +
            "id=" + id +
            ", firstName='" + firstName + "'" +
            ", lastName='" + lastName + "'" +
            '}';
    }
}
