package com.hrportal.web.rest.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the Contract entity.
 */
public class ContractDTO implements Serializable {

    private String id;

    @NotNull
    private String contractNumber;

    @NotNull
    private String contractLaborCategory;

    @NotNull
    private String contractName;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }
    public String getContractLaborCategory() {
        return contractLaborCategory;
    }

    public void setContractLaborCategory(String contractLaborCategory) {
        this.contractLaborCategory = contractLaborCategory;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ContractDTO contractDTO = (ContractDTO) o;

        if ( ! Objects.equals(id, contractDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ContractDTO{" +
            "id=" + id +
            ", contractNumber='" + contractNumber + "'" +
            ", contractLaborCategory='" + contractLaborCategory +
            ", contractName='" + contractName +
            "'" +
            '}';
    }
}
