package com.gralll.bankaudit.web.rest;

import com.gralll.bankaudit.BankAuditApp;

import com.gralll.bankaudit.domain.RateMatrix;
import com.gralll.bankaudit.repository.RateMatrixRepository;
import com.gralll.bankaudit.service.RateMatrixService;
import com.gralll.bankaudit.repository.search.RateMatrixSearchRepository;

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
 * Test class for the RateMatrixResource REST controller.
 *
 * @see RateMatrixResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BankAuditApp.class)
public class RateMatrixResourceIntTest {

    private static final String DEFAULT_INDEX_RATE = "AAAAA";
    private static final String UPDATED_INDEX_RATE = "BBBBB";

    private static final Double DEFAULT_RATE = 1D;
    private static final Double UPDATED_RATE = 2D;

    private static final Integer DEFAULT_ZERO = 1;
    private static final Integer UPDATED_ZERO = 2;

    @Inject
    private RateMatrixRepository rateMatrixRepository;

    @Inject
    private RateMatrixService rateMatrixService;

    @Inject
    private RateMatrixSearchRepository rateMatrixSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restRateMatrixMockMvc;

    private RateMatrix rateMatrix;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RateMatrixResource rateMatrixResource = new RateMatrixResource();
        ReflectionTestUtils.setField(rateMatrixResource, "rateMatrixService", rateMatrixService);
        this.restRateMatrixMockMvc = MockMvcBuilders.standaloneSetup(rateMatrixResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RateMatrix createEntity(EntityManager em) {
        RateMatrix rateMatrix = new RateMatrix()
                .indexRate(DEFAULT_INDEX_RATE)
                .rate(DEFAULT_RATE)
                .zero(DEFAULT_ZERO);
        return rateMatrix;
    }

    @Before
    public void initTest() {
        rateMatrixSearchRepository.deleteAll();
        rateMatrix = createEntity(em);
    }

    @Test
    @Transactional
    public void createRateMatrix() throws Exception {
        int databaseSizeBeforeCreate = rateMatrixRepository.findAll().size();

        // Create the RateMatrix

        restRateMatrixMockMvc.perform(post("/api/rate-matrices")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(rateMatrix)))
                .andExpect(status().isCreated());

        // Validate the RateMatrix in the database
        List<RateMatrix> rateMatrices = rateMatrixRepository.findAll();
        assertThat(rateMatrices).hasSize(databaseSizeBeforeCreate + 1);
        RateMatrix testRateMatrix = rateMatrices.get(rateMatrices.size() - 1);
        assertThat(testRateMatrix.getIndexRate()).isEqualTo(DEFAULT_INDEX_RATE);
        assertThat(testRateMatrix.getRate()).isEqualTo(DEFAULT_RATE);
        assertThat(testRateMatrix.getZero()).isEqualTo(DEFAULT_ZERO);

        // Validate the RateMatrix in ElasticSearch
        RateMatrix rateMatrixEs = rateMatrixSearchRepository.findOne(testRateMatrix.getId());
        assertThat(rateMatrixEs).isEqualToComparingFieldByField(testRateMatrix);
    }

    @Test
    @Transactional
    public void getAllRateMatrices() throws Exception {
        // Initialize the database
        rateMatrixRepository.saveAndFlush(rateMatrix);

        // Get all the rateMatrices
        restRateMatrixMockMvc.perform(get("/api/rate-matrices?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(rateMatrix.getId().intValue())))
                .andExpect(jsonPath("$.[*].indexRate").value(hasItem(DEFAULT_INDEX_RATE.toString())))
                .andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE.doubleValue())))
                .andExpect(jsonPath("$.[*].zero").value(hasItem(DEFAULT_ZERO)));
    }

    @Test
    @Transactional
    public void getRateMatrix() throws Exception {
        // Initialize the database
        rateMatrixRepository.saveAndFlush(rateMatrix);

        // Get the rateMatrix
        restRateMatrixMockMvc.perform(get("/api/rate-matrices/{id}", rateMatrix.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rateMatrix.getId().intValue()))
            .andExpect(jsonPath("$.indexRate").value(DEFAULT_INDEX_RATE.toString()))
            .andExpect(jsonPath("$.rate").value(DEFAULT_RATE.doubleValue()))
            .andExpect(jsonPath("$.zero").value(DEFAULT_ZERO));
    }

    @Test
    @Transactional
    public void getNonExistingRateMatrix() throws Exception {
        // Get the rateMatrix
        restRateMatrixMockMvc.perform(get("/api/rate-matrices/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRateMatrix() throws Exception {
        // Initialize the database
        rateMatrixService.save(rateMatrix);

        int databaseSizeBeforeUpdate = rateMatrixRepository.findAll().size();

        // Update the rateMatrix
        RateMatrix updatedRateMatrix = rateMatrixRepository.findOne(rateMatrix.getId());
        updatedRateMatrix
                .indexRate(UPDATED_INDEX_RATE)
                .rate(UPDATED_RATE)
                .zero(UPDATED_ZERO);

        restRateMatrixMockMvc.perform(put("/api/rate-matrices")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedRateMatrix)))
                .andExpect(status().isOk());

        // Validate the RateMatrix in the database
        List<RateMatrix> rateMatrices = rateMatrixRepository.findAll();
        assertThat(rateMatrices).hasSize(databaseSizeBeforeUpdate);
        RateMatrix testRateMatrix = rateMatrices.get(rateMatrices.size() - 1);
        assertThat(testRateMatrix.getIndexRate()).isEqualTo(UPDATED_INDEX_RATE);
        assertThat(testRateMatrix.getRate()).isEqualTo(UPDATED_RATE);
        assertThat(testRateMatrix.getZero()).isEqualTo(UPDATED_ZERO);

        // Validate the RateMatrix in ElasticSearch
        RateMatrix rateMatrixEs = rateMatrixSearchRepository.findOne(testRateMatrix.getId());
        assertThat(rateMatrixEs).isEqualToComparingFieldByField(testRateMatrix);
    }

    @Test
    @Transactional
    public void deleteRateMatrix() throws Exception {
        // Initialize the database
        rateMatrixService.save(rateMatrix);

        int databaseSizeBeforeDelete = rateMatrixRepository.findAll().size();

        // Get the rateMatrix
        restRateMatrixMockMvc.perform(delete("/api/rate-matrices/{id}", rateMatrix.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean rateMatrixExistsInEs = rateMatrixSearchRepository.exists(rateMatrix.getId());
        assertThat(rateMatrixExistsInEs).isFalse();

        // Validate the database is empty
        List<RateMatrix> rateMatrices = rateMatrixRepository.findAll();
        assertThat(rateMatrices).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchRateMatrix() throws Exception {
        // Initialize the database
        rateMatrixService.save(rateMatrix);

        // Search the rateMatrix
        restRateMatrixMockMvc.perform(get("/api/_search/rate-matrices?query=id:" + rateMatrix.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rateMatrix.getId().intValue())))
            .andExpect(jsonPath("$.[*].indexRate").value(hasItem(DEFAULT_INDEX_RATE.toString())))
            .andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].zero").value(hasItem(DEFAULT_ZERO)));
    }
}
