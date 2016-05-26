package com.hrportal.web.rest.dto;

import com.hrportal.domain.ContactInfo;
import com.hrportal.domain.enumeration.Suffix;
import com.hrportal.domain.enumeration.Title;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the Applicant entity.
 */
public class ApplicantDTO implements Serializable {

    private String id;

    private Title title;

    @NotNull
    @Size(max = 50)
    private String firstName;

    @NotNull
    @Size(max = 50)
    private String lastName;

    @Size(max = 50)
    private String middleName;

    private Suffix suffix;

    @NotNull
    private LocalDate offerDate;

    @NotNull
    private PositionDTO positionDTO;

    private ContractDTO contractDto;

    @NotNull
    private ManagerDTO managerDTO;

    private ProjectDTO projectDTO;

    @NotNull
    private ContactInfo contactInfo;

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

    public PositionDTO getPosition() {
        return positionDTO;
    }

    public void setPosition(PositionDTO position) {
        this.positionDTO = position;
    }

    public ContractDTO getContract() {
        return contractDto;
    }

    public void setContract(ContractDTO contractDto) {
        this.contractDto = contractDto;
    }

    public ManagerDTO getManager() {
        return managerDTO;
    }

    public void setManager(ManagerDTO managerDTO) {
        this.managerDTO = managerDTO;
    }

    public ProjectDTO getProject() {
        return projectDTO;
    }

    public void setProject(ProjectDTO projectDTO) {
        this.projectDTO = projectDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApplicantDTO that = (ApplicantDTO) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (title != that.title) return false;
        if (!firstName.equals(that.firstName)) return false;
        if (!lastName.equals(that.lastName)) return false;
        if (middleName != null ? !middleName.equals(that.middleName) : that.middleName != null) return false;
        if (!offerDate.equals(that.offerDate)) return false;
        if (!contactInfo.equals(that.contactInfo)) return false;
        if (!positionDTO.equals(that.positionDTO)) return false;
        if (!contractDto.equals(that.contractDto)) return false;
        if (!projectDTO.equals(that.projectDTO)) return false;
        if (!managerDTO.equals(that.managerDTO)) return false;
        return suffix == that.suffix;

    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ApplicantDTO{" +
            "id='" + id + '\'' +
            ", title=" + title +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", middleName='" + middleName + '\'' +
            ", offerDate=" + offerDate +
            ", contactInfo=" + contactInfo +
            ", suffix=" + suffix +
            '}';
    }
}
