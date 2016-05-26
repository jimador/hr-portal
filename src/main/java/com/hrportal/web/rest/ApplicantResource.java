package com.hrportal.web.rest;

import com.hrportal.domain.Applicant;
import com.hrportal.service.ApplicantService;
import com.hrportal.web.rest.dto.ApplicantDTO;
import com.hrportal.web.rest.mapper.ApplicantMapper;
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
 * REST controller for managing Applicant.
 */
@RestController
@RequestMapping("/api")
public class ApplicantResource {

    private final Logger log = LoggerFactory.getLogger(ApplicantResource.class);

    @Inject
    private ApplicantService applicantService;

    @Inject
    private ApplicantMapper applicantMapper;

    /**
     * POST  /applicants -> Create a new applicant.
     */
    @RequestMapping(value = "/applicants",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ApplicantDTO> createApplicant(@Valid @RequestBody ApplicantDTO applicantDTO) throws URISyntaxException {
        log.debug("REST request to save Applicant : {}", applicantDTO);
        if (applicantDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("applicant", "idexists", "A new applicant cannot already have an ID")).body(null);
        }
        ApplicantDTO result = applicantService.save(applicantDTO);
        return ResponseEntity.created(new URI("/api/applicants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("applicant", result.getId()))
            .body(result);
    }

    /**
     * PUT  /applicants -> Updates an existing applicant.
     */
    @RequestMapping(value = "/applicants",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ApplicantDTO> updateApplicant(@Valid @RequestBody ApplicantDTO applicantDTO) throws URISyntaxException {
        log.debug("REST request to update Applicant : {}", applicantDTO);
        if (applicantDTO.getId() == null) {
            return createApplicant(applicantDTO);
        }
        ApplicantDTO result = applicantService.save(applicantDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("applicant", applicantDTO.getId()))
            .body(result);
    }

    /**
     * GET  /applicants -> get all the applicants.
     */
    @RequestMapping(value = "/applicants",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<ApplicantDTO>> getAllApplicants(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Applicants");
        Page<Applicant> page = applicantService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/applicants");
        return new ResponseEntity<>(page.getContent().stream()
            .map(applicantMapper::applicantToApplicantDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /applicants/:id -> get the "id" applicant.
     */
    @RequestMapping(value = "/applicants/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ApplicantDTO> getApplicant(@PathVariable String id) {
        log.debug("REST request to get Applicant : {}", id);
        return applicantService.findOne(id)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /applicants/:id -> delete the "id" applicant.
     */
    @RequestMapping(value = "/applicants/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteApplicant(@PathVariable String id) {
        log.debug("REST request to delete Applicant : {}", id);
        applicantService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("applicant", id)).build();
    }
}
