package com.gralll.bankaudit.service;

import com.gralll.bankaudit.domain.FinalRate;
import com.gralll.bankaudit.repository.FinalRateRepository;
import com.gralll.bankaudit.repository.search.FinalRateSearchRepository;
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
 * Service Implementation for managing FinalRate.
 */
@Service
@Transactional
public class FinalRateService {

    private final Logger log = LoggerFactory.getLogger(FinalRateService.class);

    @Inject
    private FinalRateRepository finalRateRepository;

    @Inject
    private FinalRateSearchRepository finalRateSearchRepository;

    /**
     * Save a finalRate.
     *
     * @param finalRate the entity to save
     * @return the persisted entity
     */
    public FinalRate save(FinalRate finalRate) {
        log.debug("Request to save FinalRate : {}", finalRate);
        FinalRate result = finalRateRepository.save(finalRate);

        return result;
    }

    /**
     *  Get all the finalRates.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<FinalRate> findAll(Pageable pageable) {
        log.debug("Request to get all FinalRates");
        Page<FinalRate> result = finalRateRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one finalRate by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public FinalRate findOne(Long id) {
        log.debug("Request to get FinalRate : {}", id);
        FinalRate finalRate = finalRateRepository.findOne(id);
        return finalRate;
    }

    /**
     *  Delete the  finalRate by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete FinalRate : {}", id);
        finalRateRepository.delete(id);

    }

    /**
     * Search for the finalRate corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<FinalRate> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of FinalRates for query {}", query);
        Page<FinalRate> result = finalRateSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
