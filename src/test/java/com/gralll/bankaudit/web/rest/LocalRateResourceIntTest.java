package com.gralll.bankaudit.web.rest;

import com.gralll.bankaudit.BankAuditApp;

import com.gralll.bankaudit.domain.LocalRate;
import com.gralll.bankaudit.repository.LocalRateRepository;
import com.gralll.bankaudit.service.LocalRateService;
import com.gralll.bankaudit.repository.search.LocalRateSearchRepository;

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

import com.gralll.bankaudit.domain.enumeration.CheckCategory;
import com.gralll.bankaudit.domain.enumeration.Documentation;
import com.gralll.bankaudit.domain.enumeration.Execution;
/**
 * Test class for the LocalRateResource REST controller.
 *
 * @see LocalRateResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BankAuditApp.class)
public class LocalRateResourceIntTest {

    private static final String DEFAULT_INDEX_RATE = "AAAAA";
    private static final String UPDATED_INDEX_RATE = "BBBBB";
    private static final String DEFAULT_QUESTION = "AAAAA";
    private static final String UPDATED_QUESTION = "BBBBB";

    private static final Boolean DEFAULT_NECESSARY = false;
    private static final Boolean UPDATED_NECESSARY = true;

    private static final CheckCategory DEFAULT_CATEGORY = CheckCategory.FIRST;
    private static final CheckCategory UPDATED_CATEGORY = CheckCategory.SECOND;

    private static final Documentation DEFAULT_DOCUMENTATION = Documentation.YES;
    private static final Documentation UPDATED_DOCUMENTATION = Documentation.NO;

    private static final Execution DEFAULT_EXECUTION = Execution.YES;
    private static final Execution UPDATED_EXECUTION = Execution.ALMOST;

    private static final Boolean DEFAULT_NOT_RATED = false;
    private static final Boolean UPDATED_NOT_RATED = true;

    private static final Double DEFAULT_RATE = 1D;
    private static final Double UPDATED_RATE = 2D;

    @Inject
    private LocalRateRepository localRateRepository;

    @Inject
    private LocalRateService localRateService;

    @Inject
    private LocalRateSearchRepository localRateSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restLocalRateMockMvc;

    private LocalRate localRate;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LocalRateResource localRateResource = new LocalRateResource();
        ReflectionTestUtils.setField(localRateResource, "localRateService", localRateService);
        this.restLocalRateMockMvc = MockMvcBuilders.standaloneSetup(localRateResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LocalRate createEntity(EntityManager em) {
        LocalRate localRate = new LocalRate()
                .indexRate(DEFAULT_INDEX_RATE)
                .question(DEFAULT_QUESTION)
                .necessary(DEFAULT_NECESSARY)
                .category(DEFAULT_CATEGORY)
                .documentation(DEFAULT_DOCUMENTATION)
                .execution(DEFAULT_EXECUTION)
                .notRated(DEFAULT_NOT_RATED)
                .rate(DEFAULT_RATE);
        return localRate;
    }

    @Before
    public void initTest() {
        localRateSearchRepository.deleteAll();
        localRate = createEntity(em);
    }

    @Test
    @Transactional
    public void createLocalRate() throws Exception {
        int databaseSizeBeforeCreate = localRateRepository.findAll().size();

        // Create the LocalRate

        restLocalRateMockMvc.perform(post("/api/local-rates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(localRate)))
                .andExpect(status().isCreated());

        // Validate the LocalRate in the database
        List<LocalRate> localRates = localRateRepository.findAll();
        assertThat(localRates).hasSize(databaseSizeBeforeCreate + 1);
        LocalRate testLocalRate = localRates.get(localRates.size() - 1);
        assertThat(testLocalRate.getIndexRate()).isEqualTo(DEFAULT_INDEX_RATE);
        assertThat(testLocalRate.getQuestion()).isEqualTo(DEFAULT_QUESTION);
        assertThat(testLocalRate.isNecessary()).isEqualTo(DEFAULT_NECESSARY);
        assertThat(testLocalRate.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testLocalRate.getDocumentation()).isEqualTo(DEFAULT_DOCUMENTATION);
        assertThat(testLocalRate.getExecution()).isEqualTo(DEFAULT_EXECUTION);
        assertThat(testLocalRate.isNotRated()).isEqualTo(DEFAULT_NOT_RATED);
        assertThat(testLocalRate.getRate()).isEqualTo(DEFAULT_RATE);

        // Validate the LocalRate in ElasticSearch
        LocalRate localRateEs = localRateSearchRepository.findOne(testLocalRate.getId());
        assertThat(localRateEs).isEqualToComparingFieldByField(testLocalRate);
    }

    @Test
    @Transactional
    public void checkIndexRateIsRequired() throws Exception {
        int databaseSizeBeforeTest = localRateRepository.findAll().size();
        // set the field null
        localRate.setIndexRate(null);

        // Create the LocalRate, which fails.

        restLocalRateMockMvc.perform(post("/api/local-rates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(localRate)))
                .andExpect(status().isBadRequest());

        List<LocalRate> localRates = localRateRepository.findAll();
        assertThat(localRates).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuestionIsRequired() throws Exception {
        int databaseSizeBeforeTest = localRateRepository.findAll().size();
        // set the field null
        localRate.setQuestion(null);

        // Create the LocalRate, which fails.

        restLocalRateMockMvc.perform(post("/api/local-rates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(localRate)))
                .andExpect(status().isBadRequest());

        List<LocalRate> localRates = localRateRepository.findAll();
        assertThat(localRates).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNecessaryIsRequired() throws Exception {
        int databaseSizeBeforeTest = localRateRepository.findAll().size();
        // set the field null
        localRate.setNecessary(null);

        // Create the LocalRate, which fails.

        restLocalRateMockMvc.perform(post("/api/local-rates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(localRate)))
                .andExpect(status().isBadRequest());

        List<LocalRate> localRates = localRateRepository.findAll();
        assertThat(localRates).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLocalRates() throws Exception {
        // Initialize the database
        localRateRepository.saveAndFlush(localRate);

        // Get all the localRates
        restLocalRateMockMvc.perform(get("/api/local-rates?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(localRate.getId().intValue())))
                .andExpect(jsonPath("$.[*].indexRate").value(hasItem(DEFAULT_INDEX_RATE.toString())))
                .andExpect(jsonPath("$.[*].question").value(hasItem(DEFAULT_QUESTION.toString())))
                .andExpect(jsonPath("$.[*].necessary").value(hasItem(DEFAULT_NECESSARY.booleanValue())))
                .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
                .andExpect(jsonPath("$.[*].documentation").value(hasItem(DEFAULT_DOCUMENTATION.toString())))
                .andExpect(jsonPath("$.[*].execution").value(hasItem(DEFAULT_EXECUTION.toString())))
                .andExpect(jsonPath("$.[*].notRated").value(hasItem(DEFAULT_NOT_RATED.booleanValue())))
                .andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE.doubleValue())));
    }

    @Test
    @Transactional
    public void getLocalRate() throws Exception {
        // Initialize the database
        localRateRepository.saveAndFlush(localRate);

        // Get the localRate
        restLocalRateMockMvc.perform(get("/api/local-rates/{id}", localRate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(localRate.getId().intValue()))
            .andExpect(jsonPath("$.indexRate").value(DEFAULT_INDEX_RATE.toString()))
            .andExpect(jsonPath("$.question").value(DEFAULT_QUESTION.toString()))
            .andExpect(jsonPath("$.necessary").value(DEFAULT_NECESSARY.booleanValue()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()))
            .andExpect(jsonPath("$.documentation").value(DEFAULT_DOCUMENTATION.toString()))
            .andExpect(jsonPath("$.execution").value(DEFAULT_EXECUTION.toString()))
            .andExpect(jsonPath("$.notRated").value(DEFAULT_NOT_RATED.booleanValue()))
            .andExpect(jsonPath("$.rate").value(DEFAULT_RATE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingLocalRate() throws Exception {
        // Get the localRate
        restLocalRateMockMvc.perform(get("/api/local-rates/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLocalRate() throws Exception {
        // Initialize the database
        localRateService.save(localRate);

        int databaseSizeBeforeUpdate = localRateRepository.findAll().size();

        // Update the localRate
        LocalRate updatedLocalRate = localRateRepository.findOne(localRate.getId());
        updatedLocalRate
                .indexRate(UPDATED_INDEX_RATE)
                .question(UPDATED_QUESTION)
                .necessary(UPDATED_NECESSARY)
                .category(UPDATED_CATEGORY)
                .documentation(UPDATED_DOCUMENTATION)
                .execution(UPDATED_EXECUTION)
                .notRated(UPDATED_NOT_RATED)
                .rate(UPDATED_RATE);

        restLocalRateMockMvc.perform(put("/api/local-rates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedLocalRate)))
                .andExpect(status().isOk());

        // Validate the LocalRate in the database
        List<LocalRate> localRates = localRateRepository.findAll();
        assertThat(localRates).hasSize(databaseSizeBeforeUpdate);
        LocalRate testLocalRate = localRates.get(localRates.size() - 1);
        assertThat(testLocalRate.getIndexRate()).isEqualTo(UPDATED_INDEX_RATE);
        assertThat(testLocalRate.getQuestion()).isEqualTo(UPDATED_QUESTION);
        assertThat(testLocalRate.isNecessary()).isEqualTo(UPDATED_NECESSARY);
        assertThat(testLocalRate.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testLocalRate.getDocumentation()).isEqualTo(UPDATED_DOCUMENTATION);
        assertThat(testLocalRate.getExecution()).isEqualTo(UPDATED_EXECUTION);
        assertThat(testLocalRate.isNotRated()).isEqualTo(UPDATED_NOT_RATED);
        assertThat(testLocalRate.getRate()).isEqualTo(UPDATED_RATE);

        // Validate the LocalRate in ElasticSearch
        LocalRate localRateEs = localRateSearchRepository.findOne(testLocalRate.getId());
        assertThat(localRateEs).isEqualToComparingFieldByField(testLocalRate);
    }

    @Test
    @Transactional
    public void deleteLocalRate() throws Exception {
        // Initialize the database
        localRateService.save(localRate);

        int databaseSizeBeforeDelete = localRateRepository.findAll().size();

        // Get the localRate
        restLocalRateMockMvc.perform(delete("/api/local-rates/{id}", localRate.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean localRateExistsInEs = localRateSearchRepository.exists(localRate.getId());
        assertThat(localRateExistsInEs).isFalse();

        // Validate the database is empty
        List<LocalRate> localRates = localRateRepository.findAll();
        assertThat(localRates).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchLocalRate() throws Exception {
        // Initialize the database
        localRateService.save(localRate);

        // Search the localRate
        restLocalRateMockMvc.perform(get("/api/_search/local-rates?query=id:" + localRate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(localRate.getId().intValue())))
            .andExpect(jsonPath("$.[*].indexRate").value(hasItem(DEFAULT_INDEX_RATE.toString())))
            .andExpect(jsonPath("$.[*].question").value(hasItem(DEFAULT_QUESTION.toString())))
            .andExpect(jsonPath("$.[*].necessary").value(hasItem(DEFAULT_NECESSARY.booleanValue())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].documentation").value(hasItem(DEFAULT_DOCUMENTATION.toString())))
            .andExpect(jsonPath("$.[*].execution").value(hasItem(DEFAULT_EXECUTION.toString())))
            .andExpect(jsonPath("$.[*].notRated").value(hasItem(DEFAULT_NOT_RATED.booleanValue())))
            .andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE.doubleValue())));
    }
}
