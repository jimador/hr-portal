package com.hrportal.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A Contract.
 */

@Document(collection = "contract")
public class Contract implements Serializable {

    @Id
    private String id;

    @NotNull
    @Field("contract_name")
    private String contractName;

    @NotNull
    @Field("contract_number")
    private String contractNumber;

    @NotNull
    @Field("contract_labor_category")
    private String contractLaborCategory;

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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contract contract = (Contract) o;

        if (id != null ? !id.equals(contract.id) : contract.id != null) return false;
        if (!contractName.equals(contract.contractName)) return false;
        if (!contractNumber.equals(contract.contractNumber)) return false;
        return contractLaborCategory != null ? contractLaborCategory.equals(contract.contractLaborCategory) : contract.contractLaborCategory == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + contractName.hashCode();
        result = 31 * result + contractNumber.hashCode();
        result = 31 * result + (contractLaborCategory != null ? contractLaborCategory.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Contract{" +
            "id='" + id + '\'' +
            ", contractName='" + contractName + '\'' +
            ", contractNumber='" + contractNumber + '\'' +
            ", contractLaborCategory='" + contractLaborCategory + '\'' +
            '}';
    }
}
