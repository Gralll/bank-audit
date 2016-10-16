package com.gralll.bankaudit.web.rest;

import com.gralll.bankaudit.BankAuditApp;

import com.gralll.bankaudit.domain.AdditionalRate;
import com.gralll.bankaudit.repository.AdditionalRateRepository;
import com.gralll.bankaudit.service.AdditionalRateService;
import com.gralll.bankaudit.repository.search.AdditionalRateSearchRepository;

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
 * Test class for the AdditionalRateResource REST controller.
 *
 * @see AdditionalRateResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BankAuditApp.class)
public class AdditionalRateResourceIntTest {


    private static final Double DEFAULT_EV_1 = 1D;
    private static final Double UPDATED_EV_1 = 2D;

    private static final Double DEFAULT_EV_2 = 1D;
    private static final Double UPDATED_EV_2 = 2D;

    private static final Double DEFAULT_EV_3 = 1D;
    private static final Double UPDATED_EV_3 = 2D;

    private static final Double DEFAULT_EVBPTP = 1D;
    private static final Double UPDATED_EVBPTP = 2D;

    private static final Double DEFAULT_EVBITP = 1D;
    private static final Double UPDATED_EVBITP = 2D;

    private static final Double DEFAULT_EV_1_OZPD = 1D;
    private static final Double UPDATED_EV_1_OZPD = 2D;

    private static final Double DEFAULT_EV_2_OZPD = 1D;
    private static final Double UPDATED_EV_2_OZPD = 2D;

    private static final Double DEFAULT_EVOOPD = 1D;
    private static final Double UPDATED_EVOOPD = 2D;

    @Inject
    private AdditionalRateRepository additionalRateRepository;

    @Inject
    private AdditionalRateService additionalRateService;

    @Inject
    private AdditionalRateSearchRepository additionalRateSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restAdditionalRateMockMvc;

    private AdditionalRate additionalRate;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AdditionalRateResource additionalRateResource = new AdditionalRateResource();
        ReflectionTestUtils.setField(additionalRateResource, "additionalRateService", additionalRateService);
        this.restAdditionalRateMockMvc = MockMvcBuilders.standaloneSetup(additionalRateResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdditionalRate createEntity(EntityManager em) {
        AdditionalRate additionalRate = new AdditionalRate()
                .ev1(DEFAULT_EV_1)
                .ev2(DEFAULT_EV_2)
                .ev3(DEFAULT_EV_3)
                .evbptp(DEFAULT_EVBPTP)
                .evbitp(DEFAULT_EVBITP)
                .ev1Ozpd(DEFAULT_EV_1_OZPD)
                .ev2Ozpd(DEFAULT_EV_2_OZPD)
                .evoopd(DEFAULT_EVOOPD);
        return additionalRate;
    }

    @Before
    public void initTest() {
        additionalRateSearchRepository.deleteAll();
        additionalRate = createEntity(em);
    }

    @Test
    @Transactional
    public void createAdditionalRate() throws Exception {
        int databaseSizeBeforeCreate = additionalRateRepository.findAll().size();

        // Create the AdditionalRate

        restAdditionalRateMockMvc.perform(post("/api/additional-rates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(additionalRate)))
                .andExpect(status().isCreated());

        // Validate the AdditionalRate in the database
        List<AdditionalRate> additionalRates = additionalRateRepository.findAll();
        assertThat(additionalRates).hasSize(databaseSizeBeforeCreate + 1);
        AdditionalRate testAdditionalRate = additionalRates.get(additionalRates.size() - 1);
        assertThat(testAdditionalRate.getEv1()).isEqualTo(DEFAULT_EV_1);
        assertThat(testAdditionalRate.getEv2()).isEqualTo(DEFAULT_EV_2);
        assertThat(testAdditionalRate.getEv3()).isEqualTo(DEFAULT_EV_3);
        assertThat(testAdditionalRate.getEvbptp()).isEqualTo(DEFAULT_EVBPTP);
        assertThat(testAdditionalRate.getEvbitp()).isEqualTo(DEFAULT_EVBITP);
        assertThat(testAdditionalRate.getEv1Ozpd()).isEqualTo(DEFAULT_EV_1_OZPD);
        assertThat(testAdditionalRate.getEv2Ozpd()).isEqualTo(DEFAULT_EV_2_OZPD);
        assertThat(testAdditionalRate.getEvoopd()).isEqualTo(DEFAULT_EVOOPD);

        // Validate the AdditionalRate in ElasticSearch
        AdditionalRate additionalRateEs = additionalRateSearchRepository.findOne(testAdditionalRate.getId());
        assertThat(additionalRateEs).isEqualToComparingFieldByField(testAdditionalRate);
    }

    @Test
    @Transactional
    public void getAllAdditionalRates() throws Exception {
        // Initialize the database
        additionalRateRepository.saveAndFlush(additionalRate);

        // Get all the additionalRates
        restAdditionalRateMockMvc.perform(get("/api/additional-rates?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(additionalRate.getId().intValue())))
                .andExpect(jsonPath("$.[*].ev1").value(hasItem(DEFAULT_EV_1.doubleValue())))
                .andExpect(jsonPath("$.[*].ev2").value(hasItem(DEFAULT_EV_2.doubleValue())))
                .andExpect(jsonPath("$.[*].ev3").value(hasItem(DEFAULT_EV_3.doubleValue())))
                .andExpect(jsonPath("$.[*].evbptp").value(hasItem(DEFAULT_EVBPTP.doubleValue())))
                .andExpect(jsonPath("$.[*].evbitp").value(hasItem(DEFAULT_EVBITP.doubleValue())))
                .andExpect(jsonPath("$.[*].ev1Ozpd").value(hasItem(DEFAULT_EV_1_OZPD.doubleValue())))
                .andExpect(jsonPath("$.[*].ev2Ozpd").value(hasItem(DEFAULT_EV_2_OZPD.doubleValue())))
                .andExpect(jsonPath("$.[*].evoopd").value(hasItem(DEFAULT_EVOOPD.doubleValue())));
    }

    @Test
    @Transactional
    public void getAdditionalRate() throws Exception {
        // Initialize the database
        additionalRateRepository.saveAndFlush(additionalRate);

        // Get the additionalRate
        restAdditionalRateMockMvc.perform(get("/api/additional-rates/{id}", additionalRate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(additionalRate.getId().intValue()))
            .andExpect(jsonPath("$.ev1").value(DEFAULT_EV_1.doubleValue()))
            .andExpect(jsonPath("$.ev2").value(DEFAULT_EV_2.doubleValue()))
            .andExpect(jsonPath("$.ev3").value(DEFAULT_EV_3.doubleValue()))
            .andExpect(jsonPath("$.evbptp").value(DEFAULT_EVBPTP.doubleValue()))
            .andExpect(jsonPath("$.evbitp").value(DEFAULT_EVBITP.doubleValue()))
            .andExpect(jsonPath("$.ev1Ozpd").value(DEFAULT_EV_1_OZPD.doubleValue()))
            .andExpect(jsonPath("$.ev2Ozpd").value(DEFAULT_EV_2_OZPD.doubleValue()))
            .andExpect(jsonPath("$.evoopd").value(DEFAULT_EVOOPD.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAdditionalRate() throws Exception {
        // Get the additionalRate
        restAdditionalRateMockMvc.perform(get("/api/additional-rates/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdditionalRate() throws Exception {
        // Initialize the database
        additionalRateService.save(additionalRate);

        int databaseSizeBeforeUpdate = additionalRateRepository.findAll().size();

        // Update the additionalRate
        AdditionalRate updatedAdditionalRate = additionalRateRepository.findOne(additionalRate.getId());
        updatedAdditionalRate
                .ev1(UPDATED_EV_1)
                .ev2(UPDATED_EV_2)
                .ev3(UPDATED_EV_3)
                .evbptp(UPDATED_EVBPTP)
                .evbitp(UPDATED_EVBITP)
                .ev1Ozpd(UPDATED_EV_1_OZPD)
                .ev2Ozpd(UPDATED_EV_2_OZPD)
                .evoopd(UPDATED_EVOOPD);

        restAdditionalRateMockMvc.perform(put("/api/additional-rates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedAdditionalRate)))
                .andExpect(status().isOk());

        // Validate the AdditionalRate in the database
        List<AdditionalRate> additionalRates = additionalRateRepository.findAll();
        assertThat(additionalRates).hasSize(databaseSizeBeforeUpdate);
        AdditionalRate testAdditionalRate = additionalRates.get(additionalRates.size() - 1);
        assertThat(testAdditionalRate.getEv1()).isEqualTo(UPDATED_EV_1);
        assertThat(testAdditionalRate.getEv2()).isEqualTo(UPDATED_EV_2);
        assertThat(testAdditionalRate.getEv3()).isEqualTo(UPDATED_EV_3);
        assertThat(testAdditionalRate.getEvbptp()).isEqualTo(UPDATED_EVBPTP);
        assertThat(testAdditionalRate.getEvbitp()).isEqualTo(UPDATED_EVBITP);
        assertThat(testAdditionalRate.getEv1Ozpd()).isEqualTo(UPDATED_EV_1_OZPD);
        assertThat(testAdditionalRate.getEv2Ozpd()).isEqualTo(UPDATED_EV_2_OZPD);
        assertThat(testAdditionalRate.getEvoopd()).isEqualTo(UPDATED_EVOOPD);

        // Validate the AdditionalRate in ElasticSearch
        AdditionalRate additionalRateEs = additionalRateSearchRepository.findOne(testAdditionalRate.getId());
        assertThat(additionalRateEs).isEqualToComparingFieldByField(testAdditionalRate);
    }

    @Test
    @Transactional
    public void deleteAdditionalRate() throws Exception {
        // Initialize the database
        additionalRateService.save(additionalRate);

        int databaseSizeBeforeDelete = additionalRateRepository.findAll().size();

        // Get the additionalRate
        restAdditionalRateMockMvc.perform(delete("/api/additional-rates/{id}", additionalRate.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean additionalRateExistsInEs = additionalRateSearchRepository.exists(additionalRate.getId());
        assertThat(additionalRateExistsInEs).isFalse();

        // Validate the database is empty
        List<AdditionalRate> additionalRates = additionalRateRepository.findAll();
        assertThat(additionalRates).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAdditionalRate() throws Exception {
        // Initialize the database
        additionalRateService.save(additionalRate);

        // Search the additionalRate
        restAdditionalRateMockMvc.perform(get("/api/_search/additional-rates?query=id:" + additionalRate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(additionalRate.getId().intValue())))
            .andExpect(jsonPath("$.[*].ev1").value(hasItem(DEFAULT_EV_1.doubleValue())))
            .andExpect(jsonPath("$.[*].ev2").value(hasItem(DEFAULT_EV_2.doubleValue())))
            .andExpect(jsonPath("$.[*].ev3").value(hasItem(DEFAULT_EV_3.doubleValue())))
            .andExpect(jsonPath("$.[*].evbptp").value(hasItem(DEFAULT_EVBPTP.doubleValue())))
            .andExpect(jsonPath("$.[*].evbitp").value(hasItem(DEFAULT_EVBITP.doubleValue())))
            .andExpect(jsonPath("$.[*].ev1Ozpd").value(hasItem(DEFAULT_EV_1_OZPD.doubleValue())))
            .andExpect(jsonPath("$.[*].ev2Ozpd").value(hasItem(DEFAULT_EV_2_OZPD.doubleValue())))
            .andExpect(jsonPath("$.[*].evoopd").value(hasItem(DEFAULT_EVOOPD.doubleValue())));
    }
}
