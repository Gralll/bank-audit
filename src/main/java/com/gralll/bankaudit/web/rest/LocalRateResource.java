package com.gralll.bankaudit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gralll.bankaudit.domain.LocalRate;
import com.gralll.bankaudit.service.LocalRateService;
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
 * REST controller for managing LocalRate.
 */
@RestController
@RequestMapping("/api")
public class LocalRateResource {

    private final Logger log = LoggerFactory.getLogger(LocalRateResource.class);
        
    @Inject
    private LocalRateService localRateService;

    /**
     * POST  /local-rates : Create a new localRate.
     *
     * @param localRate the localRate to create
     * @return the ResponseEntity with status 201 (Created) and with body the new localRate, or with status 400 (Bad Request) if the localRate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/local-rates",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LocalRate> createLocalRate(@Valid @RequestBody LocalRate localRate) throws URISyntaxException {
        log.debug("REST request to save LocalRate : {}", localRate);
        if (localRate.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("localRate", "idexists", "A new localRate cannot already have an ID")).body(null);
        }
        LocalRate result = localRateService.save(localRate);
        return ResponseEntity.created(new URI("/api/local-rates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("localRate", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /local-rates : Updates an existing localRate.
     *
     * @param localRate the localRate to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated localRate,
     * or with status 400 (Bad Request) if the localRate is not valid,
     * or with status 500 (Internal Server Error) if the localRate couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/local-rates",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LocalRate> updateLocalRate(@Valid @RequestBody LocalRate localRate) throws URISyntaxException {
        log.debug("REST request to update LocalRate : {}", localRate);
        if (localRate.getId() == null) {
            return createLocalRate(localRate);
        }
        LocalRate result = localRateService.save(localRate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("localRate", localRate.getId().toString()))
            .body(result);
    }

    /**
     * GET  /local-rates : get all the localRates.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of localRates in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/local-rates",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<LocalRate>> getAllLocalRates(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of LocalRates");
        Page<LocalRate> page = localRateService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/local-rates");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /local-rates/:id : get the "id" localRate.
     *
     * @param id the id of the localRate to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the localRate, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/local-rates/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LocalRate> getLocalRate(@PathVariable Long id) {
        log.debug("REST request to get LocalRate : {}", id);
        LocalRate localRate = localRateService.findOne(id);
        return Optional.ofNullable(localRate)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /local-rates/:id : delete the "id" localRate.
     *
     * @param id the id of the localRate to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/local-rates/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteLocalRate(@PathVariable Long id) {
        log.debug("REST request to delete LocalRate : {}", id);
        localRateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("localRate", id.toString())).build();
    }

    /**
     * SEARCH  /_search/local-rates?query=:query : search for the localRate corresponding
     * to the query.
     *
     * @param query the query of the localRate search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/_search/local-rates",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<LocalRate>> searchLocalRates(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of LocalRates for query {}", query);
        Page<LocalRate> page = localRateService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/local-rates");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
