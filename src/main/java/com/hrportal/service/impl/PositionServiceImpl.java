package com.hrportal.service.impl;

import com.hrportal.service.PositionService;
import com.hrportal.domain.Position;
import com.hrportal.repository.PositionRepository;
import com.hrportal.web.rest.dto.PositionDTO;
import com.hrportal.web.rest.mapper.PositionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Optional;

/**
 * Service Implementation for managing Position.
 */
@Service
public class PositionServiceImpl implements PositionService{

    private final Logger log = LoggerFactory.getLogger(PositionServiceImpl.class);

    @Inject
    private PositionRepository positionRepository;

    @Inject
    private PositionMapper positionMapper;

    /**
     * Save a position.
     * @return the persisted entity
     */
    public PositionDTO save(PositionDTO positionDTO) {
        log.debug("Request to save Position : {}", positionDTO);
        Position position = positionMapper.positionDTOToPosition(positionDTO);
        position = positionRepository.save(position);
        return positionMapper.positionToPositionDTO(position);
    }

    /**
     *  get all the positions.
     *  @return the list of entities
     */
    public Page<Position> findAll(Pageable pageable) {
        log.debug("Request to get all Positions");
        return positionRepository.findAll(pageable);
    }

    /**
     *  get one position by id.
     *  @return the entity
     */
    public Optional<PositionDTO> findOne(String id) {
        log.debug("Request to get Position : {}", id);
        Position position = positionRepository.findOne(id);
        PositionDTO positionDTO = positionMapper.positionToPositionDTO(position);
        return Optional.ofNullable(positionDTO);
    }

    /**
     *  delete the  position by id.
     */
    public void delete(String id) {
        log.debug("Request to delete Position : {}", id);
        positionRepository.delete(id);
    }
}
