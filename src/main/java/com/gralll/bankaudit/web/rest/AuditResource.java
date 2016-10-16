package com.gralll.bankaudit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gralll.bankaudit.domain.Audit;
import com.gralll.bankaudit.service.AuditService;
import com.gralll.bankaudit.web.rest.util.HeaderUtil;
import com.gralll.bankaudit.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Audit.
 */
@RestController
@RequestMapping("/api")
public class AuditResource {

    private final Logger log = LoggerFactory.getLogger(AuditResource.class);
        
    @Inject
    private AuditService auditService;

    /**
     * POST  /audits : Create a new audit.
     *
     * @param audit the audit to create
     * @return the ResponseEntity with status 201 (Created) and with body the new audit, or with status 400 (Bad Request) if the audit has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/audits",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Audit> createAudit(@Valid @RequestBody Audit audit) throws URISyntaxException {
        log.debug("REST request to save Audit : {}", audit);
        if (audit.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("audit", "idexists", "A new audit cannot already have an ID")).body(null);
        }
        Audit result = auditService.save(audit);
        return ResponseEntity.created(new URI("/api/audits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("audit", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /audits : Updates an existing audit.
     *
     * @param audit the audit to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated audit,
     * or with status 400 (Bad Request) if the audit is not valid,
     * or with status 500 (Internal Server Error) if the audit couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/audits",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Audit> updateAudit(@Valid @RequestBody Audit audit) throws URISyntaxException {
        log.debug("REST request to update Audit : {}", audit);
        if (audit.getId() == null) {
            return createAudit(audit);
        }
        Audit result = auditService.save(audit);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("audit", audit.getId().toString()))
            .body(result);
    }

    /**
     * GET  /audits : get all the audits.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of audits in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/audits",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Audit>> getAllAudits(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Audits");
        Page<Audit> page = auditService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/audits");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /audits/:id : get the "id" audit.
     *
     * @param id the id of the audit to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the audit, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/audits/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Audit> getAudit(@PathVariable Long id) {
        log.debug("REST request to get Audit : {}", id);
        Audit audit = auditService.findOne(id);
        return Optional.ofNullable(audit)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /audits/:id : delete the "id" audit.
     *
     * @param id the id of the audit to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/audits/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAudit(@PathVariable Long id) {
        log.debug("REST request to delete Audit : {}", id);
        auditService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("audit", id.toString())).build();
    }

    /**
     * SEARCH  /_search/audits?query=:query : search for the audit corresponding
     * to the query.
     *
     * @param query the query of the audit search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/_search/audits",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Audit>> searchAudits(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Audits for query {}", query);
        Page<Audit> page = auditService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/audits");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
