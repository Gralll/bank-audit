package com.gralll.bankaudit.web.rest;

import com.gralll.bankaudit.BankAuditApp;

import com.gralll.bankaudit.domain.Audit;
import com.gralll.bankaudit.repository.AuditRepository;
import com.gralll.bankaudit.service.AuditService;
import com.gralll.bankaudit.repository.search.AuditSearchRepository;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AuditResource REST controller.
 *
 * @see AuditResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BankAuditApp.class)
public class AuditResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_BANK = "AAAAA";
    private static final String UPDATED_BANK = "BBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_START_LEVEL = "AAAAA";
    private static final String UPDATED_START_LEVEL = "BBBBB";
    private static final String DEFAULT_END_LEVEL = "AAAAA";
    private static final String UPDATED_END_LEVEL = "BBBBB";

    @Inject
    private AuditRepository auditRepository;

    @Inject
    private AuditService auditService;

    @Inject
    private AuditSearchRepository auditSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restAuditMockMvc;

    private Audit audit;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AuditResource auditResource = new AuditResource();
        ReflectionTestUtils.setField(auditResource, "auditService", auditService);
        this.restAuditMockMvc = MockMvcBuilders.standaloneSetup(auditResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Audit createEntity(EntityManager em) {
        Audit audit = new Audit()
                .name(DEFAULT_NAME)
                .bank(DEFAULT_BANK)
                .startDate(DEFAULT_START_DATE)
                .endDate(DEFAULT_END_DATE)
                .startLevel(DEFAULT_START_LEVEL)
                .endLevel(DEFAULT_END_LEVEL);
        return audit;
    }

    @Before
    public void initTest() {
        auditSearchRepository.deleteAll();
        audit = createEntity(em);
    }

    @Test
    @Transactional
    public void createAudit() throws Exception {
        int databaseSizeBeforeCreate = auditRepository.findAll().size();

        // Create the Audit

        restAuditMockMvc.perform(post("/api/audits")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(audit)))
                .andExpect(status().isCreated());

        // Validate the Audit in the database
        List<Audit> audits = auditRepository.findAll();
        assertThat(audits).hasSize(databaseSizeBeforeCreate + 1);
        Audit testAudit = audits.get(audits.size() - 1);
        assertThat(testAudit.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAudit.getBank()).isEqualTo(DEFAULT_BANK);
        assertThat(testAudit.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testAudit.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testAudit.getStartLevel()).isEqualTo(DEFAULT_START_LEVEL);
        assertThat(testAudit.getEndLevel()).isEqualTo(DEFAULT_END_LEVEL);

        // Validate the Audit in ElasticSearch
        Audit auditEs = auditSearchRepository.findOne(testAudit.getId());
        assertThat(auditEs).isEqualToComparingFieldByField(testAudit);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = auditRepository.findAll().size();
        // set the field null
        audit.setName(null);

        // Create the Audit, which fails.

        restAuditMockMvc.perform(post("/api/audits")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(audit)))
                .andExpect(status().isBadRequest());

        List<Audit> audits = auditRepository.findAll();
        assertThat(audits).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBankIsRequired() throws Exception {
        int databaseSizeBeforeTest = auditRepository.findAll().size();
        // set the field null
        audit.setBank(null);

        // Create the Audit, which fails.

        restAuditMockMvc.perform(post("/api/audits")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(audit)))
                .andExpect(status().isBadRequest());

        List<Audit> audits = auditRepository.findAll();
        assertThat(audits).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAudits() throws Exception {
        // Initialize the database
        auditRepository.saveAndFlush(audit);

        // Get all the audits
        restAuditMockMvc.perform(get("/api/audits?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(audit.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].bank").value(hasItem(DEFAULT_BANK.toString())))
                .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
                .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
                .andExpect(jsonPath("$.[*].startLevel").value(hasItem(DEFAULT_START_LEVEL.toString())))
                .andExpect(jsonPath("$.[*].endLevel").value(hasItem(DEFAULT_END_LEVEL.toString())));
    }

    @Test
    @Transactional
    public void getAudit() throws Exception {
        // Initialize the database
        auditRepository.saveAndFlush(audit);

        // Get the audit
        restAuditMockMvc.perform(get("/api/audits/{id}", audit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(audit.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.bank").value(DEFAULT_BANK.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.startLevel").value(DEFAULT_START_LEVEL.toString()))
            .andExpect(jsonPath("$.endLevel").value(DEFAULT_END_LEVEL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAudit() throws Exception {
        // Get the audit
        restAuditMockMvc.perform(get("/api/audits/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAudit() throws Exception {
        // Initialize the database
        auditService.save(audit);

        int databaseSizeBeforeUpdate = auditRepository.findAll().size();

        // Update the audit
        Audit updatedAudit = auditRepository.findOne(audit.getId());
        updatedAudit
                .name(UPDATED_NAME)
                .bank(UPDATED_BANK)
                .startDate(UPDATED_START_DATE)
                .endDate(UPDATED_END_DATE)
                .startLevel(UPDATED_START_LEVEL)
                .endLevel(UPDATED_END_LEVEL);

        restAuditMockMvc.perform(put("/api/audits")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedAudit)))
                .andExpect(status().isOk());

        // Validate the Audit in the database
        List<Audit> audits = auditRepository.findAll();
        assertThat(audits).hasSize(databaseSizeBeforeUpdate);
        Audit testAudit = audits.get(audits.size() - 1);
        assertThat(testAudit.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAudit.getBank()).isEqualTo(UPDATED_BANK);
        assertThat(testAudit.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testAudit.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testAudit.getStartLevel()).isEqualTo(UPDATED_START_LEVEL);
        assertThat(testAudit.getEndLevel()).isEqualTo(UPDATED_END_LEVEL);

        // Validate the Audit in ElasticSearch
        Audit auditEs = auditSearchRepository.findOne(testAudit.getId());
        assertThat(auditEs).isEqualToComparingFieldByField(testAudit);
    }

    @Test
    @Transactional
    public void deleteAudit() throws Exception {
        // Initialize the database
        auditService.save(audit);

        int databaseSizeBeforeDelete = auditRepository.findAll().size();

        // Get the audit
        restAuditMockMvc.perform(delete("/api/audits/{id}", audit.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean auditExistsInEs = auditSearchRepository.exists(audit.getId());
        assertThat(auditExistsInEs).isFalse();

        // Validate the database is empty
        List<Audit> audits = auditRepository.findAll();
        assertThat(audits).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAudit() throws Exception {
        // Initialize the database
        auditService.save(audit);

        // Search the audit
        restAuditMockMvc.perform(get("/api/_search/audits?query=id:" + audit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(audit.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].bank").value(hasItem(DEFAULT_BANK.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].startLevel").value(hasItem(DEFAULT_START_LEVEL.toString())))
            .andExpect(jsonPath("$.[*].endLevel").value(hasItem(DEFAULT_END_LEVEL.toString())));
    }
}
