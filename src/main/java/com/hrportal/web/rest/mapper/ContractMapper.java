package com.hrportal.web.rest.mapper;

import com.hrportal.domain.Contract;
import com.hrportal.web.rest.dto.ContractDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity Contract and its DTO ContractDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ContractMapper {

    ContractDTO contractToContractDTO(Contract contract);

    Contract contractDTOToContract(ContractDTO contractDTO);
}
