package com.hrportal.web.rest.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the Project entity.
 */
public class ProjectDTO implements Serializable {

    private String id;

    @NotNull
    @Size(max = 50)
    private String projectName;

    @NotNull
    @Size(max = 50)
    private String projectTitle;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProjectDTO projectDTO = (ProjectDTO) o;

        if ( ! Objects.equals(id, projectDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ProjectDTO{" +
            "id=" + id +
            ", projectTitle='" + projectTitle + "'" +
            ", projectName='" + projectName + "'" +
            '}';
    }
}
