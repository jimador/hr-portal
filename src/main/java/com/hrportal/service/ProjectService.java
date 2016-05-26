package com.hrportal.service;

import com.hrportal.domain.Project;
import com.hrportal.web.rest.dto.ProjectDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Project.
 */
public interface ProjectService {

    /**
     * Save a project.
     * @return the persisted entity
     */
    public ProjectDTO save(ProjectDTO projectDTO);

    /**
     *  get all the projects.
     *  @return the list of entities
     */
    public Page<Project> findAll(Pageable pageable);

    /**
     *  get the "id" project.
     *  @return the entity
     */
    public Optional<ProjectDTO> findOne(String id);

    /**
     *  delete the "id" project.
     */
    public void delete(String id);
}
