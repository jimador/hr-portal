package com.hrportal.web.rest;

import com.hrportal.domain.Position;
import com.hrportal.service.PositionService;
import com.hrportal.web.rest.dto.PositionDTO;
import com.hrportal.web.rest.mapper.PositionMapper;
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
 * REST controller for managing Position.
 */
@RestController
@RequestMapping("/api")
public class PositionResource {

    private final Logger log = LoggerFactory.getLogger(PositionResource.class);

    @Inject
    private PositionService positionService;

    @Inject
    private PositionMapper positionMapper;

    /**
     * POST  /positions -> Create a new position.
     */
    @RequestMapping(value = "/positions",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PositionDTO> createPosition(@Valid @RequestBody PositionDTO positionDTO) throws URISyntaxException {
        log.debug("REST request to save Position : {}", positionDTO);
        if (positionDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("position", "idexists", "A new position cannot already have an ID")).body(null);
        }
        PositionDTO result = positionService.save(positionDTO);
        return ResponseEntity.created(new URI("/api/positions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("position", result.getId()))
            .body(result);
    }

    /**
     * PUT  /positions -> Updates an existing position.
     */
    @RequestMapping(value = "/positions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PositionDTO> updatePosition(@Valid @RequestBody PositionDTO positionDTO) throws URISyntaxException {
        log.debug("REST request to update Position : {}", positionDTO);
        if (positionDTO.getId() == null) {
            return createPosition(positionDTO);
        }
        PositionDTO result = positionService.save(positionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("position", positionDTO.getId()))
            .body(result);
    }

    /**
     * GET  /positions -> get all the positions.
     */
    @RequestMapping(value = "/positions",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<PositionDTO>> getAllPositions(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Positions");
        Page<Position> page = positionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/positions");
        return new ResponseEntity<>(page.getContent().stream()
            .map(positionMapper::positionToPositionDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /positions/:id -> get the "id" position.
     */
    @RequestMapping(value = "/positions/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PositionDTO> getPosition(@PathVariable String id) {
        log.debug("REST request to get Position : {}", id);
        return positionService.findOne(id)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /positions/:id -> delete the "id" position.
     */
    @RequestMapping(value = "/positions/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePosition(@PathVariable String id) {
        log.debug("REST request to delete Position : {}", id);
        positionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("position", id)).build();
    }
}
