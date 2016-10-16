package com.gralll.bankaudit.service;

import com.gralll.bankaudit.domain.AdditionalRate;
import com.gralll.bankaudit.repository.AdditionalRateRepository;
import com.gralll.bankaudit.repository.search.AdditionalRateSearchRepository;
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
 * Service Implementation for managing AdditionalRate.
 */
@Service
@Transactional
public class AdditionalRateService {

    private final Logger log = LoggerFactory.getLogger(AdditionalRateService.class);
    
    @Inject
    private AdditionalRateRepository additionalRateRepository;

    @Inject
    private AdditionalRateSearchRepository additionalRateSearchRepository;

    /**
     * Save a additionalRate.
     *
     * @param additionalRate the entity to save
     * @return the persisted entity
     */
    public AdditionalRate save(AdditionalRate additionalRate) {
        log.debug("Request to save AdditionalRate : {}", additionalRate);
        AdditionalRate result = additionalRateRepository.save(additionalRate);
        additionalRateSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the additionalRates.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<AdditionalRate> findAll(Pageable pageable) {
        log.debug("Request to get all AdditionalRates");
        Page<AdditionalRate> result = additionalRateRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one additionalRate by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public AdditionalRate findOne(Long id) {
        log.debug("Request to get AdditionalRate : {}", id);
        AdditionalRate additionalRate = additionalRateRepository.findOne(id);
        return additionalRate;
    }

    /**
     *  Delete the  additionalRate by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete AdditionalRate : {}", id);
        additionalRateRepository.delete(id);
        additionalRateSearchRepository.delete(id);
    }

    /**
     * Search for the additionalRate corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<AdditionalRate> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of AdditionalRates for query {}", query);
        Page<AdditionalRate> result = additionalRateSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
