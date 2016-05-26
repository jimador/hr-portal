package com.hrportal.web.rest;

import com.hrportal.Application;
import com.hrportal.domain.Contract;
import com.hrportal.repository.ContractRepository;
import com.hrportal.service.ContractService;
import com.hrportal.web.rest.dto.ContractDTO;
import com.hrportal.web.rest.mapper.ContractMapper;
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
 * Test class for the ContractResource REST controller.
 *
 * @see ContractResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ContractResourceIntTest {

    private static final String DEFAULT_CONTRACT_NUMBER = "AAAAA";
    private static final String UPDATED_CONTRACT_NUMBER = "BBBBB";
    private static final String DEFAULT_CONTRACT_LABOR_CATEGORY = "AAAAA";
    private static final String UPDATED_CONTRACT_LABOR_CATEGORY = "BBBBB";
    private static final String DEFAULT_CONTRACT_NAME = "CCCCC";
    private static final String UPDATED_CONTRACT_NAME = "DDDDD";

    @Inject
    private ContractRepository contractRepository;

    @Inject
    private ContractMapper contractMapper;

    @Inject
    private ContractService contractService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restContractMockMvc;

    private Contract contract;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ContractResource contractResource = new ContractResource();
        ReflectionTestUtils.setField(contractResource, "contractService", contractService);
        ReflectionTestUtils.setField(contractResource, "contractMapper", contractMapper);
        this.restContractMockMvc = MockMvcBuilders.standaloneSetup(contractResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        contractRepository.deleteAll();
        contract = new Contract();
        contract.setContractName(DEFAULT_CONTRACT_NAME);
        contract.setContractNumber(DEFAULT_CONTRACT_NUMBER);
        contract.setContractLaborCategory(DEFAULT_CONTRACT_LABOR_CATEGORY);
    }

    @Test
    public void createContract() throws Exception {
        int databaseSizeBeforeCreate = contractRepository.findAll().size();

        // Create the Contract
        ContractDTO contractDTO = contractMapper.contractToContractDTO(contract);

        restContractMockMvc.perform(post("/api/contracts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(contractDTO)))
                .andExpect(status().isCreated());

        // Validate the Contract in the database
        List<Contract> contracts = contractRepository.findAll();
        assertThat(contracts).hasSize(databaseSizeBeforeCreate + 1);
        Contract testContract = contracts.get(contracts.size() - 1);
        assertThat(testContract.getContractName()).isEqualTo(DEFAULT_CONTRACT_NAME);
        assertThat(testContract.getContractNumber()).isEqualTo(DEFAULT_CONTRACT_NUMBER);
        assertThat(testContract.getContractLaborCategory()).isEqualTo(DEFAULT_CONTRACT_LABOR_CATEGORY);
    }

    @Test
    public void getAllContracts() throws Exception {
        // Initialize the database
        contractRepository.save(contract);

        // Get all the contracts
        restContractMockMvc.perform(get("/api/contracts?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(contract.getId())))
                .andExpect(jsonPath("$.[*].contractName").value(hasItem(DEFAULT_CONTRACT_NAME)))
                .andExpect(jsonPath("$.[*].contractNumber").value(hasItem(DEFAULT_CONTRACT_NUMBER)))
                .andExpect(jsonPath("$.[*].contractLaborCategory").value(hasItem(DEFAULT_CONTRACT_LABOR_CATEGORY)));
    }

    @Test
    public void getContract() throws Exception {
        // Initialize the database
        contractRepository.save(contract);

        // Get the contract
        restContractMockMvc.perform(get("/api/contracts/{id}", contract.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(contract.getId()))
            .andExpect(jsonPath("$.contractName").value(DEFAULT_CONTRACT_NAME))
            .andExpect(jsonPath("$.contractNumber").value(DEFAULT_CONTRACT_NUMBER))
            .andExpect(jsonPath("$.contractLaborCategory").value(DEFAULT_CONTRACT_LABOR_CATEGORY));
    }

    @Test
    public void getNonExistingContract() throws Exception {
        // Get the contract
        restContractMockMvc.perform(get("/api/contracts/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateContract() throws Exception {
        // Initialize the database
        contractRepository.save(contract);

		int databaseSizeBeforeUpdate = contractRepository.findAll().size();

        // Update the contract
        contract.setContractName(UPDATED_CONTRACT_NAME);
        contract.setContractNumber(UPDATED_CONTRACT_NUMBER);
        contract.setContractLaborCategory(UPDATED_CONTRACT_LABOR_CATEGORY);
        ContractDTO contractDTO = contractMapper.contractToContractDTO(contract);

        restContractMockMvc.perform(put("/api/contracts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(contractDTO)))
                .andExpect(status().isOk());

        // Validate the Contract in the database
        List<Contract> contracts = contractRepository.findAll();
        assertThat(contracts).hasSize(databaseSizeBeforeUpdate);
        Contract testContract = contracts.get(contracts.size() - 1);
        assertThat(testContract.getContractName()).isEqualTo(UPDATED_CONTRACT_NAME);
        assertThat(testContract.getContractNumber()).isEqualTo(UPDATED_CONTRACT_NUMBER);
        assertThat(testContract.getContractLaborCategory()).isEqualTo(UPDATED_CONTRACT_LABOR_CATEGORY);
    }

    @Test
    public void deleteContract() throws Exception {
        // Initialize the database
        contractRepository.save(contract);

		int databaseSizeBeforeDelete = contractRepository.findAll().size();

        // Get the contract
        restContractMockMvc.perform(delete("/api/contracts/{id}", contract.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Contract> contracts = contractRepository.findAll();
        assertThat(contracts).hasSize(databaseSizeBeforeDelete - 1);
    }
}
