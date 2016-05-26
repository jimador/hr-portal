package com.hrportal.service.impl;

import com.hrportal.service.ContractService;
import com.hrportal.domain.Contract;
import com.hrportal.repository.ContractRepository;
import com.hrportal.web.rest.dto.ContractDTO;
import com.hrportal.web.rest.mapper.ContractMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Optional;

/**
 * Service Implementation for managing Contract.
 */
@Service
public class ContractServiceImpl implements ContractService{

    private final Logger log = LoggerFactory.getLogger(ContractServiceImpl.class);

    @Inject
    private ContractRepository contractRepository;

    @Inject
    private ContractMapper contractMapper;

    /**
     * Save a contract.
     * @return the persisted entity
     */
    public ContractDTO save(ContractDTO contractDTO) {
        log.debug("Request to save Contract : {}", contractDTO);
        Contract contract = contractMapper.contractDTOToContract(contractDTO);
        contract = contractRepository.save(contract);
        return contractMapper.contractToContractDTO(contract);
    }

    /**
     *  get all the contracts.
     *  @return the list of entities
     */
    public Page<Contract> findAll(Pageable pageable) {
        log.debug("Request to get all Contracts");
        Page<Contract> result = contractRepository.findAll(pageable);
        return result;
    }

    /**
     *  get one contract by id.
     *  @return the entity
     */
    public Optional<ContractDTO> findOne(String id) {
        log.debug("Request to get Contract : {}", id);
        Contract contract = contractRepository.findOne(id);
        return Optional.ofNullable(contractMapper.contractToContractDTO(contract));
    }

    /**
     *  delete the  contract by id.
     */
    public void delete(String id) {
        log.debug("Request to delete Contract : {}", id);
        contractRepository.delete(id);
    }
}
