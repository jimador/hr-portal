package com.hrportal.web.rest.mapper;

import com.hrportal.domain.*;
import com.hrportal.web.rest.dto.ApplicantDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Applicant and its DTO ApplicantDTO.
 */
@Mapper(componentModel = "spring", uses = {
    PositionMapper.class,
    ContractMapper.class,
    ManagerMapper.class,
    ProjectMapper.class
})
public interface ApplicantMapper {

    ApplicantDTO applicantToApplicantDTO(Applicant applicant);

    Applicant applicantDTOToApplicant(ApplicantDTO applicantDTO);
}
