package com.hrportal.repository;

import com.hrportal.domain.Project;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Project entity.
 */
public interface ProjectRepository extends MongoRepository<Project,String> {

}
