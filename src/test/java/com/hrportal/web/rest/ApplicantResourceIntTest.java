package com.hrportal.web.rest;

import com.hrportal.Application;
import com.hrportal.domain.*;
import com.hrportal.domain.enumeration.Suffix;
import com.hrportal.domain.enumeration.Title;
import com.hrportal.repository.ApplicantRepository;
import com.hrportal.service.ApplicantService;
import com.hrportal.web.rest.dto.ApplicantDTO;
import com.hrportal.web.rest.mapper.ApplicantMapper;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ApplicantResource REST controller.
 *
 * @see ApplicantResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ApplicantResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE;


    private static final Title DEFAULT_TITLE = Title.Dr;
    private static final Title UPDATED_TITLE = Title.Blank;
    private static final String DEFAULT_FIRST_NAME = "AAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBB";
    private static final String DEFAULT_LAST_NAME = "AAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBB";
    private static final String DEFAULT_MIDDLE_NAME = "AAAAA";
    private static final String UPDATED_MIDDLE_NAME = "BBBBB";

    private static final LocalDate DEFAULT_OFFER_DATE = LocalDate.of(1984,11,23);
    private static final LocalDate UPDATED_OFFER_DATE = LocalDate.of(1984,11,23);
    private static final String DEFAULT_OFFER_DATE_STR = dateTimeFormatter.format(DEFAULT_OFFER_DATE);

    private static final Suffix DEFAULT_SUFFIX = Suffix.Jr;
    private static final Suffix UPDATED_SUFFIX = Suffix.Sr;

    @Inject
    private ApplicantRepository applicantRepository;

    @Inject
    private ApplicantMapper applicantMapper;

    @Inject
    private ApplicantService applicantService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restApplicantMockMvc;

    private Applicant applicant;

    private ContactInfo contactInfo;

    private Address address;

    private Email email;

    private PhoneNumber phoneNumber;

    private Contract contract;

    private Project project;

    private Manager manager;

    private Position position;


    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ApplicantResource applicantResource = new ApplicantResource();
        ReflectionTestUtils.setField(applicantResource, "applicantService", applicantService);
        ReflectionTestUtils.setField(applicantResource, "applicantMapper", applicantMapper);
        this.restApplicantMockMvc = MockMvcBuilders.standaloneSetup(applicantResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        applicantRepository.deleteAll();

        address = new Address();
        address.setCity("AAAA");
        address.setCounty("BBBB");
        address.setState("CCCC");
        address.setZip("12345");
        address.setStreet("EEEE");

        email = new Email();
        email.setEmail("AAAA@EMAIL.com");

        phoneNumber = new PhoneNumber();
        phoneNumber.setPhoneNumber("1234567890");

        contactInfo = new ContactInfo();
        contactInfo.setAddress(address);
        contactInfo.setEmail(email);
        contactInfo.setPhoneNumber(phoneNumber);

        contract = new Contract();
        contract.setId("1");
        contract.setContractLaborCategory("Nuclear Safety Inspector");
        contract.setContractName("Nuclear Power Regulation");
        contract.setContractNumber("12345");

        project = new Project();
        project.setId("1");
        project.setProjectName("Don't get hurt");
        project.setProjectTitle("This can hurt");

        manager = new Manager();
        manager.setId("1");
        manager.setFirstName("Weyland");
        manager.setLastName("Smithers");

        position = new Position();
        position.setId("1");
        position.setLaborCategory("Foo");
        position.setPositionName("bar");
        position.setSalary(999_999);


        applicant = new Applicant();
        applicant.setTitle(DEFAULT_TITLE);
        applicant.setFirstName(DEFAULT_FIRST_NAME);
        applicant.setLastName(DEFAULT_LAST_NAME);
        applicant.setMiddleName(DEFAULT_MIDDLE_NAME);
        applicant.setOfferDate(DEFAULT_OFFER_DATE);
        applicant.setSuffix(DEFAULT_SUFFIX);
        applicant.setContactInfo(contactInfo);
        applicant.setContract(contract);
        applicant.setProject(project);
        applicant.setManager(manager);
        applicant.setPosition(position);
    }

    @Test
    public void createApplicant() throws Exception {
        int databaseSizeBeforeCreate = applicantRepository.findAll().size();

        // Create the Applicant
        ApplicantDTO applicantDTO = applicantMapper.applicantToApplicantDTO(applicant);

        restApplicantMockMvc.perform(post("/api/applicants")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(applicantDTO)))
                .andExpect(status().isCreated());

        // Validate the Applicant in the database
        List<Applicant> applicants = applicantRepository.findAll();
        assertThat(applicants).hasSize(databaseSizeBeforeCreate + 1);
        Applicant testApplicant = applicants.get(applicants.size() - 1);
        assertThat(testApplicant.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testApplicant.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testApplicant.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testApplicant.getMiddleName()).isEqualTo(DEFAULT_MIDDLE_NAME);
        assertThat(testApplicant.getOfferDate()).isEqualTo(DEFAULT_OFFER_DATE);
        assertThat(testApplicant.getSuffix()).isEqualTo(DEFAULT_SUFFIX);
        assertThat(testApplicant.getContract()).isEqualTo(contract);
        assertThat(testApplicant.getPosition()).isEqualTo(position);
        assertThat(testApplicant.getProject()).isEqualTo(project);
        assertThat(testApplicant.getManager()).isEqualTo(manager);
    }

    @Test
    public void checkOfferDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicantRepository.findAll().size();
        // set the field null
        applicant.setOfferDate(null);

        // Create the Applicant, which fails.
        ApplicantDTO applicantDTO = applicantMapper.applicantToApplicantDTO(applicant);

        restApplicantMockMvc.perform(post("/api/applicants")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(applicantDTO)))
                .andExpect(status().isBadRequest());

        List<Applicant> applicants = applicantRepository.findAll();
        assertThat(applicants).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllApplicants() throws Exception {
        // Initialize the database
        applicantRepository.save(applicant);

        // Get all the applicants
        restApplicantMockMvc.perform(get("/api/applicants?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(applicant.getId())))
                .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.value())))
                .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
                .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
                .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME)))
                .andExpect(jsonPath("$.[*].offerDate").value(hasItem(DEFAULT_OFFER_DATE_STR)))
                .andExpect(jsonPath("$.[*].suffix").value(hasItem(DEFAULT_SUFFIX.value())));
    }

    @Test
    public void getApplicant() throws Exception {
        // Initialize the database
        applicantRepository.save(applicant);

        // Get the applicant
        restApplicantMockMvc.perform(get("/api/applicants/{id}", applicant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(applicant.getId()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.value()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.middleName").value(DEFAULT_MIDDLE_NAME))
            .andExpect(jsonPath("$.offerDate").value(DEFAULT_OFFER_DATE_STR))
            .andExpect(jsonPath("$.suffix").value(DEFAULT_SUFFIX.value()));
    }

    @Test
    public void getNonExistingApplicant() throws Exception {
        // Get the applicant
        restApplicantMockMvc.perform(get("/api/applicants/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateApplicant() throws Exception {
        // Initialize the database
        applicantRepository.save(applicant);

		int databaseSizeBeforeUpdate = applicantRepository.findAll().size();

        // Update the applicant
        applicant.setTitle(UPDATED_TITLE);
        applicant.setFirstName(UPDATED_FIRST_NAME);
        applicant.setLastName(UPDATED_LAST_NAME);
        applicant.setMiddleName(UPDATED_MIDDLE_NAME);
        applicant.setOfferDate(UPDATED_OFFER_DATE);
        applicant.setSuffix(UPDATED_SUFFIX);
        ApplicantDTO applicantDTO = applicantMapper.applicantToApplicantDTO(applicant);

        restApplicantMockMvc.perform(put("/api/applicants")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(applicantDTO)))
                .andExpect(status().isOk());

        // Validate the Applicant in the database
        List<Applicant> applicants = applicantRepository.findAll();
        assertThat(applicants).hasSize(databaseSizeBeforeUpdate);
        Applicant testApplicant = applicants.get(applicants.size() - 1);
        assertThat(testApplicant.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testApplicant.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testApplicant.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testApplicant.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testApplicant.getOfferDate()).isEqualTo(UPDATED_OFFER_DATE);
        assertThat(testApplicant.getSuffix()).isEqualTo(UPDATED_SUFFIX);
    }

    @Test
    public void deleteApplicant() throws Exception {
        // Initialize the database
        applicantRepository.save(applicant);

		int databaseSizeBeforeDelete = applicantRepository.findAll().size();

        // Get the applicant
        restApplicantMockMvc.perform(delete("/api/applicants/{id}", applicant.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Applicant> applicants = applicantRepository.findAll();
        assertThat(applicants).hasSize(databaseSizeBeforeDelete - 1);
    }
}
