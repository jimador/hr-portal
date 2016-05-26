package com.hrportal.web.rest.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the Position entity.
 */
public class PositionDTO implements Serializable {

    private String id;

    private String laborCategory;

    private String positionName;

    @NotNull
    @Max(value = 999999)
    private Integer salary;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getLaborCategory() {
        return laborCategory;
    }

    public void setLaborCategory(String laborCategory) {
        this.laborCategory = laborCategory;
    }
    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PositionDTO positionDTO = (PositionDTO) o;

        return Objects.equals(id, positionDTO.id);

    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PositionDTO{" +
            "id=" + id +
            ", laborCategory='" + laborCategory + "'" +
            ", positionName='" + positionName + "'" +
            ", salary='" + salary + "'" +
            '}';
    }
}
