package com.multic.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.multic.domain.Actividad;

import com.multic.repository.ActividadRepository;
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
 * REST controller for managing Actividad.
 */
@RestController
@RequestMapping("/api")
public class ActividadResource {

    private final Logger log = LoggerFactory.getLogger(ActividadResource.class);

    private static final String ENTITY_NAME = "actividad";

    private final ActividadRepository actividadRepository;

    public ActividadResource(ActividadRepository actividadRepository) {
        this.actividadRepository = actividadRepository;
    }

    /**
     * POST  /actividads : Create a new actividad.
     *
     * @param actividad the actividad to create
     * @return the ResponseEntity with status 201 (Created) and with body the new actividad, or with status 400 (Bad Request) if the actividad has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/actividads")
    @Timed
    public ResponseEntity<Actividad> createActividad(@RequestBody Actividad actividad) throws URISyntaxException {
        log.debug("REST request to save Actividad : {}", actividad);
        if (actividad.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new actividad cannot already have an ID")).body(null);
        }
        Actividad result = actividadRepository.save(actividad);
        return ResponseEntity.created(new URI("/api/actividads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /actividads : Updates an existing actividad.
     *
     * @param actividad the actividad to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated actividad,
     * or with status 400 (Bad Request) if the actividad is not valid,
     * or with status 500 (Internal Server Error) if the actividad couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/actividads")
    @Timed
    public ResponseEntity<Actividad> updateActividad(@RequestBody Actividad actividad) throws URISyntaxException {
        log.debug("REST request to update Actividad : {}", actividad);
        if (actividad.getId() == null) {
            return createActividad(actividad);
        }
        Actividad result = actividadRepository.save(actividad);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, actividad.getId().toString()))
            .body(result);
    }

    /**
     * GET  /actividads : get all the actividads.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of actividads in body
     */
    @GetMapping("/actividads")
    @Timed
    public ResponseEntity<List<Actividad>> getAllActividads(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Actividads");
        Page<Actividad> page = actividadRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/actividads");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /actividads/:id : get the "id" actividad.
     *
     * @param id the id of the actividad to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the actividad, or with status 404 (Not Found)
     */
    @GetMapping("/actividads/{id}")
    @Timed
    public ResponseEntity<Actividad> getActividad(@PathVariable Long id) {
        log.debug("REST request to get Actividad : {}", id);
        Actividad actividad = actividadRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(actividad));
    }

    /**
     * DELETE  /actividads/:id : delete the "id" actividad.
     *
     * @param id the id of the actividad to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/actividads/{id}")
    @Timed
    public ResponseEntity<Void> deleteActividad(@PathVariable Long id) {
        log.debug("REST request to delete Actividad : {}", id);
        actividadRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
