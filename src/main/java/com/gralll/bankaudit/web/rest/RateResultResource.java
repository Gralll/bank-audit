package com.gralll.bankaudit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gralll.bankaudit.domain.RateResult;
import com.gralll.bankaudit.service.RateResultService;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing RateResult.
 */
@RestController
@RequestMapping("/api")
public class RateResultResource {

    private final Logger log = LoggerFactory.getLogger(RateResultResource.class);
        
    @Inject
    private RateResultService rateResultService;

    /**
     * POST  /rate-results : Create a new rateResult.
     *
     * @param rateResult the rateResult to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rateResult, or with status 400 (Bad Request) if the rateResult has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/rate-results",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RateResult> createRateResult(@RequestBody RateResult rateResult) throws URISyntaxException {
        log.debug("REST request to save RateResult : {}", rateResult);
        if (rateResult.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("rateResult", "idexists", "A new rateResult cannot already have an ID")).body(null);
        }
        RateResult result = rateResultService.save(rateResult);
        return ResponseEntity.created(new URI("/api/rate-results/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("rateResult", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rate-results : Updates an existing rateResult.
     *
     * @param rateResult the rateResult to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rateResult,
     * or with status 400 (Bad Request) if the rateResult is not valid,
     * or with status 500 (Internal Server Error) if the rateResult couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/rate-results",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RateResult> updateRateResult(@RequestBody RateResult rateResult) throws URISyntaxException {
        log.debug("REST request to update RateResult : {}", rateResult);
        if (rateResult.getId() == null) {
            return createRateResult(rateResult);
        }
        RateResult result = rateResultService.save(rateResult);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("rateResult", rateResult.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rate-results : get all the rateResults.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of rateResults in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/rate-results",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<RateResult>> getAllRateResults(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of RateResults");
        Page<RateResult> page = rateResultService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/rate-results");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /rate-results/:id : get the "id" rateResult.
     *
     * @param id the id of the rateResult to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rateResult, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/rate-results/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RateResult> getRateResult(@PathVariable Long id) {
        log.debug("REST request to get RateResult : {}", id);
        RateResult rateResult = rateResultService.findOne(id);
        return Optional.ofNullable(rateResult)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /rate-results/:id : delete the "id" rateResult.
     *
     * @param id the id of the rateResult to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/rate-results/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteRateResult(@PathVariable Long id) {
        log.debug("REST request to delete RateResult : {}", id);
        rateResultService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("rateResult", id.toString())).build();
    }

    /**
     * SEARCH  /_search/rate-results?query=:query : search for the rateResult corresponding
     * to the query.
     *
     * @param query the query of the rateResult search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/_search/rate-results",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<RateResult>> searchRateResults(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of RateResults for query {}", query);
        Page<RateResult> page = rateResultService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/rate-results");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
