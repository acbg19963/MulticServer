package com.multic.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.multic.domain.TipEstudiante;

import com.multic.repository.TipEstudianteRepository;
import com.multic.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing TipEstudiante.
 */
@RestController
@RequestMapping("/api")
public class TipEstudianteResource {

    private final Logger log = LoggerFactory.getLogger(TipEstudianteResource.class);

    private static final String ENTITY_NAME = "tipEstudiante";

    private final TipEstudianteRepository tipEstudianteRepository;

    public TipEstudianteResource(TipEstudianteRepository tipEstudianteRepository) {
        this.tipEstudianteRepository = tipEstudianteRepository;
    }

    /**
     * POST  /tip-estudiantes : Create a new tipEstudiante.
     *
     * @param tipEstudiante the tipEstudiante to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tipEstudiante, or with status 400 (Bad Request) if the tipEstudiante has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tip-estudiantes")
    @Timed
    public ResponseEntity<TipEstudiante> createTipEstudiante(@Valid @RequestBody TipEstudiante tipEstudiante) throws URISyntaxException {
        log.debug("REST request to save TipEstudiante : {}", tipEstudiante);
        if (tipEstudiante.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new tipEstudiante cannot already have an ID")).body(null);
        }
        TipEstudiante result = tipEstudianteRepository.save(tipEstudiante);
        return ResponseEntity.created(new URI("/api/tip-estudiantes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tip-estudiantes : Updates an existing tipEstudiante.
     *
     * @param tipEstudiante the tipEstudiante to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tipEstudiante,
     * or with status 400 (Bad Request) if the tipEstudiante is not valid,
     * or with status 500 (Internal Server Error) if the tipEstudiante couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tip-estudiantes")
    @Timed
    public ResponseEntity<TipEstudiante> updateTipEstudiante(@Valid @RequestBody TipEstudiante tipEstudiante) throws URISyntaxException {
        log.debug("REST request to update TipEstudiante : {}", tipEstudiante);
        if (tipEstudiante.getId() == null) {
            return createTipEstudiante(tipEstudiante);
        }
        TipEstudiante result = tipEstudianteRepository.save(tipEstudiante);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tipEstudiante.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tip-estudiantes : get all the tipEstudiantes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tipEstudiantes in body
     */
    @GetMapping("/tip-estudiantes")
    @Timed
    public List<TipEstudiante> getAllTipEstudiantes() {
        log.debug("REST request to get all TipEstudiantes");
        return tipEstudianteRepository.findAll();
        }

    /**
     * GET  /tip-estudiantes/:id : get the "id" tipEstudiante.
     *
     * @param id the id of the tipEstudiante to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tipEstudiante, or with status 404 (Not Found)
     */
    @GetMapping("/tip-estudiantes/{id}")
    @Timed
    public ResponseEntity<TipEstudiante> getTipEstudiante(@PathVariable Long id) {
        log.debug("REST request to get TipEstudiante : {}", id);
        TipEstudiante tipEstudiante = tipEstudianteRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tipEstudiante));
    }

    /**
     * DELETE  /tip-estudiantes/:id : delete the "id" tipEstudiante.
     *
     * @param id the id of the tipEstudiante to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tip-estudiantes/{id}")
    @Timed
    public ResponseEntity<Void> deleteTipEstudiante(@PathVariable Long id) {
        log.debug("REST request to delete TipEstudiante : {}", id);
        tipEstudianteRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
