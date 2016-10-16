package com.gralll.bankaudit.web.rest;

import com.gralll.bankaudit.BankAuditApp;

import com.gralll.bankaudit.domain.RateResult;
import com.gralll.bankaudit.repository.RateResultRepository;
import com.gralll.bankaudit.service.RateResultService;
import com.gralll.bankaudit.repository.search.RateResultSearchRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the RateResultResource REST controller.
 *
 * @see RateResultResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BankAuditApp.class)
public class RateResultResourceIntTest {


    @Inject
    private RateResultRepository rateResultRepository;

    @Inject
    private RateResultService rateResultService;

    @Inject
    private RateResultSearchRepository rateResultSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restRateResultMockMvc;

    private RateResult rateResult;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RateResultResource rateResultResource = new RateResultResource();
        ReflectionTestUtils.setField(rateResultResource, "rateResultService", rateResultService);
        this.restRateResultMockMvc = MockMvcBuilders.standaloneSetup(rateResultResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RateResult createEntity(EntityManager em) {
        RateResult rateResult = new RateResult();
        return rateResult;
    }

    @Before
    public void initTest() {
        rateResultSearchRepository.deleteAll();
        rateResult = createEntity(em);
    }

    @Test
    @Transactional
    public void createRateResult() throws Exception {
        int databaseSizeBeforeCreate = rateResultRepository.findAll().size();

        // Create the RateResult

        restRateResultMockMvc.perform(post("/api/rate-results")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(rateResult)))
                .andExpect(status().isCreated());

        // Validate the RateResult in the database
        List<RateResult> rateResults = rateResultRepository.findAll();
        assertThat(rateResults).hasSize(databaseSizeBeforeCreate + 1);
        RateResult testRateResult = rateResults.get(rateResults.size() - 1);

        // Validate the RateResult in ElasticSearch
        RateResult rateResultEs = rateResultSearchRepository.findOne(testRateResult.getId());
        assertThat(rateResultEs).isEqualToComparingFieldByField(testRateResult);
    }

    @Test
    @Transactional
    public void getAllRateResults() throws Exception {
        // Initialize the database
        rateResultRepository.saveAndFlush(rateResult);

        // Get all the rateResults
        restRateResultMockMvc.perform(get("/api/rate-results?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(rateResult.getId().intValue())));
    }

    @Test
    @Transactional
    public void getRateResult() throws Exception {
        // Initialize the database
        rateResultRepository.saveAndFlush(rateResult);

        // Get the rateResult
        restRateResultMockMvc.perform(get("/api/rate-results/{id}", rateResult.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rateResult.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingRateResult() throws Exception {
        // Get the rateResult
        restRateResultMockMvc.perform(get("/api/rate-results/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRateResult() throws Exception {
        // Initialize the database
        rateResultService.save(rateResult);

        int databaseSizeBeforeUpdate = rateResultRepository.findAll().size();

        // Update the rateResult
        RateResult updatedRateResult = rateResultRepository.findOne(rateResult.getId());

        restRateResultMockMvc.perform(put("/api/rate-results")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedRateResult)))
                .andExpect(status().isOk());

        // Validate the RateResult in the database
        List<RateResult> rateResults = rateResultRepository.findAll();
        assertThat(rateResults).hasSize(databaseSizeBeforeUpdate);
        RateResult testRateResult = rateResults.get(rateResults.size() - 1);

        // Validate the RateResult in ElasticSearch
        RateResult rateResultEs = rateResultSearchRepository.findOne(testRateResult.getId());
        assertThat(rateResultEs).isEqualToComparingFieldByField(testRateResult);
    }

    @Test
    @Transactional
    public void deleteRateResult() throws Exception {
        // Initialize the database
        rateResultService.save(rateResult);

        int databaseSizeBeforeDelete = rateResultRepository.findAll().size();

        // Get the rateResult
        restRateResultMockMvc.perform(delete("/api/rate-results/{id}", rateResult.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean rateResultExistsInEs = rateResultSearchRepository.exists(rateResult.getId());
        assertThat(rateResultExistsInEs).isFalse();

        // Validate the database is empty
        List<RateResult> rateResults = rateResultRepository.findAll();
        assertThat(rateResults).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchRateResult() throws Exception {
        // Initialize the database
        rateResultService.save(rateResult);

        // Search the rateResult
        restRateResultMockMvc.perform(get("/api/_search/rate-results?query=id:" + rateResult.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rateResult.getId().intValue())));
    }
}
