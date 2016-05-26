package com.hrportal.service;

import com.hrportal.domain.Contract;
import com.hrportal.web.rest.dto.ContractDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Contract.
 */
public interface ContractService {

    /**
     * Save a contract.
     * @return the persisted entity
     */
    public ContractDTO save(ContractDTO contractDTO);

    /**
     *  get all the contracts.
     *  @return the list of entities
     */
    public Page<Contract> findAll(Pageable pageable);

    /**
     *  get the "id" contract.
     *  @return the entity
     */
    public Optional<ContractDTO> findOne(String id);

    /**
     *  delete the "id" contract.
     */
    public void delete(String id);
}
