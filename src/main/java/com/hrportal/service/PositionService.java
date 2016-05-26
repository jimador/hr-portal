package com.hrportal.service;

import com.hrportal.domain.Position;
import com.hrportal.web.rest.dto.PositionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Position.
 */
public interface PositionService {

    /**
     * Save a position.
     * @return the persisted entity
     */
    public PositionDTO save(PositionDTO positionDTO);

    /**
     *  get all the positions.
     *  @return the list of entities
     */
    public Page<Position> findAll(Pageable pageable);

    /**
     *  get the "id" position.
     *  @return the entity
     */
    public Optional<PositionDTO> findOne(String id);

    /**
     *  delete the "id" position.
     */
    public void delete(String id);
}
