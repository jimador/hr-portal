package com.hrportal.service.impl;

import com.hrportal.domain.Applicant;
import com.hrportal.repository.ApplicantRepository;
import com.hrportal.service.ApplicantService;
import com.hrportal.web.rest.dto.ApplicantDTO;
import com.hrportal.web.rest.mapper.ApplicantMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Optional;

/**
 * Service Implementation for managing Applicant.
 */
@Service
public class ApplicantServiceImpl implements ApplicantService{

    private final Logger log = LoggerFactory.getLogger(ApplicantServiceImpl.class);

    @Inject
    private ApplicantRepository applicantRepository;

    @Inject
    private ApplicantMapper applicantMapper;

    /**
     * Save a applicant.
     * @return the persisted entity
     */
    public ApplicantDTO save(ApplicantDTO applicantDTO) {
        log.debug("Request to save Applicant : {}", applicantDTO);
        Applicant applicant = applicantMapper.applicantDTOToApplicant(applicantDTO);
        applicant = applicantRepository.save(applicant);
        return applicantMapper.applicantToApplicantDTO(applicant);
    }

    /**
     *  get all the applicants.
     *  @return the list of entities
     */
    public Page<Applicant> findAll(Pageable pageable) {
        log.debug("Request to get all Applicants");
        return applicantRepository.findAll(pageable);
    }

    /**
     *  get one applicant by id.
     *  @return the entity
     */
    public Optional<ApplicantDTO> findOne(String id) {
        log.debug("Request to get Applicant : {}", id);
        Applicant applicant = applicantRepository.findOne(id);
        ApplicantDTO applicantDTO = applicantMapper.applicantToApplicantDTO(applicant);
        return Optional.ofNullable(applicantDTO);
    }

    /**
     *  delete the  applicant by id.
     */
    public void delete(String id) {
        log.debug("Request to delete Applicant : {}", id);
        applicantRepository.delete(id);
    }
}
