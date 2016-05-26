package com.hrportal.web.rest;

import com.hrportal.Application;
import com.hrportal.domain.Project;
import com.hrportal.repository.ProjectRepository;
import com.hrportal.service.ProjectService;
import com.hrportal.web.rest.dto.ProjectDTO;
import com.hrportal.web.rest.mapper.ProjectMapper;
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
 * Test class for the ProjectResource REST controller.
 *
 * @see ProjectResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ProjectResourceIntTest {

    private static final String DEFAULT_PROJECT_TITLE = "AAAAA";
    private static final String UPDATED_PROJECT_TITLE = "BBBBB";
    private static final String DEFAULT_PROJECT_NAME = "AAAAA";
    private static final String UPDATED_PROJECT_NAME = "BBBBB";

    @Inject
    private ProjectRepository projectRepository;

    @Inject
    private ProjectMapper projectMapper;

    @Inject
    private ProjectService projectService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restProjectMockMvc;

    private Project project;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProjectResource projectResource = new ProjectResource();
        ReflectionTestUtils.setField(projectResource, "projectService", projectService);
        ReflectionTestUtils.setField(projectResource, "projectMapper", projectMapper);
        this.restProjectMockMvc = MockMvcBuilders.standaloneSetup(projectResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        projectRepository.deleteAll();
        project = new Project();
        project.setProjectTitle(DEFAULT_PROJECT_TITLE);
        project.setProjectName(DEFAULT_PROJECT_NAME);
    }

    @Test
    public void createProject() throws Exception {
        int databaseSizeBeforeCreate = projectRepository.findAll().size();

        // Create the Project
        ProjectDTO projectDTO = projectMapper.projectToProjectDTO(project);

        restProjectMockMvc.perform(post("/api/projects")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(projectDTO)))
                .andExpect(status().isCreated());

        // Validate the Project in the database
        List<Project> projects = projectRepository.findAll();
        assertThat(projects).hasSize(databaseSizeBeforeCreate + 1);
        Project testProject = projects.get(projects.size() - 1);
        assertThat(testProject.getProjectTitle()).isEqualTo(DEFAULT_PROJECT_TITLE);
        assertThat(testProject.getProjectName()).isEqualTo(DEFAULT_PROJECT_NAME);
    }

    @Test
    public void checkProjectTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectRepository.findAll().size();
        // set the field null
        project.setProjectTitle(null);

        // Create the Project, which fails.
        ProjectDTO projectDTO = projectMapper.projectToProjectDTO(project);

        restProjectMockMvc.perform(post("/api/projects")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(projectDTO)))
                .andExpect(status().isBadRequest());

        List<Project> projects = projectRepository.findAll();
        assertThat(projects).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkProjectNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectRepository.findAll().size();
        // set the field null
        project.setProjectName(null);

        // Create the Project, which fails.
        ProjectDTO projectDTO = projectMapper.projectToProjectDTO(project);

        restProjectMockMvc.perform(post("/api/projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectDTO)))
            .andExpect(status().isBadRequest());

        List<Project> projects = projectRepository.findAll();
        assertThat(projects).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllProjects() throws Exception {
        // Initialize the database
        projectRepository.save(project);

        // Get all the projects
        restProjectMockMvc.perform(get("/api/projects?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(project.getId())))
                .andExpect(jsonPath("$.[*].projectTitle").value(hasItem(DEFAULT_PROJECT_TITLE)));
    }

    @Test
    public void getProject() throws Exception {
        // Initialize the database
        projectRepository.save(project);

        // Get the project
        restProjectMockMvc.perform(get("/api/projects/{id}", project.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(project.getId()))
            .andExpect(jsonPath("$.projectTitle").value(DEFAULT_PROJECT_TITLE));
    }

    @Test
    public void getNonExistingProject() throws Exception {
        // Get the project
        restProjectMockMvc.perform(get("/api/projects/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateProject() throws Exception {
        // Initialize the database
        projectRepository.save(project);

		int databaseSizeBeforeUpdate = projectRepository.findAll().size();

        // Update the project
        project.setProjectTitle(UPDATED_PROJECT_TITLE);
        ProjectDTO projectDTO = projectMapper.projectToProjectDTO(project);

        restProjectMockMvc.perform(put("/api/projects")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(projectDTO)))
                .andExpect(status().isOk());

        // Validate the Project in the database
        List<Project> projects = projectRepository.findAll();
        assertThat(projects).hasSize(databaseSizeBeforeUpdate);
        Project testProject = projects.get(projects.size() - 1);
        assertThat(testProject.getProjectTitle()).isEqualTo(UPDATED_PROJECT_TITLE);
    }

    @Test
    public void deleteProject() throws Exception {
        // Initialize the database
        projectRepository.save(project);

		int databaseSizeBeforeDelete = projectRepository.findAll().size();

        // Get the project
        restProjectMockMvc.perform(delete("/api/projects/{id}", project.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Project> projects = projectRepository.findAll();
        assertThat(projects).hasSize(databaseSizeBeforeDelete - 1);
    }
}
