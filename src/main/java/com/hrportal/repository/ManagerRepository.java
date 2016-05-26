package com.hrportal.repository;

import com.hrportal.domain.Manager;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Manager entity.
 */
public interface ManagerRepository extends MongoRepository<Manager,String> {

}
