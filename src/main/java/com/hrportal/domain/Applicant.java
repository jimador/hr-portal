package com.hrportal.domain;

import com.hrportal.domain.enumeration.Suffix;
import com.hrportal.domain.enumeration.Title;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Applicant.
 */

@Document(collection = "applicant")
public class Applicant implements Serializable {

    @Id
    private String id;

    @Field("title")
    private Title title;

    @NotNull
    @Size(max = 50)
    @Field("first_name")
    private String firstName;

    @NotNull
    @Size(max = 50)
    @Field("last_name")
    private String lastName;

    @Size(max = 50)
    @Field("middle_name")
    private String middleName;

    @NotNull
    @Field("offer_date")
    private LocalDate offerDate;

    @Field("suffix")
    private Suffix suffix;

    @NotNull
    @Field("contact_info")
    private ContactInfo contactInfo;

    @NotNull
    @Field("position")
    private Position position;

    @Field("project")
    private Project project;

    @Field("contract")
    private Contract contract;

    @NotNull
    @Field("manager")
    private Manager manager;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
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

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public LocalDate getOfferDate() {
        return offerDate;
    }

    public void setOfferDate(LocalDate offerDate) {
        this.offerDate = offerDate;
    }

    public Suffix getSuffix() {
        return suffix;
    }

    public void setSuffix(Suffix suffix) {
        this.suffix = suffix;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position project) {
        this.position = project;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Applicant applicant = (Applicant) o;

        if (!id.equals(applicant.id)) return false;
        if (title != applicant.title) return false;
        if (!firstName.equals(applicant.firstName)) return false;
        if (!lastName.equals(applicant.lastName)) return false;
        if (!middleName.equals(applicant.middleName)) return false;
        if (!offerDate.equals(applicant.offerDate)) return false;
        if (!position.equals(applicant.position)) return false;
        if (!contract.equals(applicant.contract)) return false;
        if (!project.equals(applicant.project)) return false;
        if (!manager.equals(applicant.manager)) return false;
        return suffix == applicant.suffix && contactInfo.equals(applicant.contactInfo);

    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Applicant{" +
            "id='" + id + '\'' +
            ", title=" + title +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", middleName='" + middleName + '\'' +
            ", offerDate=" + offerDate + '\'' +
            ", suffix=" + suffix + '\'' +
            ", contactInfo=" + contactInfo + '\'' +
            ", project=" + position + '\'' +
            ", contract=" + contract + '\'' +
            ", project=" + project + '\'' +
            ", manager=" + manager +
            '}';
    }
}
