package com.gralll.bankaudit.web.rest;

import com.gralll.bankaudit.BankAuditApp;

import com.gralll.bankaudit.domain.FinalRate;
import com.gralll.bankaudit.repository.FinalRateRepository;
import com.gralll.bankaudit.service.FinalRateService;
import com.gralll.bankaudit.repository.search.FinalRateSearchRepository;

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
 * Test class for the FinalRateResource REST controller.
 *
 * @see FinalRateResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BankAuditApp.class)
public class FinalRateResourceIntTest {


    private static final Double DEFAULT_EVOOPD = 1D;
    private static final Double UPDATED_EVOOPD = 2D;

    private static final Double DEFAULT_EV_1_OZPD = 1D;
    private static final Double UPDATED_EV_1_OZPD = 2D;

    private static final Double DEFAULT_EVMB = 1D;
    private static final Double UPDATED_EVMB = 2D;

    private static final Double DEFAULT_R = 1D;
    private static final Double UPDATED_R = 2D;

    @Inject
    private FinalRateRepository finalRateRepository;

    @Inject
    private FinalRateService finalRateService;

    @Inject
    private FinalRateSearchRepository finalRateSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restFinalRateMockMvc;

    private FinalRate finalRate;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FinalRateResource finalRateResource = new FinalRateResource();
        ReflectionTestUtils.setField(finalRateResource, "finalRateService", finalRateService);
        this.restFinalRateMockMvc = MockMvcBuilders.standaloneSetup(finalRateResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FinalRate createEntity(EntityManager em) {
        FinalRate finalRate = new FinalRate()
                .evoopd(DEFAULT_EVOOPD)
                .ev1Ozpd(DEFAULT_EV_1_OZPD)
                .evmb(DEFAULT_EVMB)
                .r(DEFAULT_R);
        return finalRate;
    }

    @Before
    public void initTest() {
        finalRateSearchRepository.deleteAll();
        finalRate = createEntity(em);
    }

    @Test
    @Transactional
    public void createFinalRate() throws Exception {
        int databaseSizeBeforeCreate = finalRateRepository.findAll().size();

        // Create the FinalRate

        restFinalRateMockMvc.perform(post("/api/final-rates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(finalRate)))
                .andExpect(status().isCreated());

        // Validate the FinalRate in the database
        List<FinalRate> finalRates = finalRateRepository.findAll();
        assertThat(finalRates).hasSize(databaseSizeBeforeCreate + 1);
        FinalRate testFinalRate = finalRates.get(finalRates.size() - 1);
        assertThat(testFinalRate.getEvoopd()).isEqualTo(DEFAULT_EVOOPD);
        assertThat(testFinalRate.getEv1Ozpd()).isEqualTo(DEFAULT_EV_1_OZPD);
        assertThat(testFinalRate.getEvmb()).isEqualTo(DEFAULT_EVMB);
        assertThat(testFinalRate.getR()).isEqualTo(DEFAULT_R);

        // Validate the FinalRate in ElasticSearch
        FinalRate finalRateEs = finalRateSearchRepository.findOne(testFinalRate.getId());
        assertThat(finalRateEs).isEqualToComparingFieldByField(testFinalRate);
    }

    @Test
    @Transactional
    public void getAllFinalRates() throws Exception {
        // Initialize the database
        finalRateRepository.saveAndFlush(finalRate);

        // Get all the finalRates
        restFinalRateMockMvc.perform(get("/api/final-rates?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(finalRate.getId().intValue())))
                .andExpect(jsonPath("$.[*].evoopd").value(hasItem(DEFAULT_EVOOPD.doubleValue())))
                .andExpect(jsonPath("$.[*].ev1Ozpd").value(hasItem(DEFAULT_EV_1_OZPD.doubleValue())))
                .andExpect(jsonPath("$.[*].evmb").value(hasItem(DEFAULT_EVMB.doubleValue())))
                .andExpect(jsonPath("$.[*].r").value(hasItem(DEFAULT_R.doubleValue())));
    }

    @Test
    @Transactional
    public void getFinalRate() throws Exception {
        // Initialize the database
        finalRateRepository.saveAndFlush(finalRate);

        // Get the finalRate
        restFinalRateMockMvc.perform(get("/api/final-rates/{id}", finalRate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(finalRate.getId().intValue()))
            .andExpect(jsonPath("$.evoopd").value(DEFAULT_EVOOPD.doubleValue()))
            .andExpect(jsonPath("$.ev1Ozpd").value(DEFAULT_EV_1_OZPD.doubleValue()))
            .andExpect(jsonPath("$.evmb").value(DEFAULT_EVMB.doubleValue()))
            .andExpect(jsonPath("$.r").value(DEFAULT_R.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFinalRate() throws Exception {
        // Get the finalRate
        restFinalRateMockMvc.perform(get("/api/final-rates/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFinalRate() throws Exception {
        // Initialize the database
        finalRateService.save(finalRate);

        int databaseSizeBeforeUpdate = finalRateRepository.findAll().size();

        // Update the finalRate
        FinalRate updatedFinalRate = finalRateRepository.findOne(finalRate.getId());
        updatedFinalRate
                .evoopd(UPDATED_EVOOPD)
                .ev1Ozpd(UPDATED_EV_1_OZPD)
                .evmb(UPDATED_EVMB)
                .r(UPDATED_R);

        restFinalRateMockMvc.perform(put("/api/final-rates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFinalRate)))
                .andExpect(status().isOk());

        // Validate the FinalRate in the database
        List<FinalRate> finalRates = finalRateRepository.findAll();
        assertThat(finalRates).hasSize(databaseSizeBeforeUpdate);
        FinalRate testFinalRate = finalRates.get(finalRates.size() - 1);
        assertThat(testFinalRate.getEvoopd()).isEqualTo(UPDATED_EVOOPD);
        assertThat(testFinalRate.getEv1Ozpd()).isEqualTo(UPDATED_EV_1_OZPD);
        assertThat(testFinalRate.getEvmb()).isEqualTo(UPDATED_EVMB);
        assertThat(testFinalRate.getR()).isEqualTo(UPDATED_R);

        // Validate the FinalRate in ElasticSearch
        FinalRate finalRateEs = finalRateSearchRepository.findOne(testFinalRate.getId());
        assertThat(finalRateEs).isEqualToComparingFieldByField(testFinalRate);
    }

    @Test
    @Transactional
    public void deleteFinalRate() throws Exception {
        // Initialize the database
        finalRateService.save(finalRate);

        int databaseSizeBeforeDelete = finalRateRepository.findAll().size();

        // Get the finalRate
        restFinalRateMockMvc.perform(delete("/api/final-rates/{id}", finalRate.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean finalRateExistsInEs = finalRateSearchRepository.exists(finalRate.getId());
        assertThat(finalRateExistsInEs).isFalse();

        // Validate the database is empty
        List<FinalRate> finalRates = finalRateRepository.findAll();
        assertThat(finalRates).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchFinalRate() throws Exception {
        // Initialize the database
        finalRateService.save(finalRate);

        // Search the finalRate
        restFinalRateMockMvc.perform(get("/api/_search/final-rates?query=id:" + finalRate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(finalRate.getId().intValue())))
            .andExpect(jsonPath("$.[*].evoopd").value(hasItem(DEFAULT_EVOOPD.doubleValue())))
            .andExpect(jsonPath("$.[*].ev1Ozpd").value(hasItem(DEFAULT_EV_1_OZPD.doubleValue())))
            .andExpect(jsonPath("$.[*].evmb").value(hasItem(DEFAULT_EVMB.doubleValue())))
            .andExpect(jsonPath("$.[*].r").value(hasItem(DEFAULT_R.doubleValue())));
    }
}
