package com.gralll.bankaudit.service;

import com.gralll.bankaudit.domain.GroupRate;
import com.gralll.bankaudit.repository.GroupRateRepository;
import com.gralll.bankaudit.repository.LocalRateRepository;
import com.gralll.bankaudit.repository.search.GroupRateSearchRepository;
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
 * Service Implementation for managing GroupRate.
 */
@Service
@Transactional
public class GroupRateService {

    private final Logger log = LoggerFactory.getLogger(GroupRateService.class);

    @Inject
    private GroupRateRepository groupRateRepository;

    @Inject
    private LocalRateRepository localRateRepository;

    @Inject
    private GroupRateSearchRepository groupRateSearchRepository;

    /**
     * Save a groupRate.
     *
     * @param groupRate the entity to save
     * @return the persisted entity
     */
    public GroupRate save(GroupRate groupRate) {
        log.debug("Request to save GroupRate : {}", groupRate);
        groupRate.getLocalRates().stream().forEach(localRate1 -> log.debug(localRate1.toString() + localRate1.getGroupRate()));
        log.debug(String.valueOf(groupRate.getRateData()));
        //groupRate.getLocalRates().stream().forEach(localRate -> {localRate.setGroupRate(groupRate); localRateRepository.save(localRate);});
        GroupRate result = groupRateRepository.save(groupRate);
        result.getLocalRates().stream().forEach(localRate1 -> log.debug(localRate1.toString() + localRate1.getGroupRate()));
        log.debug("" + groupRate.getRateData());

        return result;
    }

    /**
     *  Get all the groupRates.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<GroupRate> findAll(Pageable pageable) {
        log.debug("Request to get all GroupRates");
        Page<GroupRate> result = groupRateRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one groupRate by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public GroupRate findOne(Long id) {
        log.debug("Request to get GroupRate : {}", id);
        GroupRate groupRate = groupRateRepository.findOne(id);
        return groupRate;
    }

    /**
     *  Delete the  groupRate by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete GroupRate : {}", id);
        groupRateRepository.delete(id);

    }

    /**
     * Search for the groupRate corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<GroupRate> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of GroupRates for query {}", query);
        Page<GroupRate> result = groupRateSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
