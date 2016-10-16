package com.gralll.bankaudit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gralll.bankaudit.domain.EvDiagram;
import com.gralll.bankaudit.service.EvDiagramService;
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
 * REST controller for managing EvDiagram.
 */
@RestController
@RequestMapping("/api")
public class EvDiagramResource {

    private final Logger log = LoggerFactory.getLogger(EvDiagramResource.class);
        
    @Inject
    private EvDiagramService evDiagramService;

    /**
     * POST  /ev-diagrams : Create a new evDiagram.
     *
     * @param evDiagram the evDiagram to create
     * @return the ResponseEntity with status 201 (Created) and with body the new evDiagram, or with status 400 (Bad Request) if the evDiagram has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/ev-diagrams",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EvDiagram> createEvDiagram(@RequestBody EvDiagram evDiagram) throws URISyntaxException {
        log.debug("REST request to save EvDiagram : {}", evDiagram);
        if (evDiagram.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("evDiagram", "idexists", "A new evDiagram cannot already have an ID")).body(null);
        }
        EvDiagram result = evDiagramService.save(evDiagram);
        return ResponseEntity.created(new URI("/api/ev-diagrams/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("evDiagram", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ev-diagrams : Updates an existing evDiagram.
     *
     * @param evDiagram the evDiagram to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated evDiagram,
     * or with status 400 (Bad Request) if the evDiagram is not valid,
     * or with status 500 (Internal Server Error) if the evDiagram couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/ev-diagrams",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EvDiagram> updateEvDiagram(@RequestBody EvDiagram evDiagram) throws URISyntaxException {
        log.debug("REST request to update EvDiagram : {}", evDiagram);
        if (evDiagram.getId() == null) {
            return createEvDiagram(evDiagram);
        }
        EvDiagram result = evDiagramService.save(evDiagram);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("evDiagram", evDiagram.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ev-diagrams : get all the evDiagrams.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of evDiagrams in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/ev-diagrams",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<EvDiagram>> getAllEvDiagrams(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of EvDiagrams");
        Page<EvDiagram> page = evDiagramService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ev-diagrams");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ev-diagrams/:id : get the "id" evDiagram.
     *
     * @param id the id of the evDiagram to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the evDiagram, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/ev-diagrams/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EvDiagram> getEvDiagram(@PathVariable Long id) {
        log.debug("REST request to get EvDiagram : {}", id);
        EvDiagram evDiagram = evDiagramService.findOne(id);
        return Optional.ofNullable(evDiagram)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /ev-diagrams/:id : delete the "id" evDiagram.
     *
     * @param id the id of the evDiagram to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/ev-diagrams/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteEvDiagram(@PathVariable Long id) {
        log.debug("REST request to delete EvDiagram : {}", id);
        evDiagramService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("evDiagram", id.toString())).build();
    }

    /**
     * SEARCH  /_search/ev-diagrams?query=:query : search for the evDiagram corresponding
     * to the query.
     *
     * @param query the query of the evDiagram search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/_search/ev-diagrams",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<EvDiagram>> searchEvDiagrams(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of EvDiagrams for query {}", query);
        Page<EvDiagram> page = evDiagramService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/ev-diagrams");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
