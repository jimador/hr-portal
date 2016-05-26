package com.hrportal.web.rest;

import com.hrportal.Application;
import com.hrportal.domain.Manager;
import com.hrportal.repository.ManagerRepository;
import com.hrportal.service.ManagerService;
import com.hrportal.web.rest.dto.ManagerDTO;
import com.hrportal.web.rest.mapper.ManagerMapper;
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
 * Test class for the ManagerResource REST controller.
 *
 * @see ManagerResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ManagerResourceIntTest {

    private static final String DEFAULT_FIRST_NAME = "AAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBB";
    private static final String DEFAULT_LAST_NAME = "AAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBB";

    @Inject
    private ManagerRepository managerRepository;

    @Inject
    private ManagerMapper managerMapper;

    @Inject
    private ManagerService managerService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restManagerMockMvc;

    private Manager manager;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ManagerResource managerResource = new ManagerResource();
        ReflectionTestUtils.setField(managerResource, "managerService", managerService);
        ReflectionTestUtils.setField(managerResource, "managerMapper", managerMapper);
        this.restManagerMockMvc = MockMvcBuilders.standaloneSetup(managerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        managerRepository.deleteAll();
        manager = new Manager();
        manager.setFirstName(DEFAULT_FIRST_NAME);
        manager.setLastName(DEFAULT_LAST_NAME);
    }

    @Test
    public void createManager() throws Exception {
        int databaseSizeBeforeCreate = managerRepository.findAll().size();

        // Create the Manager
        ManagerDTO managerDTO = managerMapper.managerToManagerDTO(manager);

        restManagerMockMvc.perform(post("/api/managers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(managerDTO)))
                .andExpect(status().isCreated());

        // Validate the Manager in the database
        List<Manager> managers = managerRepository.findAll();
        assertThat(managers).hasSize(databaseSizeBeforeCreate + 1);
        Manager testManager = managers.get(managers.size() - 1);
        assertThat(testManager.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testManager.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
    }

    @Test
    public void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = managerRepository.findAll().size();
        // set the field null
        manager.setFirstName(null);

        // Create the Manager, which fails.
        ManagerDTO managerDTO = managerMapper.managerToManagerDTO(manager);

        restManagerMockMvc.perform(post("/api/managers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(managerDTO)))
                .andExpect(status().isBadRequest());

        List<Manager> managers = managerRepository.findAll();
        assertThat(managers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = managerRepository.findAll().size();
        // set the field null
        manager.setLastName(null);

        // Create the Manager, which fails.
        ManagerDTO managerDTO = managerMapper.managerToManagerDTO(manager);

        restManagerMockMvc.perform(post("/api/managers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(managerDTO)))
                .andExpect(status().isBadRequest());

        List<Manager> managers = managerRepository.findAll();
        assertThat(managers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllManagers() throws Exception {
        // Initialize the database
        managerRepository.save(manager);

        // Get all the managers
        restManagerMockMvc.perform(get("/api/managers?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(manager.getId())))
                .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
                .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)));
    }

    @Test
    public void getManager() throws Exception {
        // Initialize the database
        managerRepository.save(manager);

        // Get the manager
        restManagerMockMvc.perform(get("/api/managers/{id}", manager.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(manager.getId()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME));
    }

    @Test
    public void getNonExistingManager() throws Exception {
        // Get the manager
        restManagerMockMvc.perform(get("/api/managers/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateManager() throws Exception {
        // Initialize the database
        managerRepository.save(manager);

		int databaseSizeBeforeUpdate = managerRepository.findAll().size();

        // Update the manager
        manager.setFirstName(UPDATED_FIRST_NAME);
        manager.setLastName(UPDATED_LAST_NAME);
        ManagerDTO managerDTO = managerMapper.managerToManagerDTO(manager);

        restManagerMockMvc.perform(put("/api/managers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(managerDTO)))
                .andExpect(status().isOk());

        // Validate the Manager in the database
        List<Manager> managers = managerRepository.findAll();
        assertThat(managers).hasSize(databaseSizeBeforeUpdate);
        Manager testManager = managers.get(managers.size() - 1);
        assertThat(testManager.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testManager.getLastName()).isEqualTo(UPDATED_LAST_NAME);
    }

    @Test
    public void deleteManager() throws Exception {
        // Initialize the database
        managerRepository.save(manager);

		int databaseSizeBeforeDelete = managerRepository.findAll().size();

        // Get the manager
        restManagerMockMvc.perform(delete("/api/managers/{id}", manager.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Manager> managers = managerRepository.findAll();
        assertThat(managers).hasSize(databaseSizeBeforeDelete - 1);
    }
}
