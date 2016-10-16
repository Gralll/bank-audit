package com.gralll.bankaudit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gralll.bankaudit.domain.RateData;
import com.gralll.bankaudit.service.RateDataService;
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
 * REST controller for managing RateData.
 */
@RestController
@RequestMapping("/api")
public class RateDataResource {

    private final Logger log = LoggerFactory.getLogger(RateDataResource.class);
        
    @Inject
    private RateDataService rateDataService;

    /**
     * POST  /rate-data : Create a new rateData.
     *
     * @param rateData the rateData to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rateData, or with status 400 (Bad Request) if the rateData has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/rate-data",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RateData> createRateData(@RequestBody RateData rateData) throws URISyntaxException {
        log.debug("REST request to save RateData : {}", rateData);
        if (rateData.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("rateData", "idexists", "A new rateData cannot already have an ID")).body(null);
        }
        RateData result = rateDataService.save(rateData);
        return ResponseEntity.created(new URI("/api/rate-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("rateData", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rate-data : Updates an existing rateData.
     *
     * @param rateData the rateData to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rateData,
     * or with status 400 (Bad Request) if the rateData is not valid,
     * or with status 500 (Internal Server Error) if the rateData couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/rate-data",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RateData> updateRateData(@RequestBody RateData rateData) throws URISyntaxException {
        log.debug("REST request to update RateData : {}", rateData);
        if (rateData.getId() == null) {
            return createRateData(rateData);
        }
        RateData result = rateDataService.save(rateData);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("rateData", rateData.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rate-data : get all the rateData.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of rateData in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/rate-data",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<RateData>> getAllRateData(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of RateData");
        Page<RateData> page = rateDataService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/rate-data");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /rate-data/:id : get the "id" rateData.
     *
     * @param id the id of the rateData to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rateData, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/rate-data/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RateData> getRateData(@PathVariable Long id) {
        log.debug("REST request to get RateData : {}", id);
        RateData rateData = rateDataService.findOne(id);
        return Optional.ofNullable(rateData)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /rate-data/:id : delete the "id" rateData.
     *
     * @param id the id of the rateData to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/rate-data/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteRateData(@PathVariable Long id) {
        log.debug("REST request to delete RateData : {}", id);
        rateDataService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("rateData", id.toString())).build();
    }

    /**
     * SEARCH  /_search/rate-data?query=:query : search for the rateData corresponding
     * to the query.
     *
     * @param query the query of the rateData search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/_search/rate-data",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<RateData>> searchRateData(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of RateData for query {}", query);
        Page<RateData> page = rateDataService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/rate-data");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
