package com.hrportal.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Position.
 */

@Document(collection = "position")
public class Position implements Serializable {

    @Id
    private String id;

    @NotNull
    @Field("labor_category")
    private String laborCategory;

    @NotNull
    @Field("position_name")
    private String positionName;

    @NotNull
    @Max(value = 999_999)
    @Field("salary")
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (id != null ? !id.equals(position.id) : position.id != null) return false;
        if (!laborCategory.equals(position.laborCategory)) return false;
        if (!positionName.equals(position.positionName)) return false;
        return salary.equals(position.salary);

    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Position{" +
            "id='" + id + '\'' +
            ", laborCategory='" + laborCategory + '\'' +
            ", positionName='" + positionName + '\'' +
            ", salary=" + salary +
            '}';
    }
}
