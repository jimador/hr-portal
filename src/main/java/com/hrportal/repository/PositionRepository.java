package com.hrportal.repository;

import com.hrportal.domain.Position;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Position entity.
 */
public interface PositionRepository extends MongoRepository<Position,String> {

}
