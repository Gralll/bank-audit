package com.gralll.bankaudit.service;

import com.gralll.bankaudit.domain.EvDiagram;
import com.gralll.bankaudit.repository.EvDiagramRepository;
import com.gralll.bankaudit.repository.search.EvDiagramSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing EvDiagram.
 */
@Service
@Transactional
public class EvDiagramService {

    private final Logger log = LoggerFactory.getLogger(EvDiagramService.class);

    @Inject
    private EvDiagramRepository evDiagramRepository;

    @Inject
    private EvDiagramSearchRepository evDiagramSearchRepository;

    /**
     * Save a evDiagram.
     *
     * @param evDiagram the entity to save
     * @return the persisted entity
     */
    public EvDiagram save(EvDiagram evDiagram) {
        log.debug("Request to save EvDiagram : {}", evDiagram);
        EvDiagram result = evDiagramRepository.save(evDiagram);

        return result;
    }

    /**
     *  Get all the evDiagrams.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<EvDiagram> findAll(Pageable pageable) {
        log.debug("Request to get all EvDiagrams");
        Page<EvDiagram> result = evDiagramRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one evDiagram by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public EvDiagram findOne(Long id) {
        log.debug("Request to get EvDiagram : {}", id);
        EvDiagram evDiagram = evDiagramRepository.findOne(id);
        return evDiagram;
    }

    /**
     *  Delete the  evDiagram by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete EvDiagram : {}", id);
        evDiagramRepository.delete(id);

    }

    /**
     * Search for the evDiagram corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<EvDiagram> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of EvDiagrams for query {}", query);
        Page<EvDiagram> result = evDiagramSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
