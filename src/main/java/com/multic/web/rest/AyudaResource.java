package com.multic.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.multic.domain.Ayuda;

import com.multic.repository.AyudaRepository;
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
 * REST controller for managing Ayuda.
 */
@RestController
@RequestMapping("/api")
public class AyudaResource {

    private final Logger log = LoggerFactory.getLogger(AyudaResource.class);

    private static final String ENTITY_NAME = "ayuda";

    private final AyudaRepository ayudaRepository;

    public AyudaResource(AyudaRepository ayudaRepository) {
        this.ayudaRepository = ayudaRepository;
    }

    /**
     * POST  /ayudas : Create a new ayuda.
     *
     * @param ayuda the ayuda to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ayuda, or with status 400 (Bad Request) if the ayuda has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ayudas")
    @Timed
    public ResponseEntity<Ayuda> createAyuda(@RequestBody Ayuda ayuda) throws URISyntaxException {
        log.debug("REST request to save Ayuda : {}", ayuda);
        if (ayuda.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new ayuda cannot already have an ID")).body(null);
        }
        Ayuda result = ayudaRepository.save(ayuda);
        return ResponseEntity.created(new URI("/api/ayudas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ayudas : Updates an existing ayuda.
     *
     * @param ayuda the ayuda to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ayuda,
     * or with status 400 (Bad Request) if the ayuda is not valid,
     * or with status 500 (Internal Server Error) if the ayuda couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ayudas")
    @Timed
    public ResponseEntity<Ayuda> updateAyuda(@RequestBody Ayuda ayuda) throws URISyntaxException {
        log.debug("REST request to update Ayuda : {}", ayuda);
        if (ayuda.getId() == null) {
            return createAyuda(ayuda);
        }
        Ayuda result = ayudaRepository.save(ayuda);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ayuda.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ayudas : get all the ayudas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of ayudas in body
     */
    @GetMapping("/ayudas")
    @Timed
    public ResponseEntity<List<Ayuda>> getAllAyudas(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Ayudas");
        Page<Ayuda> page = ayudaRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ayudas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ayudas/:id : get the "id" ayuda.
     *
     * @param id the id of the ayuda to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ayuda, or with status 404 (Not Found)
     */
    @GetMapping("/ayudas/{id}")
    @Timed
    public ResponseEntity<Ayuda> getAyuda(@PathVariable Long id) {
        log.debug("REST request to get Ayuda : {}", id);
        Ayuda ayuda = ayudaRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ayuda));
    }

    /**
     * DELETE  /ayudas/:id : delete the "id" ayuda.
     *
     * @param id the id of the ayuda to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ayudas/{id}")
    @Timed
    public ResponseEntity<Void> deleteAyuda(@PathVariable Long id) {
        log.debug("REST request to delete Ayuda : {}", id);
        ayudaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
