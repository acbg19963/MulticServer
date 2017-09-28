package com.multic.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.multic.domain.Curso;

import com.multic.repository.CursoRepository;
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
 * REST controller for managing Curso.
 */
@RestController
@RequestMapping("/api")
public class CursoResource {

    private final Logger log = LoggerFactory.getLogger(CursoResource.class);

    private static final String ENTITY_NAME = "curso";

    private final CursoRepository cursoRepository;

    public CursoResource(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    /**
     * POST  /cursos : Create a new curso.
     *
     * @param curso the curso to create
     * @return the ResponseEntity with status 201 (Created) and with body the new curso, or with status 400 (Bad Request) if the curso has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cursos")
    @Timed
    public ResponseEntity<Curso> createCurso(@RequestBody Curso curso) throws URISyntaxException {
        log.debug("REST request to save Curso : {}", curso);
        if (curso.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new curso cannot already have an ID")).body(null);
        }
        Curso result = cursoRepository.save(curso);
        return ResponseEntity.created(new URI("/api/cursos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cursos : Updates an existing curso.
     *
     * @param curso the curso to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated curso,
     * or with status 400 (Bad Request) if the curso is not valid,
     * or with status 500 (Internal Server Error) if the curso couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cursos")
    @Timed
    public ResponseEntity<Curso> updateCurso(@RequestBody Curso curso) throws URISyntaxException {
        log.debug("REST request to update Curso : {}", curso);
        if (curso.getId() == null) {
            return createCurso(curso);
        }
        Curso result = cursoRepository.save(curso);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, curso.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cursos : get all the cursos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of cursos in body
     */
    @GetMapping("/cursos")
    @Timed
    public ResponseEntity<List<Curso>> getAllCursos(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Cursos");
        Page<Curso> page = cursoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cursos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /cursos/:id : get the "id" curso.
     *
     * @param id the id of the curso to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the curso, or with status 404 (Not Found)
     */
    @GetMapping("/cursos/{id}")
    @Timed
    public ResponseEntity<Curso> getCurso(@PathVariable Long id) {
        log.debug("REST request to get Curso : {}", id);
        Curso curso = cursoRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(curso));
    }

    /**
     * DELETE  /cursos/:id : delete the "id" curso.
     *
     * @param id the id of the curso to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cursos/{id}")
    @Timed
    public ResponseEntity<Void> deleteCurso(@PathVariable Long id) {
        log.debug("REST request to delete Curso : {}", id);
        cursoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
