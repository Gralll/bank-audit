package com.gralll.bankaudit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gralll.bankaudit.domain.GroupDiagram;
import com.gralll.bankaudit.service.GroupDiagramService;
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
 * REST controller for managing GroupDiagram.
 */
@RestController
@RequestMapping("/api")
public class GroupDiagramResource {

    private final Logger log = LoggerFactory.getLogger(GroupDiagramResource.class);
        
    @Inject
    private GroupDiagramService groupDiagramService;

    /**
     * POST  /group-diagrams : Create a new groupDiagram.
     *
     * @param groupDiagram the groupDiagram to create
     * @return the ResponseEntity with status 201 (Created) and with body the new groupDiagram, or with status 400 (Bad Request) if the groupDiagram has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/group-diagrams",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GroupDiagram> createGroupDiagram(@RequestBody GroupDiagram groupDiagram) throws URISyntaxException {
        log.debug("REST request to save GroupDiagram : {}", groupDiagram);
        if (groupDiagram.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("groupDiagram", "idexists", "A new groupDiagram cannot already have an ID")).body(null);
        }
        GroupDiagram result = groupDiagramService.save(groupDiagram);
        return ResponseEntity.created(new URI("/api/group-diagrams/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("groupDiagram", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /group-diagrams : Updates an existing groupDiagram.
     *
     * @param groupDiagram the groupDiagram to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated groupDiagram,
     * or with status 400 (Bad Request) if the groupDiagram is not valid,
     * or with status 500 (Internal Server Error) if the groupDiagram couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/group-diagrams",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GroupDiagram> updateGroupDiagram(@RequestBody GroupDiagram groupDiagram) throws URISyntaxException {
        log.debug("REST request to update GroupDiagram : {}", groupDiagram);
        if (groupDiagram.getId() == null) {
            return createGroupDiagram(groupDiagram);
        }
        GroupDiagram result = groupDiagramService.save(groupDiagram);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("groupDiagram", groupDiagram.getId().toString()))
            .body(result);
    }

    /**
     * GET  /group-diagrams : get all the groupDiagrams.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of groupDiagrams in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/group-diagrams",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<GroupDiagram>> getAllGroupDiagrams(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of GroupDiagrams");
        Page<GroupDiagram> page = groupDiagramService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/group-diagrams");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /group-diagrams/:id : get the "id" groupDiagram.
     *
     * @param id the id of the groupDiagram to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the groupDiagram, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/group-diagrams/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GroupDiagram> getGroupDiagram(@PathVariable Long id) {
        log.debug("REST request to get GroupDiagram : {}", id);
        GroupDiagram groupDiagram = groupDiagramService.findOne(id);
        return Optional.ofNullable(groupDiagram)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /group-diagrams/:id : delete the "id" groupDiagram.
     *
     * @param id the id of the groupDiagram to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/group-diagrams/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteGroupDiagram(@PathVariable Long id) {
        log.debug("REST request to delete GroupDiagram : {}", id);
        groupDiagramService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("groupDiagram", id.toString())).build();
    }

    /**
     * SEARCH  /_search/group-diagrams?query=:query : search for the groupDiagram corresponding
     * to the query.
     *
     * @param query the query of the groupDiagram search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/_search/group-diagrams",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<GroupDiagram>> searchGroupDiagrams(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of GroupDiagrams for query {}", query);
        Page<GroupDiagram> page = groupDiagramService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/group-diagrams");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
