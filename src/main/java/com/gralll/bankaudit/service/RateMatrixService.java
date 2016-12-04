package com.gralll.bankaudit.service;

import com.gralll.bankaudit.domain.RateMatrix;
import com.gralll.bankaudit.repository.RateMatrixRepository;
import com.gralll.bankaudit.repository.search.RateMatrixSearchRepository;
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
 * Service Implementation for managing RateMatrix.
 */
@Service
@Transactional
public class RateMatrixService {

    private final Logger log = LoggerFactory.getLogger(RateMatrixService.class);

    @Inject
    private RateMatrixRepository rateMatrixRepository;

    @Inject
    private RateMatrixSearchRepository rateMatrixSearchRepository;

    /**
     * Save a rateMatrix.
     *
     * @param rateMatrix the entity to save
     * @return the persisted entity
     */
    public RateMatrix save(RateMatrix rateMatrix) {
        log.debug("Request to save RateMatrix : {}", rateMatrix);
        RateMatrix result = rateMatrixRepository.save(rateMatrix);

        return result;
    }

    /**
     *  Get all the rateMatrices.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<RateMatrix> findAll(Pageable pageable) {
        log.debug("Request to get all RateMatrices");
        Page<RateMatrix> result = rateMatrixRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one rateMatrix by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public RateMatrix findOne(Long id) {
        log.debug("Request to get RateMatrix : {}", id);
        RateMatrix rateMatrix = rateMatrixRepository.findOne(id);
        return rateMatrix;
    }

    /**
     *  Delete the  rateMatrix by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete RateMatrix : {}", id);
        rateMatrixRepository.delete(id);

    }

    /**
     * Search for the rateMatrix corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<RateMatrix> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of RateMatrices for query {}", query);
        Page<RateMatrix> result = rateMatrixSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
