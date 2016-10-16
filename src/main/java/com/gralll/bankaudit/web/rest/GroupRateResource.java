package com.gralll.bankaudit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gralll.bankaudit.domain.GroupRate;
import com.gralll.bankaudit.service.GroupRateService;
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
 * REST controller for managing GroupRate.
 */
@RestController
@RequestMapping("/api")
public class GroupRateResource {

    private final Logger log = LoggerFactory.getLogger(GroupRateResource.class);
        
    @Inject
    private GroupRateService groupRateService;

    /**
     * POST  /group-rates : Create a new groupRate.
     *
     * @param groupRate the groupRate to create
     * @return the ResponseEntity with status 201 (Created) and with body the new groupRate, or with status 400 (Bad Request) if the groupRate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/group-rates",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GroupRate> createGroupRate(@Valid @RequestBody GroupRate groupRate) throws URISyntaxException {
        log.debug("REST request to save GroupRate : {}", groupRate);
        if (groupRate.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("groupRate", "idexists", "A new groupRate cannot already have an ID")).body(null);
        }
        GroupRate result = groupRateService.save(groupRate);
        return ResponseEntity.created(new URI("/api/group-rates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("groupRate", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /group-rates : Updates an existing groupRate.
     *
     * @param groupRate the groupRate to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated groupRate,
     * or with status 400 (Bad Request) if the groupRate is not valid,
     * or with status 500 (Internal Server Error) if the groupRate couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/group-rates",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GroupRate> updateGroupRate(@Valid @RequestBody GroupRate groupRate) throws URISyntaxException {
        log.debug("REST request to update GroupRate : {}", groupRate);
        if (groupRate.getId() == null) {
            return createGroupRate(groupRate);
        }
        GroupRate result = groupRateService.save(groupRate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("groupRate", groupRate.getId().toString()))
            .body(result);
    }

    /**
     * GET  /group-rates : get all the groupRates.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of groupRates in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/group-rates",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<GroupRate>> getAllGroupRates(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of GroupRates");
        Page<GroupRate> page = groupRateService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/group-rates");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /group-rates/:id : get the "id" groupRate.
     *
     * @param id the id of the groupRate to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the groupRate, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/group-rates/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GroupRate> getGroupRate(@PathVariable Long id) {
        log.debug("REST request to get GroupRate : {}", id);
        GroupRate groupRate = groupRateService.findOne(id);
        return Optional.ofNullable(groupRate)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /group-rates/:id : delete the "id" groupRate.
     *
     * @param id the id of the groupRate to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/group-rates/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteGroupRate(@PathVariable Long id) {
        log.debug("REST request to delete GroupRate : {}", id);
        groupRateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("groupRate", id.toString())).build();
    }

    /**
     * SEARCH  /_search/group-rates?query=:query : search for the groupRate corresponding
     * to the query.
     *
     * @param query the query of the groupRate search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/_search/group-rates",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<GroupRate>> searchGroupRates(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of GroupRates for query {}", query);
        Page<GroupRate> page = groupRateService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/group-rates");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
