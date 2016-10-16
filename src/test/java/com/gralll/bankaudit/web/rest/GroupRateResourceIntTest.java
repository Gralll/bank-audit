package com.gralll.bankaudit.web.rest;

import com.gralll.bankaudit.BankAuditApp;

import com.gralll.bankaudit.domain.GroupRate;
import com.gralll.bankaudit.repository.GroupRateRepository;
import com.gralll.bankaudit.service.GroupRateService;
import com.gralll.bankaudit.repository.search.GroupRateSearchRepository;

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
 * Test class for the GroupRateResource REST controller.
 *
 * @see GroupRateResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BankAuditApp.class)
public class GroupRateResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_INDEX_RATE = "AAAAA";
    private static final String UPDATED_INDEX_RATE = "BBBBB";

    private static final Double DEFAULT_RATE = 1D;
    private static final Double UPDATED_RATE = 2D;

    @Inject
    private GroupRateRepository groupRateRepository;

    @Inject
    private GroupRateService groupRateService;

    @Inject
    private GroupRateSearchRepository groupRateSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restGroupRateMockMvc;

    private GroupRate groupRate;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        GroupRateResource groupRateResource = new GroupRateResource();
        ReflectionTestUtils.setField(groupRateResource, "groupRateService", groupRateService);
        this.restGroupRateMockMvc = MockMvcBuilders.standaloneSetup(groupRateResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GroupRate createEntity(EntityManager em) {
        GroupRate groupRate = new GroupRate()
                .name(DEFAULT_NAME)
                .indexRate(DEFAULT_INDEX_RATE)
                .rate(DEFAULT_RATE);
        return groupRate;
    }

    @Before
    public void initTest() {
        groupRateSearchRepository.deleteAll();
        groupRate = createEntity(em);
    }

    @Test
    @Transactional
    public void createGroupRate() throws Exception {
        int databaseSizeBeforeCreate = groupRateRepository.findAll().size();

        // Create the GroupRate

        restGroupRateMockMvc.perform(post("/api/group-rates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(groupRate)))
                .andExpect(status().isCreated());

        // Validate the GroupRate in the database
        List<GroupRate> groupRates = groupRateRepository.findAll();
        assertThat(groupRates).hasSize(databaseSizeBeforeCreate + 1);
        GroupRate testGroupRate = groupRates.get(groupRates.size() - 1);
        assertThat(testGroupRate.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGroupRate.getIndexRate()).isEqualTo(DEFAULT_INDEX_RATE);
        assertThat(testGroupRate.getRate()).isEqualTo(DEFAULT_RATE);

        // Validate the GroupRate in ElasticSearch
        GroupRate groupRateEs = groupRateSearchRepository.findOne(testGroupRate.getId());
        assertThat(groupRateEs).isEqualToComparingFieldByField(testGroupRate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = groupRateRepository.findAll().size();
        // set the field null
        groupRate.setName(null);

        // Create the GroupRate, which fails.

        restGroupRateMockMvc.perform(post("/api/group-rates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(groupRate)))
                .andExpect(status().isBadRequest());

        List<GroupRate> groupRates = groupRateRepository.findAll();
        assertThat(groupRates).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIndexRateIsRequired() throws Exception {
        int databaseSizeBeforeTest = groupRateRepository.findAll().size();
        // set the field null
        groupRate.setIndexRate(null);

        // Create the GroupRate, which fails.

        restGroupRateMockMvc.perform(post("/api/group-rates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(groupRate)))
                .andExpect(status().isBadRequest());

        List<GroupRate> groupRates = groupRateRepository.findAll();
        assertThat(groupRates).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGroupRates() throws Exception {
        // Initialize the database
        groupRateRepository.saveAndFlush(groupRate);

        // Get all the groupRates
        restGroupRateMockMvc.perform(get("/api/group-rates?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(groupRate.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].indexRate").value(hasItem(DEFAULT_INDEX_RATE.toString())))
                .andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE.doubleValue())));
    }

    @Test
    @Transactional
    public void getGroupRate() throws Exception {
        // Initialize the database
        groupRateRepository.saveAndFlush(groupRate);

        // Get the groupRate
        restGroupRateMockMvc.perform(get("/api/group-rates/{id}", groupRate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(groupRate.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.indexRate").value(DEFAULT_INDEX_RATE.toString()))
            .andExpect(jsonPath("$.rate").value(DEFAULT_RATE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingGroupRate() throws Exception {
        // Get the groupRate
        restGroupRateMockMvc.perform(get("/api/group-rates/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGroupRate() throws Exception {
        // Initialize the database
        groupRateService.save(groupRate);

        int databaseSizeBeforeUpdate = groupRateRepository.findAll().size();

        // Update the groupRate
        GroupRate updatedGroupRate = groupRateRepository.findOne(groupRate.getId());
        updatedGroupRate
                .name(UPDATED_NAME)
                .indexRate(UPDATED_INDEX_RATE)
                .rate(UPDATED_RATE);

        restGroupRateMockMvc.perform(put("/api/group-rates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedGroupRate)))
                .andExpect(status().isOk());

        // Validate the GroupRate in the database
        List<GroupRate> groupRates = groupRateRepository.findAll();
        assertThat(groupRates).hasSize(databaseSizeBeforeUpdate);
        GroupRate testGroupRate = groupRates.get(groupRates.size() - 1);
        assertThat(testGroupRate.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGroupRate.getIndexRate()).isEqualTo(UPDATED_INDEX_RATE);
        assertThat(testGroupRate.getRate()).isEqualTo(UPDATED_RATE);

        // Validate the GroupRate in ElasticSearch
        GroupRate groupRateEs = groupRateSearchRepository.findOne(testGroupRate.getId());
        assertThat(groupRateEs).isEqualToComparingFieldByField(testGroupRate);
    }

    @Test
    @Transactional
    public void deleteGroupRate() throws Exception {
        // Initialize the database
        groupRateService.save(groupRate);

        int databaseSizeBeforeDelete = groupRateRepository.findAll().size();

        // Get the groupRate
        restGroupRateMockMvc.perform(delete("/api/group-rates/{id}", groupRate.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean groupRateExistsInEs = groupRateSearchRepository.exists(groupRate.getId());
        assertThat(groupRateExistsInEs).isFalse();

        // Validate the database is empty
        List<GroupRate> groupRates = groupRateRepository.findAll();
        assertThat(groupRates).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchGroupRate() throws Exception {
        // Initialize the database
        groupRateService.save(groupRate);

        // Search the groupRate
        restGroupRateMockMvc.perform(get("/api/_search/group-rates?query=id:" + groupRate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(groupRate.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].indexRate").value(hasItem(DEFAULT_INDEX_RATE.toString())))
            .andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE.doubleValue())));
    }
}
