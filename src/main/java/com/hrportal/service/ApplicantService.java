package com.hrportal.service;

import com.hrportal.domain.Applicant;
import com.hrportal.web.rest.dto.ApplicantDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Applicant.
 */
public interface ApplicantService {

    /**
     * Save a applicant.
     * @return the persisted entity
     */
    public ApplicantDTO save(ApplicantDTO applicantDTO);

    /**
     *  get all the applicants.
     *  @return the list of entities
     */
    public Page<Applicant> findAll(Pageable pageable);

    /**
     *  get the "id" applicant.
     *  @return the entity
     */
    public Optional<ApplicantDTO> findOne(String id);

    /**
     *  delete the "id" applicant.
     */
    public void delete(String id);
}
