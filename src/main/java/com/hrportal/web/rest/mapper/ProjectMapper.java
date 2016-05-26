package com.hrportal.web.rest.mapper;

import com.hrportal.domain.*;
import com.hrportal.web.rest.dto.ProjectDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Project and its DTO ProjectDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProjectMapper {

    ProjectDTO projectToProjectDTO(Project project);

    Project projectDTOToProject(ProjectDTO projectDTO);
}
