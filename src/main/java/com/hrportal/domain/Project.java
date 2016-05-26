package com.hrportal.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Project.
 */

@Document(collection = "project")
public class Project implements Serializable {

    @Id
    private String id;

    @NotNull
    @Size(max = 50)
    @Field("project_name")
    private String projectName;

    @NotNull
    @Size(max = 50)
    @Field("project_title")
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
        Project project = (Project) o;
        if(project.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, project.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Project{" +
            "id=" + id +
            ", projectTitle='" + projectTitle + "'" +
            ", projectName='" + projectName + "'" +
            '}';
    }
}
