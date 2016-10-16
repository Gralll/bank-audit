package com.gralll.bankaudit.service;

import com.gralll.bankaudit.domain.RateData;
import com.gralll.bankaudit.repository.RateDataRepository;
import com.gralll.bankaudit.repository.search.RateDataSearchRepository;
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
 * Service Implementation for managing RateData.
 */
@Service
@Transactional
public class RateDataService {

    private final Logger log = LoggerFactory.getLogger(RateDataService.class);
    
    @Inject
    private RateDataRepository rateDataRepository;

    @Inject
    private RateDataSearchRepository rateDataSearchRepository;

    /**
     * Save a rateData.
     *
     * @param rateData the entity to save
     * @return the persisted entity
     */
    public RateData save(RateData rateData) {
        log.debug("Request to save RateData : {}", rateData);
        RateData result = rateDataRepository.save(rateData);
        rateDataSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the rateData.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<RateData> findAll(Pageable pageable) {
        log.debug("Request to get all RateData");
        Page<RateData> result = rateDataRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one rateData by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public RateData findOne(Long id) {
        log.debug("Request to get RateData : {}", id);
        RateData rateData = rateDataRepository.findOne(id);
        return rateData;
    }

    /**
     *  Delete the  rateData by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete RateData : {}", id);
        rateDataRepository.delete(id);
        rateDataSearchRepository.delete(id);
    }

    /**
     * Search for the rateData corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<RateData> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of RateData for query {}", query);
        Page<RateData> result = rateDataSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
