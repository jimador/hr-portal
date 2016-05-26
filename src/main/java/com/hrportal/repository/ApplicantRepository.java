package com.hrportal.repository;

import com.hrportal.domain.Applicant;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Applicant entity.
 */
public interface ApplicantRepository extends MongoRepository<Applicant,String> {

}
