package com.gralll.bankaudit.web.rest;

import com.gralll.bankaudit.BankAuditApp;

import com.gralll.bankaudit.domain.GroupDiagram;
import com.gralll.bankaudit.repository.GroupDiagramRepository;
import com.gralll.bankaudit.service.GroupDiagramService;
import com.gralll.bankaudit.repository.search.GroupDiagramSearchRepository;

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
 * Test class for the GroupDiagramResource REST controller.
 *
 * @see GroupDiagramResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BankAuditApp.class)
public class GroupDiagramResourceIntTest {

    private static final String DEFAULT_INDEX_RATE = "AAAAA";
    private static final String UPDATED_INDEX_RATE = "BBBBB";

    private static final Double DEFAULT_VALUE = 1D;
    private static final Double UPDATED_VALUE = 2D;

    private static final Integer DEFAULT_LEVEL = 1;
    private static final Integer UPDATED_LEVEL = 2;

    @Inject
    private GroupDiagramRepository groupDiagramRepository;

    @Inject
    private GroupDiagramService groupDiagramService;

    @Inject
    private GroupDiagramSearchRepository groupDiagramSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restGroupDiagramMockMvc;

    private GroupDiagram groupDiagram;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        GroupDiagramResource groupDiagramResource = new GroupDiagramResource();
        ReflectionTestUtils.setField(groupDiagramResource, "groupDiagramService", groupDiagramService);
        this.restGroupDiagramMockMvc = MockMvcBuilders.standaloneSetup(groupDiagramResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GroupDiagram createEntity(EntityManager em) {
        GroupDiagram groupDiagram = new GroupDiagram()
                .indexRate(DEFAULT_INDEX_RATE)
                .value(DEFAULT_VALUE)
                .level(DEFAULT_LEVEL);
        return groupDiagram;
    }

    @Before
    public void initTest() {
        groupDiagramSearchRepository.deleteAll();
        groupDiagram = createEntity(em);
    }

    @Test
    @Transactional
    public void createGroupDiagram() throws Exception {
        int databaseSizeBeforeCreate = groupDiagramRepository.findAll().size();

        // Create the GroupDiagram

        restGroupDiagramMockMvc.perform(post("/api/group-diagrams")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(groupDiagram)))
                .andExpect(status().isCreated());

        // Validate the GroupDiagram in the database
        List<GroupDiagram> groupDiagrams = groupDiagramRepository.findAll();
        assertThat(groupDiagrams).hasSize(databaseSizeBeforeCreate + 1);
        GroupDiagram testGroupDiagram = groupDiagrams.get(groupDiagrams.size() - 1);
        assertThat(testGroupDiagram.getIndexRate()).isEqualTo(DEFAULT_INDEX_RATE);
        assertThat(testGroupDiagram.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testGroupDiagram.getLevel()).isEqualTo(DEFAULT_LEVEL);

        // Validate the GroupDiagram in ElasticSearch
        GroupDiagram groupDiagramEs = groupDiagramSearchRepository.findOne(testGroupDiagram.getId());
        assertThat(groupDiagramEs).isEqualToComparingFieldByField(testGroupDiagram);
    }

    @Test
    @Transactional
    public void getAllGroupDiagrams() throws Exception {
        // Initialize the database
        groupDiagramRepository.saveAndFlush(groupDiagram);

        // Get all the groupDiagrams
        restGroupDiagramMockMvc.perform(get("/api/group-diagrams?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(groupDiagram.getId().intValue())))
                .andExpect(jsonPath("$.[*].indexRate").value(hasItem(DEFAULT_INDEX_RATE.toString())))
                .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())))
                .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL)));
    }

    @Test
    @Transactional
    public void getGroupDiagram() throws Exception {
        // Initialize the database
        groupDiagramRepository.saveAndFlush(groupDiagram);

        // Get the groupDiagram
        restGroupDiagramMockMvc.perform(get("/api/group-diagrams/{id}", groupDiagram.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(groupDiagram.getId().intValue()))
            .andExpect(jsonPath("$.indexRate").value(DEFAULT_INDEX_RATE.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.doubleValue()))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL));
    }

    @Test
    @Transactional
    public void getNonExistingGroupDiagram() throws Exception {
        // Get the groupDiagram
        restGroupDiagramMockMvc.perform(get("/api/group-diagrams/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGroupDiagram() throws Exception {
        // Initialize the database
        groupDiagramService.save(groupDiagram);

        int databaseSizeBeforeUpdate = groupDiagramRepository.findAll().size();

        // Update the groupDiagram
        GroupDiagram updatedGroupDiagram = groupDiagramRepository.findOne(groupDiagram.getId());
        updatedGroupDiagram
                .indexRate(UPDATED_INDEX_RATE)
                .value(UPDATED_VALUE)
                .level(UPDATED_LEVEL);

        restGroupDiagramMockMvc.perform(put("/api/group-diagrams")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedGroupDiagram)))
                .andExpect(status().isOk());

        // Validate the GroupDiagram in the database
        List<GroupDiagram> groupDiagrams = groupDiagramRepository.findAll();
        assertThat(groupDiagrams).hasSize(databaseSizeBeforeUpdate);
        GroupDiagram testGroupDiagram = groupDiagrams.get(groupDiagrams.size() - 1);
        assertThat(testGroupDiagram.getIndexRate()).isEqualTo(UPDATED_INDEX_RATE);
        assertThat(testGroupDiagram.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testGroupDiagram.getLevel()).isEqualTo(UPDATED_LEVEL);

        // Validate the GroupDiagram in ElasticSearch
        GroupDiagram groupDiagramEs = groupDiagramSearchRepository.findOne(testGroupDiagram.getId());
        assertThat(groupDiagramEs).isEqualToComparingFieldByField(testGroupDiagram);
    }

    @Test
    @Transactional
    public void deleteGroupDiagram() throws Exception {
        // Initialize the database
        groupDiagramService.save(groupDiagram);

        int databaseSizeBeforeDelete = groupDiagramRepository.findAll().size();

        // Get the groupDiagram
        restGroupDiagramMockMvc.perform(delete("/api/group-diagrams/{id}", groupDiagram.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean groupDiagramExistsInEs = groupDiagramSearchRepository.exists(groupDiagram.getId());
        assertThat(groupDiagramExistsInEs).isFalse();

        // Validate the database is empty
        List<GroupDiagram> groupDiagrams = groupDiagramRepository.findAll();
        assertThat(groupDiagrams).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchGroupDiagram() throws Exception {
        // Initialize the database
        groupDiagramService.save(groupDiagram);

        // Search the groupDiagram
        restGroupDiagramMockMvc.perform(get("/api/_search/group-diagrams?query=id:" + groupDiagram.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(groupDiagram.getId().intValue())))
            .andExpect(jsonPath("$.[*].indexRate").value(hasItem(DEFAULT_INDEX_RATE.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL)));
    }
}
