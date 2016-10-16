package com.gralll.bankaudit.service;

import com.gralll.bankaudit.domain.RateResult;
import com.gralll.bankaudit.repository.RateResultRepository;
import com.gralll.bankaudit.repository.search.RateResultSearchRepository;
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
 * Service Implementation for managing RateResult.
 */
@Service
@Transactional
public class RateResultService {

    private final Logger log = LoggerFactory.getLogger(RateResultService.class);
    
    @Inject
    private RateResultRepository rateResultRepository;

    @Inject
    private RateResultSearchRepository rateResultSearchRepository;

    /**
     * Save a rateResult.
     *
     * @param rateResult the entity to save
     * @return the persisted entity
     */
    public RateResult save(RateResult rateResult) {
        log.debug("Request to save RateResult : {}", rateResult);
        RateResult result = rateResultRepository.save(rateResult);
        rateResultSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the rateResults.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<RateResult> findAll(Pageable pageable) {
        log.debug("Request to get all RateResults");
        Page<RateResult> result = rateResultRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one rateResult by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public RateResult findOne(Long id) {
        log.debug("Request to get RateResult : {}", id);
        RateResult rateResult = rateResultRepository.findOne(id);
        return rateResult;
    }

    /**
     *  Delete the  rateResult by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete RateResult : {}", id);
        rateResultRepository.delete(id);
        rateResultSearchRepository.delete(id);
    }

    /**
     * Search for the rateResult corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<RateResult> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of RateResults for query {}", query);
        Page<RateResult> result = rateResultSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
