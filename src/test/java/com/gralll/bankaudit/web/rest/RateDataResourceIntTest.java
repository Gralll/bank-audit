package com.gralll.bankaudit.web.rest;

import com.gralll.bankaudit.BankAuditApp;

import com.gralll.bankaudit.domain.RateData;
import com.gralll.bankaudit.repository.RateDataRepository;
import com.gralll.bankaudit.service.RateDataService;
import com.gralll.bankaudit.repository.search.RateDataSearchRepository;

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

import com.gralll.bankaudit.domain.enumeration.AuditProgress;
/**
 * Test class for the RateDataResource REST controller.
 *
 * @see RateDataResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BankAuditApp.class)
public class RateDataResourceIntTest {


    private static final AuditProgress DEFAULT_PROGRESS = AuditProgress.CREATED;
    private static final AuditProgress UPDATED_PROGRESS = AuditProgress.STARTED;

    @Inject
    private RateDataRepository rateDataRepository;

    @Inject
    private RateDataService rateDataService;

    @Inject
    private RateDataSearchRepository rateDataSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restRateDataMockMvc;

    private RateData rateData;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RateDataResource rateDataResource = new RateDataResource();
        ReflectionTestUtils.setField(rateDataResource, "rateDataService", rateDataService);
        this.restRateDataMockMvc = MockMvcBuilders.standaloneSetup(rateDataResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RateData createEntity(EntityManager em) {
        RateData rateData = new RateData()
                .progress(DEFAULT_PROGRESS);
        return rateData;
    }

    @Before
    public void initTest() {
        rateDataSearchRepository.deleteAll();
        rateData = createEntity(em);
    }

    @Test
    @Transactional
    public void createRateData() throws Exception {
        int databaseSizeBeforeCreate = rateDataRepository.findAll().size();

        // Create the RateData

        restRateDataMockMvc.perform(post("/api/rate-data")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(rateData)))
                .andExpect(status().isCreated());

        // Validate the RateData in the database
        List<RateData> rateData = rateDataRepository.findAll();
        assertThat(rateData).hasSize(databaseSizeBeforeCreate + 1);
        RateData testRateData = rateData.get(rateData.size() - 1);
        assertThat(testRateData.getProgress()).isEqualTo(DEFAULT_PROGRESS);

        // Validate the RateData in ElasticSearch
        RateData rateDataEs = rateDataSearchRepository.findOne(testRateData.getId());
        assertThat(rateDataEs).isEqualToComparingFieldByField(testRateData);
    }

    @Test
    @Transactional
    public void getAllRateData() throws Exception {
        // Initialize the database
        rateDataRepository.saveAndFlush(rateData);

        // Get all the rateData
        restRateDataMockMvc.perform(get("/api/rate-data?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(rateData.getId().intValue())))
                .andExpect(jsonPath("$.[*].progress").value(hasItem(DEFAULT_PROGRESS.toString())));
    }

    @Test
    @Transactional
    public void getRateData() throws Exception {
        // Initialize the database
        rateDataRepository.saveAndFlush(rateData);

        // Get the rateData
        restRateDataMockMvc.perform(get("/api/rate-data/{id}", rateData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rateData.getId().intValue()))
            .andExpect(jsonPath("$.progress").value(DEFAULT_PROGRESS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRateData() throws Exception {
        // Get the rateData
        restRateDataMockMvc.perform(get("/api/rate-data/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRateData() throws Exception {
        // Initialize the database
        rateDataService.save(rateData);

        int databaseSizeBeforeUpdate = rateDataRepository.findAll().size();

        // Update the rateData
        RateData updatedRateData = rateDataRepository.findOne(rateData.getId());
        updatedRateData
                .progress(UPDATED_PROGRESS);

        restRateDataMockMvc.perform(put("/api/rate-data")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedRateData)))
                .andExpect(status().isOk());

        // Validate the RateData in the database
        List<RateData> rateData = rateDataRepository.findAll();
        assertThat(rateData).hasSize(databaseSizeBeforeUpdate);
        RateData testRateData = rateData.get(rateData.size() - 1);
        assertThat(testRateData.getProgress()).isEqualTo(UPDATED_PROGRESS);

        // Validate the RateData in ElasticSearch
        RateData rateDataEs = rateDataSearchRepository.findOne(testRateData.getId());
        assertThat(rateDataEs).isEqualToComparingFieldByField(testRateData);
    }

    @Test
    @Transactional
    public void deleteRateData() throws Exception {
        // Initialize the database
        rateDataService.save(rateData);

        int databaseSizeBeforeDelete = rateDataRepository.findAll().size();

        // Get the rateData
        restRateDataMockMvc.perform(delete("/api/rate-data/{id}", rateData.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean rateDataExistsInEs = rateDataSearchRepository.exists(rateData.getId());
        assertThat(rateDataExistsInEs).isFalse();

        // Validate the database is empty
        List<RateData> rateData = rateDataRepository.findAll();
        assertThat(rateData).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchRateData() throws Exception {
        // Initialize the database
        rateDataService.save(rateData);

        // Search the rateData
        restRateDataMockMvc.perform(get("/api/_search/rate-data?query=id:" + rateData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rateData.getId().intValue())))
            .andExpect(jsonPath("$.[*].progress").value(hasItem(DEFAULT_PROGRESS.toString())));
    }
}
