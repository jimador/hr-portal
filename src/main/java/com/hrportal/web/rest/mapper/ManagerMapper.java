package com.hrportal.web.rest.mapper;

import com.hrportal.domain.Manager;
import com.hrportal.web.rest.dto.ManagerDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity Manager and its DTO ManagerDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ManagerMapper {

    ManagerDTO managerToManagerDTO(Manager manager);

    Manager managerDTOToManager(ManagerDTO managerDTO);
}
