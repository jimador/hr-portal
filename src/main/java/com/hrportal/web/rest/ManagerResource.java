package com.hrportal.web.rest;

import com.hrportal.domain.Manager;
import com.hrportal.service.ManagerService;
import com.hrportal.web.rest.dto.ManagerDTO;
import com.hrportal.web.rest.mapper.ManagerMapper;
import com.hrportal.web.rest.util.HeaderUtil;
import com.hrportal.web.rest.util.PaginationUtil;
import com.codahale.metrics.annotation.Timed;
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
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for managing Manager.
 */
@RestController
@RequestMapping("/api")
public class ManagerResource {

    private final Logger log = LoggerFactory.getLogger(ManagerResource.class);

    @Inject
    private ManagerService managerService;

    @Inject
    private ManagerMapper managerMapper;

    /**
     * POST  /managers -> Create a new manager.
     */
    @RequestMapping(value = "/managers",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ManagerDTO> createManager(@Valid @RequestBody ManagerDTO managerDTO) throws URISyntaxException {
        log.debug("REST request to save Manager : {}", managerDTO);
        if (managerDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("manager", "idexists", "A new manager cannot already have an ID")).body(null);
        }
        ManagerDTO result = managerService.save(managerDTO);
        return ResponseEntity.created(new URI("/api/managers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("manager", result.getId()))
            .body(result);
    }

    /**
     * PUT  /managers -> Updates an existing manager.
     */
    @RequestMapping(value = "/managers",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ManagerDTO> updateManager(@Valid @RequestBody ManagerDTO managerDTO) throws URISyntaxException {
        log.debug("REST request to update Manager : {}", managerDTO);
        if (managerDTO.getId() == null) {
            return createManager(managerDTO);
        }
        ManagerDTO result = managerService.save(managerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("manager", managerDTO.getId()))
            .body(result);
    }

    /**
     * GET  /managers -> get all the managers.
     */
    @RequestMapping(value = "/managers",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<ManagerDTO>> getAllManagers(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Managers");
        Page<Manager> page = managerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/managers");
        return new ResponseEntity<>(page.getContent().stream()
            .map(managerMapper::managerToManagerDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /managers/:id -> get the "id" manager.
     */
    @RequestMapping(value = "/managers/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ManagerDTO> getManager(@PathVariable String id) {
        log.debug("REST request to get Manager : {}", id);
        return managerService.findOne(id)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /managers/:id -> delete the "id" manager.
     */
    @RequestMapping(value = "/managers/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteManager(@PathVariable String id) {
        log.debug("REST request to delete Manager : {}", id);
        managerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("manager", id)).build();
    }
}
