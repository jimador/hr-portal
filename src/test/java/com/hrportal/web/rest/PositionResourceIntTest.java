package com.hrportal.web.rest;

import com.hrportal.Application;
import com.hrportal.domain.Position;
import com.hrportal.repository.PositionRepository;
import com.hrportal.service.PositionService;
import com.hrportal.web.rest.dto.PositionDTO;
import com.hrportal.web.rest.mapper.PositionMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the PositionResource REST controller.
 *
 * @see PositionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PositionResourceIntTest {

    private static final String DEFAULT_LABOR_CATEGORY = "AAAAA";
    private static final String UPDATED_LABOR_CATEGORY = "BBBBB";


    private static final Integer DEFAULT_SALARY = 999999;
    private static final Integer UPDATED_SALARY = 999998;

    private static final String POSITION_NAME = "AAAA";

    @Inject
    private PositionRepository positionRepository;

    @Inject
    private PositionMapper positionMapper;

    @Inject
    private PositionService positionService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPositionMockMvc;

    private Position position;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PositionResource positionResource = new PositionResource();
        ReflectionTestUtils.setField(positionResource, "positionService", positionService);
        ReflectionTestUtils.setField(positionResource, "positionMapper", positionMapper);
        this.restPositionMockMvc = MockMvcBuilders.standaloneSetup(positionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        positionRepository.deleteAll();
        position = new Position();
        position.setLaborCategory(DEFAULT_LABOR_CATEGORY);
        position.setSalary(DEFAULT_SALARY);
        position.setPositionName(POSITION_NAME);
    }

    @Test
    public void createPosition() throws Exception {
        int databaseSizeBeforeCreate = positionRepository.findAll().size();

        // Create the Position
        PositionDTO positionDTO = positionMapper.positionToPositionDTO(position);

        restPositionMockMvc.perform(post("/api/positions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(positionDTO)))
                .andExpect(status().isCreated());

        // Validate the Position in the database
        List<Position> positions = positionRepository.findAll();
        assertThat(positions).hasSize(databaseSizeBeforeCreate + 1);
        Position testPosition = positions.get(positions.size() - 1);
        assertThat(testPosition.getLaborCategory()).isEqualTo(DEFAULT_LABOR_CATEGORY);
        assertThat(testPosition.getSalary()).isEqualTo(DEFAULT_SALARY);
    }

    @Test
    public void checkSalaryIsRequired() throws Exception {
        int databaseSizeBeforeTest = positionRepository.findAll().size();
        // set the field null
        position.setSalary(null);

        // Create the Position, which fails.
        PositionDTO positionDTO = positionMapper.positionToPositionDTO(position);

        restPositionMockMvc.perform(post("/api/positions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(positionDTO)))
                .andExpect(status().isBadRequest());

        List<Position> positions = positionRepository.findAll();
        assertThat(positions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllPositions() throws Exception {
        // Initialize the database
        positionRepository.save(position);

        // Get all the positions
        restPositionMockMvc.perform(get("/api/positions?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(position.getId())))
                .andExpect(jsonPath("$.[*].laborCategory").value(hasItem(DEFAULT_LABOR_CATEGORY)))
                .andExpect(jsonPath("$.[*].salary").value(hasItem(DEFAULT_SALARY)));
    }

    @Test
    public void getPosition() throws Exception {
        // Initialize the database
        positionRepository.save(position);

        // Get the position
        restPositionMockMvc.perform(get("/api/positions/{id}", position.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(position.getId()))
            .andExpect(jsonPath("$.laborCategory").value(DEFAULT_LABOR_CATEGORY))
            .andExpect(jsonPath("$.salary").value(DEFAULT_SALARY));
    }

    @Test
    public void getNonExistingPosition() throws Exception {
        // Get the position
        restPositionMockMvc.perform(get("/api/positions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updatePosition() throws Exception {
        // Initialize the database
        positionRepository.save(position);

		int databaseSizeBeforeUpdate = positionRepository.findAll().size();

        // Update the position
        position.setLaborCategory(UPDATED_LABOR_CATEGORY);
        position.setSalary(UPDATED_SALARY);
        PositionDTO positionDTO = positionMapper.positionToPositionDTO(position);

        restPositionMockMvc.perform(put("/api/positions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(positionDTO)))
                .andExpect(status().isOk());

        // Validate the Position in the database
        List<Position> positions = positionRepository.findAll();
        assertThat(positions).hasSize(databaseSizeBeforeUpdate);
        Position testPosition = positions.get(positions.size() - 1);
        assertThat(testPosition.getLaborCategory()).isEqualTo(UPDATED_LABOR_CATEGORY);
        assertThat(testPosition.getSalary()).isEqualTo(UPDATED_SALARY);
    }

    @Test
    public void deletePosition() throws Exception {
        // Initialize the database
        positionRepository.save(position);

		int databaseSizeBeforeDelete = positionRepository.findAll().size();

        // Get the position
        restPositionMockMvc.perform(delete("/api/positions/{id}", position.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Position> positions = positionRepository.findAll();
        assertThat(positions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
