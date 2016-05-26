package com.hrportal.repository;

import com.hrportal.domain.Contract;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Contract entity.
 */
public interface ContractRepository extends MongoRepository<Contract,String> {

}
