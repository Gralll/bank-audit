package com.gralll.bankaudit.service;

import com.gralll.bankaudit.domain.Audit;
import com.gralll.bankaudit.domain.GroupRate;
import com.gralll.bankaudit.domain.RateData;
import com.gralll.bankaudit.domain.enumeration.AuditProgress;
import com.gralll.bankaudit.domain.util.RateDataGenerator;
import com.gralll.bankaudit.repository.AuditRepository;
import com.gralll.bankaudit.repository.GroupRateRepository;
import com.gralll.bankaudit.repository.LocalRateRepository;
import com.gralll.bankaudit.repository.RateDataRepository;
import com.gralll.bankaudit.repository.search.AuditSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Audit.
 */
@Service
@Transactional
public class AuditService {

    private final Logger log = LoggerFactory.getLogger(AuditService.class);

    @Inject
    private AuditRepository auditRepository;

    @Inject
    private GroupRateRepository groupRateRepository;

    @Inject
    private LocalRateRepository localRateRepository;

    @Inject
    private RateDataRepository rateDataRepository;

    @Inject
    private RateDataGenerator rateDataGenerator;

    @Inject
    private AuditSearchRepository auditSearchRepository;

    /**
     * Save a audit.
     *
     * @param audit the entity to save
     * @return the persisted entity
     */
    public Audit save(Audit audit) {
        log.debug("Request to save Audit : {}", audit);
        if (audit.getRateData() == null) {
            audit.setRateData(rateDataGenerator.generateRateData());
        }
        Audit result = auditRepository.save(audit);
        auditSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the audits.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Audit> findAll(Pageable pageable) {
        log.debug("Request to get all Audits");
        Page<Audit> result = auditRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one audit by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Audit findOne(Long id) {
        log.debug("Request to get Audit : {}", id);
        Audit audit = auditRepository.findOne(id);
        return audit;
    }

    /**
     *  Delete the  audit by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Audit : {}", id);
        auditRepository.delete(id);
        auditSearchRepository.delete(id);
    }

    /**
     * Search for the audit corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Audit> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Audits for query {}", query);
        Page<Audit> result = auditSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
