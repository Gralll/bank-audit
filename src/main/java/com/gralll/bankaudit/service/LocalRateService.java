package com.gralll.bankaudit.service;

import com.gralll.bankaudit.domain.LocalRate;
import com.gralll.bankaudit.repository.LocalRateRepository;
import com.gralll.bankaudit.repository.search.LocalRateSearchRepository;
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
 * Service Implementation for managing LocalRate.
 */
@Service
@Transactional
public class LocalRateService {

    private final Logger log = LoggerFactory.getLogger(LocalRateService.class);
    
    @Inject
    private LocalRateRepository localRateRepository;

    @Inject
    private LocalRateSearchRepository localRateSearchRepository;

    /**
     * Save a localRate.
     *
     * @param localRate the entity to save
     * @return the persisted entity
     */
    public LocalRate save(LocalRate localRate) {
        log.debug("Request to save LocalRate : {}", localRate);
        LocalRate result = localRateRepository.save(localRate);
        localRateSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the localRates.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<LocalRate> findAll(Pageable pageable) {
        log.debug("Request to get all LocalRates");
        Page<LocalRate> result = localRateRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one localRate by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public LocalRate findOne(Long id) {
        log.debug("Request to get LocalRate : {}", id);
        LocalRate localRate = localRateRepository.findOne(id);
        return localRate;
    }

    /**
     *  Delete the  localRate by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete LocalRate : {}", id);
        localRateRepository.delete(id);
        localRateSearchRepository.delete(id);
    }

    /**
     * Search for the localRate corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<LocalRate> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of LocalRates for query {}", query);
        Page<LocalRate> result = localRateSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
