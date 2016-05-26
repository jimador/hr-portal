package com.hrportal.web.rest.mapper;

import com.hrportal.domain.*;
import com.hrportal.web.rest.dto.PositionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Position and its DTO PositionDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PositionMapper {

    PositionDTO positionToPositionDTO(Position position);

    Position positionDTOToPosition(PositionDTO positionDTO);
}
