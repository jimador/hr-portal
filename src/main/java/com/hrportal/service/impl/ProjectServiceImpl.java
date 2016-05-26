package com.hrportal.service.impl;

import com.hrportal.service.ProjectService;
import com.hrportal.domain.Project;
import com.hrportal.repository.ProjectRepository;
import com.hrportal.web.rest.dto.ProjectDTO;
import com.hrportal.web.rest.mapper.ProjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Optional;

/**
 * Service Implementation for managing Project.
 */
@Service
public class ProjectServiceImpl implements ProjectService{

    private final Logger log = LoggerFactory.getLogger(ProjectServiceImpl.class);

    @Inject
    private ProjectRepository projectRepository;

    @Inject
    private ProjectMapper projectMapper;

    /**
     * Save a project.
     * @return the persisted entity
     */
    public ProjectDTO save(ProjectDTO projectDTO) {
        log.debug("Request to save Project : {}", projectDTO);
        Project project = projectMapper.projectDTOToProject(projectDTO);
        project = projectRepository.save(project);
        ProjectDTO result = projectMapper.projectToProjectDTO(project);
        return result;
    }

    /**
     *  get all the projects.
     *  @return the list of entities
     */
    public Page<Project> findAll(Pageable pageable) {
        log.debug("Request to get all Projects");
        Page<Project> result = projectRepository.findAll(pageable);
        return result;
    }

    /**
     *  get one project by id.
     *  @return the entity
     */
    public Optional<ProjectDTO> findOne(String id) {
        log.debug("Request to get Project : {}", id);
        Project project = projectRepository.findOne(id);
        ProjectDTO projectDTO = projectMapper.projectToProjectDTO(project);
        return Optional.ofNullable(projectDTO);
    }

    /**
     *  delete the  project by id.
     */
    public void delete(String id) {
        log.debug("Request to delete Project : {}", id);
        projectRepository.delete(id);
    }
}
