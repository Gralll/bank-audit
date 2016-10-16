package com.gralll.bankaudit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gralll.bankaudit.domain.AdditionalRate;
import com.gralll.bankaudit.service.AdditionalRateService;
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
 * REST controller for managing AdditionalRate.
 */
@RestController
@RequestMapping("/api")
public class AdditionalRateResource {

    private final Logger log = LoggerFactory.getLogger(AdditionalRateResource.class);
        
    @Inject
    private AdditionalRateService additionalRateService;

    /**
     * POST  /additional-rates : Create a new additionalRate.
     *
     * @param additionalRate the additionalRate to create
     * @return the ResponseEntity with status 201 (Created) and with body the new additionalRate, or with status 400 (Bad Request) if the additionalRate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/additional-rates",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AdditionalRate> createAdditionalRate(@RequestBody AdditionalRate additionalRate) throws URISyntaxException {
        log.debug("REST request to save AdditionalRate : {}", additionalRate);
        if (additionalRate.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("additionalRate", "idexists", "A new additionalRate cannot already have an ID")).body(null);
        }
        AdditionalRate result = additionalRateService.save(additionalRate);
        return ResponseEntity.created(new URI("/api/additional-rates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("additionalRate", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /additional-rates : Updates an existing additionalRate.
     *
     * @param additionalRate the additionalRate to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated additionalRate,
     * or with status 400 (Bad Request) if the additionalRate is not valid,
     * or with status 500 (Internal Server Error) if the additionalRate couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/additional-rates",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AdditionalRate> updateAdditionalRate(@RequestBody AdditionalRate additionalRate) throws URISyntaxException {
        log.debug("REST request to update AdditionalRate : {}", additionalRate);
        if (additionalRate.getId() == null) {
            return createAdditionalRate(additionalRate);
        }
        AdditionalRate result = additionalRateService.save(additionalRate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("additionalRate", additionalRate.getId().toString()))
            .body(result);
    }

    /**
     * GET  /additional-rates : get all the additionalRates.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of additionalRates in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/additional-rates",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<AdditionalRate>> getAllAdditionalRates(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of AdditionalRates");
        Page<AdditionalRate> page = additionalRateService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/additional-rates");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /additional-rates/:id : get the "id" additionalRate.
     *
     * @param id the id of the additionalRate to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the additionalRate, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/additional-rates/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AdditionalRate> getAdditionalRate(@PathVariable Long id) {
        log.debug("REST request to get AdditionalRate : {}", id);
        AdditionalRate additionalRate = additionalRateService.findOne(id);
        return Optional.ofNullable(additionalRate)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /additional-rates/:id : delete the "id" additionalRate.
     *
     * @param id the id of the additionalRate to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/additional-rates/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAdditionalRate(@PathVariable Long id) {
        log.debug("REST request to delete AdditionalRate : {}", id);
        additionalRateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("additionalRate", id.toString())).build();
    }

    /**
     * SEARCH  /_search/additional-rates?query=:query : search for the additionalRate corresponding
     * to the query.
     *
     * @param query the query of the additionalRate search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/_search/additional-rates",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<AdditionalRate>> searchAdditionalRates(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of AdditionalRates for query {}", query);
        Page<AdditionalRate> page = additionalRateService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/additional-rates");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
