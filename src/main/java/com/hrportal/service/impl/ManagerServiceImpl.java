package com.hrportal.service.impl;

import com.hrportal.service.ManagerService;
import com.hrportal.domain.Manager;
import com.hrportal.repository.ManagerRepository;
import com.hrportal.web.rest.dto.ManagerDTO;
import com.hrportal.web.rest.mapper.ManagerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Optional;

/**
 * Service Implementation for managing Manager.
 */
@Service
public class ManagerServiceImpl implements ManagerService{

    private final Logger log = LoggerFactory.getLogger(ManagerServiceImpl.class);

    @Inject
    private ManagerRepository managerRepository;

    @Inject
    private ManagerMapper managerMapper;

    /**
     * Save a manager.
     * @return the persisted entity
     */
    public ManagerDTO save(ManagerDTO managerDTO) {
        log.debug("Request to save Manager : {}", managerDTO);
        Manager manager = managerMapper.managerDTOToManager(managerDTO);
        manager = managerRepository.save(manager);
        ManagerDTO result = managerMapper.managerToManagerDTO(manager);
        return result;
    }

    /**
     *  get all the managers.
     *  @return the list of entities
     */
    public Page<Manager> findAll(Pageable pageable) {
        log.debug("Request to get all Managers");
        Page<Manager> result = managerRepository.findAll(pageable);
        return result;
    }

    /**
     *  get one manager by id.
     *  @return the entity
     */
    public Optional<ManagerDTO> findOne(String id) {
        log.debug("Request to get Manager : {}", id);
        Manager manager = managerRepository.findOne(id);
        return Optional.ofNullable(managerMapper.managerToManagerDTO(manager));
    }

    /**
     *  delete the  manager by id.
     */
    public void delete(String id) {
        log.debug("Request to delete Manager : {}", id);
        managerRepository.delete(id);
    }
}
