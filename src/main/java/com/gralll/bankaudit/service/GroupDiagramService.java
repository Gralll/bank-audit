package com.gralll.bankaudit.service;

import com.gralll.bankaudit.domain.GroupDiagram;
import com.gralll.bankaudit.repository.GroupDiagramRepository;
import com.gralll.bankaudit.repository.search.GroupDiagramSearchRepository;
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
 * Service Implementation for managing GroupDiagram.
 */
@Service
@Transactional
public class GroupDiagramService {

    private final Logger log = LoggerFactory.getLogger(GroupDiagramService.class);
    
    @Inject
    private GroupDiagramRepository groupDiagramRepository;

    @Inject
    private GroupDiagramSearchRepository groupDiagramSearchRepository;

    /**
     * Save a groupDiagram.
     *
     * @param groupDiagram the entity to save
     * @return the persisted entity
     */
    public GroupDiagram save(GroupDiagram groupDiagram) {
        log.debug("Request to save GroupDiagram : {}", groupDiagram);
        GroupDiagram result = groupDiagramRepository.save(groupDiagram);
        groupDiagramSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the groupDiagrams.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<GroupDiagram> findAll(Pageable pageable) {
        log.debug("Request to get all GroupDiagrams");
        Page<GroupDiagram> result = groupDiagramRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one groupDiagram by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public GroupDiagram findOne(Long id) {
        log.debug("Request to get GroupDiagram : {}", id);
        GroupDiagram groupDiagram = groupDiagramRepository.findOne(id);
        return groupDiagram;
    }

    /**
     *  Delete the  groupDiagram by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete GroupDiagram : {}", id);
        groupDiagramRepository.delete(id);
        groupDiagramSearchRepository.delete(id);
    }

    /**
     * Search for the groupDiagram corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<GroupDiagram> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of GroupDiagrams for query {}", query);
        Page<GroupDiagram> result = groupDiagramSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
