package com.gralll.bankaudit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gralll.bankaudit.domain.FinalRate;
import com.gralll.bankaudit.service.FinalRateService;
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
 * REST controller for managing FinalRate.
 */
@RestController
@RequestMapping("/api")
public class FinalRateResource {

    private final Logger log = LoggerFactory.getLogger(FinalRateResource.class);
        
    @Inject
    private FinalRateService finalRateService;

    /**
     * POST  /final-rates : Create a new finalRate.
     *
     * @param finalRate the finalRate to create
     * @return the ResponseEntity with status 201 (Created) and with body the new finalRate, or with status 400 (Bad Request) if the finalRate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/final-rates",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FinalRate> createFinalRate(@RequestBody FinalRate finalRate) throws URISyntaxException {
        log.debug("REST request to save FinalRate : {}", finalRate);
        if (finalRate.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("finalRate", "idexists", "A new finalRate cannot already have an ID")).body(null);
        }
        FinalRate result = finalRateService.save(finalRate);
        return ResponseEntity.created(new URI("/api/final-rates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("finalRate", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /final-rates : Updates an existing finalRate.
     *
     * @param finalRate the finalRate to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated finalRate,
     * or with status 400 (Bad Request) if the finalRate is not valid,
     * or with status 500 (Internal Server Error) if the finalRate couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/final-rates",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FinalRate> updateFinalRate(@RequestBody FinalRate finalRate) throws URISyntaxException {
        log.debug("REST request to update FinalRate : {}", finalRate);
        if (finalRate.getId() == null) {
            return createFinalRate(finalRate);
        }
        FinalRate result = finalRateService.save(finalRate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("finalRate", finalRate.getId().toString()))
            .body(result);
    }

    /**
     * GET  /final-rates : get all the finalRates.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of finalRates in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/final-rates",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<FinalRate>> getAllFinalRates(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of FinalRates");
        Page<FinalRate> page = finalRateService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/final-rates");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /final-rates/:id : get the "id" finalRate.
     *
     * @param id the id of the finalRate to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the finalRate, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/final-rates/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FinalRate> getFinalRate(@PathVariable Long id) {
        log.debug("REST request to get FinalRate : {}", id);
        FinalRate finalRate = finalRateService.findOne(id);
        return Optional.ofNullable(finalRate)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /final-rates/:id : delete the "id" finalRate.
     *
     * @param id the id of the finalRate to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/final-rates/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFinalRate(@PathVariable Long id) {
        log.debug("REST request to delete FinalRate : {}", id);
        finalRateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("finalRate", id.toString())).build();
    }

    /**
     * SEARCH  /_search/final-rates?query=:query : search for the finalRate corresponding
     * to the query.
     *
     * @param query the query of the finalRate search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/_search/final-rates",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<FinalRate>> searchFinalRates(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of FinalRates for query {}", query);
        Page<FinalRate> page = finalRateService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/final-rates");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
