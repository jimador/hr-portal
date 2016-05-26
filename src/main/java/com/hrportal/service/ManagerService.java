package com.hrportal.service;

import com.hrportal.domain.Manager;
import com.hrportal.web.rest.dto.ManagerDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Manager.
 */
public interface ManagerService {

    /**
     * Save a manager.
     * @return the persisted entity
     */
    public ManagerDTO save(ManagerDTO managerDTO);

    /**
     *  get all the managers.
     *  @return the list of entities
     */
    public Page<Manager> findAll(Pageable pageable);

    /**
     *  get the "id" manager.
     *  @return the entity
     */
    public Optional<ManagerDTO> findOne(String id);

    /**
     *  delete the "id" manager.
     */
    public void delete(String id);
}
