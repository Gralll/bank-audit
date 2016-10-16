package com.gralll.bankaudit.web.rest;

import com.gralll.bankaudit.BankAuditApp;

import com.gralll.bankaudit.domain.EvDiagram;
import com.gralll.bankaudit.repository.EvDiagramRepository;
import com.gralll.bankaudit.service.EvDiagramService;
import com.gralll.bankaudit.repository.search.EvDiagramSearchRepository;

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
 * Test class for the EvDiagramResource REST controller.
 *
 * @see EvDiagramResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BankAuditApp.class)
public class EvDiagramResourceIntTest {


    private static final Double DEFAULT_EV_1 = 1D;
    private static final Double UPDATED_EV_1 = 2D;

    private static final Double DEFAULT_EV_2 = 1D;
    private static final Double UPDATED_EV_2 = 2D;

    private static final Double DEFAULT_EV_3 = 1D;
    private static final Double UPDATED_EV_3 = 2D;

    @Inject
    private EvDiagramRepository evDiagramRepository;

    @Inject
    private EvDiagramService evDiagramService;

    @Inject
    private EvDiagramSearchRepository evDiagramSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restEvDiagramMockMvc;

    private EvDiagram evDiagram;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EvDiagramResource evDiagramResource = new EvDiagramResource();
        ReflectionTestUtils.setField(evDiagramResource, "evDiagramService", evDiagramService);
        this.restEvDiagramMockMvc = MockMvcBuilders.standaloneSetup(evDiagramResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EvDiagram createEntity(EntityManager em) {
        EvDiagram evDiagram = new EvDiagram()
                .ev1(DEFAULT_EV_1)
                .ev2(DEFAULT_EV_2)
                .ev3(DEFAULT_EV_3);
        return evDiagram;
    }

    @Before
    public void initTest() {
        evDiagramSearchRepository.deleteAll();
        evDiagram = createEntity(em);
    }

    @Test
    @Transactional
    public void createEvDiagram() throws Exception {
        int databaseSizeBeforeCreate = evDiagramRepository.findAll().size();

        // Create the EvDiagram

        restEvDiagramMockMvc.perform(post("/api/ev-diagrams")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(evDiagram)))
                .andExpect(status().isCreated());

        // Validate the EvDiagram in the database
        List<EvDiagram> evDiagrams = evDiagramRepository.findAll();
        assertThat(evDiagrams).hasSize(databaseSizeBeforeCreate + 1);
        EvDiagram testEvDiagram = evDiagrams.get(evDiagrams.size() - 1);
        assertThat(testEvDiagram.getEv1()).isEqualTo(DEFAULT_EV_1);
        assertThat(testEvDiagram.getEv2()).isEqualTo(DEFAULT_EV_2);
        assertThat(testEvDiagram.getEv3()).isEqualTo(DEFAULT_EV_3);

        // Validate the EvDiagram in ElasticSearch
        EvDiagram evDiagramEs = evDiagramSearchRepository.findOne(testEvDiagram.getId());
        assertThat(evDiagramEs).isEqualToComparingFieldByField(testEvDiagram);
    }

    @Test
    @Transactional
    public void getAllEvDiagrams() throws Exception {
        // Initialize the database
        evDiagramRepository.saveAndFlush(evDiagram);

        // Get all the evDiagrams
        restEvDiagramMockMvc.perform(get("/api/ev-diagrams?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(evDiagram.getId().intValue())))
                .andExpect(jsonPath("$.[*].ev1").value(hasItem(DEFAULT_EV_1.doubleValue())))
                .andExpect(jsonPath("$.[*].ev2").value(hasItem(DEFAULT_EV_2.doubleValue())))
                .andExpect(jsonPath("$.[*].ev3").value(hasItem(DEFAULT_EV_3.doubleValue())));
    }

    @Test
    @Transactional
    public void getEvDiagram() throws Exception {
        // Initialize the database
        evDiagramRepository.saveAndFlush(evDiagram);

        // Get the evDiagram
        restEvDiagramMockMvc.perform(get("/api/ev-diagrams/{id}", evDiagram.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(evDiagram.getId().intValue()))
            .andExpect(jsonPath("$.ev1").value(DEFAULT_EV_1.doubleValue()))
            .andExpect(jsonPath("$.ev2").value(DEFAULT_EV_2.doubleValue()))
            .andExpect(jsonPath("$.ev3").value(DEFAULT_EV_3.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEvDiagram() throws Exception {
        // Get the evDiagram
        restEvDiagramMockMvc.perform(get("/api/ev-diagrams/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEvDiagram() throws Exception {
        // Initialize the database
        evDiagramService.save(evDiagram);

        int databaseSizeBeforeUpdate = evDiagramRepository.findAll().size();

        // Update the evDiagram
        EvDiagram updatedEvDiagram = evDiagramRepository.findOne(evDiagram.getId());
        updatedEvDiagram
                .ev1(UPDATED_EV_1)
                .ev2(UPDATED_EV_2)
                .ev3(UPDATED_EV_3);

        restEvDiagramMockMvc.perform(put("/api/ev-diagrams")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedEvDiagram)))
                .andExpect(status().isOk());

        // Validate the EvDiagram in the database
        List<EvDiagram> evDiagrams = evDiagramRepository.findAll();
        assertThat(evDiagrams).hasSize(databaseSizeBeforeUpdate);
        EvDiagram testEvDiagram = evDiagrams.get(evDiagrams.size() - 1);
        assertThat(testEvDiagram.getEv1()).isEqualTo(UPDATED_EV_1);
        assertThat(testEvDiagram.getEv2()).isEqualTo(UPDATED_EV_2);
        assertThat(testEvDiagram.getEv3()).isEqualTo(UPDATED_EV_3);

        // Validate the EvDiagram in ElasticSearch
        EvDiagram evDiagramEs = evDiagramSearchRepository.findOne(testEvDiagram.getId());
        assertThat(evDiagramEs).isEqualToComparingFieldByField(testEvDiagram);
    }

    @Test
    @Transactional
    public void deleteEvDiagram() throws Exception {
        // Initialize the database
        evDiagramService.save(evDiagram);

        int databaseSizeBeforeDelete = evDiagramRepository.findAll().size();

        // Get the evDiagram
        restEvDiagramMockMvc.perform(delete("/api/ev-diagrams/{id}", evDiagram.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean evDiagramExistsInEs = evDiagramSearchRepository.exists(evDiagram.getId());
        assertThat(evDiagramExistsInEs).isFalse();

        // Validate the database is empty
        List<EvDiagram> evDiagrams = evDiagramRepository.findAll();
        assertThat(evDiagrams).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchEvDiagram() throws Exception {
        // Initialize the database
        evDiagramService.save(evDiagram);

        // Search the evDiagram
        restEvDiagramMockMvc.perform(get("/api/_search/ev-diagrams?query=id:" + evDiagram.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(evDiagram.getId().intValue())))
            .andExpect(jsonPath("$.[*].ev1").value(hasItem(DEFAULT_EV_1.doubleValue())))
            .andExpect(jsonPath("$.[*].ev2").value(hasItem(DEFAULT_EV_2.doubleValue())))
            .andExpect(jsonPath("$.[*].ev3").value(hasItem(DEFAULT_EV_3.doubleValue())));
    }
}
