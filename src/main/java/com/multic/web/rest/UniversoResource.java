package com.multic.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.multic.domain.Universo;

import com.multic.repository.UniversoRepository;
import com.multic.web.rest.util.HeaderUtil;
import com.multic.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Universo.
 */
@RestController
@RequestMapping("/api")
public class UniversoResource {

    private final Logger log = LoggerFactory.getLogger(UniversoResource.class);

    private static final String ENTITY_NAME = "universo";

    private final UniversoRepository universoRepository;

    public UniversoResource(UniversoRepository universoRepository) {
        this.universoRepository = universoRepository;
    }

    /**
     * POST  /universos : Create a new universo.
     *
     * @param universo the universo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new universo, or with status 400 (Bad Request) if the universo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/universos")
    @Timed
    public ResponseEntity<Universo> createUniverso(@Valid @RequestBody Universo universo) throws URISyntaxException {
        log.debug("REST request to save Universo : {}", universo);
        if (universo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new universo cannot already have an ID")).body(null);
        }
        Universo result = universoRepository.save(universo);
        return ResponseEntity.created(new URI("/api/universos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /universos : Updates an existing universo.
     *
     * @param universo the universo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated universo,
     * or with status 400 (Bad Request) if the universo is not valid,
     * or with status 500 (Internal Server Error) if the universo couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/universos")
    @Timed
    public ResponseEntity<Universo> updateUniverso(@Valid @RequestBody Universo universo) throws URISyntaxException {
        log.debug("REST request to update Universo : {}", universo);
        if (universo.getId() == null) {
            return createUniverso(universo);
        }
        Universo result = universoRepository.save(universo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, universo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /universos : get all the universos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of universos in body
     */
    @GetMapping("/universos")
    @Timed
    public ResponseEntity<List<Universo>> getAllUniversos(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Universos");
        Page<Universo> page = universoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/universos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /universos/:id : get the "id" universo.
     *
     * @param id the id of the universo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the universo, or with status 404 (Not Found)
     */
    @GetMapping("/universos/{id}")
    @Timed
    public ResponseEntity<Universo> getUniverso(@PathVariable Long id) {
        log.debug("REST request to get Universo : {}", id);
        Universo universo = universoRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(universo));
    }

    /**
     * DELETE  /universos/:id : delete the "id" universo.
     *
     * @param id the id of the universo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/universos/{id}")
    @Timed
    public ResponseEntity<Void> deleteUniverso(@PathVariable Long id) {
        log.debug("REST request to delete Universo : {}", id);
        universoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
