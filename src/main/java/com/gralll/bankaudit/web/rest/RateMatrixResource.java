package com.gralll.bankaudit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gralll.bankaudit.domain.RateMatrix;
import com.gralll.bankaudit.service.RateMatrixService;
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
 * REST controller for managing RateMatrix.
 */
@RestController
@RequestMapping("/api")
public class RateMatrixResource {

    private final Logger log = LoggerFactory.getLogger(RateMatrixResource.class);
        
    @Inject
    private RateMatrixService rateMatrixService;

    /**
     * POST  /rate-matrices : Create a new rateMatrix.
     *
     * @param rateMatrix the rateMatrix to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rateMatrix, or with status 400 (Bad Request) if the rateMatrix has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/rate-matrices",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RateMatrix> createRateMatrix(@RequestBody RateMatrix rateMatrix) throws URISyntaxException {
        log.debug("REST request to save RateMatrix : {}", rateMatrix);
        if (rateMatrix.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("rateMatrix", "idexists", "A new rateMatrix cannot already have an ID")).body(null);
        }
        RateMatrix result = rateMatrixService.save(rateMatrix);
        return ResponseEntity.created(new URI("/api/rate-matrices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("rateMatrix", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rate-matrices : Updates an existing rateMatrix.
     *
     * @param rateMatrix the rateMatrix to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rateMatrix,
     * or with status 400 (Bad Request) if the rateMatrix is not valid,
     * or with status 500 (Internal Server Error) if the rateMatrix couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/rate-matrices",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RateMatrix> updateRateMatrix(@RequestBody RateMatrix rateMatrix) throws URISyntaxException {
        log.debug("REST request to update RateMatrix : {}", rateMatrix);
        if (rateMatrix.getId() == null) {
            return createRateMatrix(rateMatrix);
        }
        RateMatrix result = rateMatrixService.save(rateMatrix);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("rateMatrix", rateMatrix.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rate-matrices : get all the rateMatrices.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of rateMatrices in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/rate-matrices",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<RateMatrix>> getAllRateMatrices(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of RateMatrices");
        Page<RateMatrix> page = rateMatrixService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/rate-matrices");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /rate-matrices/:id : get the "id" rateMatrix.
     *
     * @param id the id of the rateMatrix to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rateMatrix, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/rate-matrices/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RateMatrix> getRateMatrix(@PathVariable Long id) {
        log.debug("REST request to get RateMatrix : {}", id);
        RateMatrix rateMatrix = rateMatrixService.findOne(id);
        return Optional.ofNullable(rateMatrix)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /rate-matrices/:id : delete the "id" rateMatrix.
     *
     * @param id the id of the rateMatrix to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/rate-matrices/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteRateMatrix(@PathVariable Long id) {
        log.debug("REST request to delete RateMatrix : {}", id);
        rateMatrixService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("rateMatrix", id.toString())).build();
    }

    /**
     * SEARCH  /_search/rate-matrices?query=:query : search for the rateMatrix corresponding
     * to the query.
     *
     * @param query the query of the rateMatrix search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/_search/rate-matrices",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<RateMatrix>> searchRateMatrices(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of RateMatrices for query {}", query);
        Page<RateMatrix> page = rateMatrixService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/rate-matrices");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
