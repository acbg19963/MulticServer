package com.multic.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.multic.domain.Planeta;

import com.multic.repository.PlanetaRepository;
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

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Planeta.
 */
@RestController
@RequestMapping("/api")
public class PlanetaResource {

    private final Logger log = LoggerFactory.getLogger(PlanetaResource.class);

    private static final String ENTITY_NAME = "planeta";

    private final PlanetaRepository planetaRepository;

    public PlanetaResource(PlanetaRepository planetaRepository) {
        this.planetaRepository = planetaRepository;
    }

    /**
     * POST  /planetas : Create a new planeta.
     *
     * @param planeta the planeta to create
     * @return the ResponseEntity with status 201 (Created) and with body the new planeta, or with status 400 (Bad Request) if the planeta has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/planetas")
    @Timed
    public ResponseEntity<Planeta> createPlaneta(@RequestBody Planeta planeta) throws URISyntaxException {
        log.debug("REST request to save Planeta : {}", planeta);
        if (planeta.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new planeta cannot already have an ID")).body(null);
        }
        Planeta result = planetaRepository.save(planeta);
        return ResponseEntity.created(new URI("/api/planetas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /planetas : Updates an existing planeta.
     *
     * @param planeta the planeta to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated planeta,
     * or with status 400 (Bad Request) if the planeta is not valid,
     * or with status 500 (Internal Server Error) if the planeta couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/planetas")
    @Timed
    public ResponseEntity<Planeta> updatePlaneta(@RequestBody Planeta planeta) throws URISyntaxException {
        log.debug("REST request to update Planeta : {}", planeta);
        if (planeta.getId() == null) {
            return createPlaneta(planeta);
        }
        Planeta result = planetaRepository.save(planeta);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, planeta.getId().toString()))
            .body(result);
    }

    /**
     * GET  /planetas : get all the planetas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of planetas in body
     */
    @GetMapping("/planetas")
    @Timed
    public ResponseEntity<List<Planeta>> getAllPlanetas(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Planetas");
        Page<Planeta> page = planetaRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/planetas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /planetas/:id : get the "id" planeta.
     *
     * @param id the id of the planeta to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the planeta, or with status 404 (Not Found)
     */
    @GetMapping("/planetas/{id}")
    @Timed
    public ResponseEntity<Planeta> getPlaneta(@PathVariable Long id) {
        log.debug("REST request to get Planeta : {}", id);
        Planeta planeta = planetaRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(planeta));
    }

    /**
     * DELETE  /planetas/:id : delete the "id" planeta.
     *
     * @param id the id of the planeta to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/planetas/{id}")
    @Timed
    public ResponseEntity<Void> deletePlaneta(@PathVariable Long id) {
        log.debug("REST request to delete Planeta : {}", id);
        planetaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
