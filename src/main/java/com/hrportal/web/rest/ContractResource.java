package com.hrportal.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hrportal.domain.Contract;
import com.hrportal.service.ContractService;
import com.hrportal.web.rest.util.HeaderUtil;
import com.hrportal.web.rest.util.PaginationUtil;
import com.hrportal.web.rest.dto.ContractDTO;
import com.hrportal.web.rest.mapper.ContractMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Contract.
 */
@RestController
@RequestMapping("/api")
public class ContractResource {

    private final Logger log = LoggerFactory.getLogger(ContractResource.class);

    @Inject
    private ContractService contractService;

    @Inject
    private ContractMapper contractMapper;

    /**
     * POST  /contracts -> Create a new contract.
     */
    @RequestMapping(value = "/contracts",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ContractDTO> createContract(@RequestBody ContractDTO contractDTO) throws URISyntaxException {
        log.debug("REST request to save Contract : {}", contractDTO);
        if (contractDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("contract", "idexists", "A new contract cannot already have an ID")).body(null);
        }
        ContractDTO result = contractService.save(contractDTO);
        return ResponseEntity.created(new URI("/api/contracts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("contract", result.getId()))
            .body(result);
    }

    /**
     * PUT  /contracts -> Updates an existing contract.
     */
    @RequestMapping(value = "/contracts",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ContractDTO> updateContract(@RequestBody ContractDTO contractDTO) throws URISyntaxException {
        log.debug("REST request to update Contract : {}", contractDTO);
        if (contractDTO.getId() == null) {
            return createContract(contractDTO);
        }
        ContractDTO result = contractService.save(contractDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("contract", contractDTO.getId()))
            .body(result);
    }

    /**
     * GET  /contracts -> get all the contracts.
     */
    @RequestMapping(value = "/contracts",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<ContractDTO>> getAllContracts(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Contracts");
        Page<Contract> page = contractService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/contracts");
        return new ResponseEntity<>(page.getContent().stream()
            .map(contractMapper::contractToContractDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /contracts/:id -> get the "id" contract.
     */
    @RequestMapping(value = "/contracts/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ContractDTO> getContract(@PathVariable String id) {
        log.debug("REST request to get Contract : {}", id);
        Optional<ContractDTO> contractDTO = contractService.findOne(id);
        return contractDTO
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /contracts/:id -> delete the "id" contract.
     */
    @RequestMapping(value = "/contracts/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteContract(@PathVariable String id) {
        log.debug("REST request to delete Contract : {}", id);
        contractService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("contract", id)).build();
    }
}
