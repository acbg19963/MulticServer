package com.multic.web.rest;

import com.multic.MulticApp;

import com.multic.domain.Universo;
import com.multic.repository.UniversoRepository;
import com.multic.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the UniversoResource REST controller.
 *
 * @see UniversoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MulticApp.class)
public class UniversoResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    @Autowired
    private UniversoRepository universoRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUniversoMockMvc;

    private Universo universo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UniversoResource universoResource = new UniversoResource(universoRepository);
        this.restUniversoMockMvc = MockMvcBuilders.standaloneSetup(universoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Universo createEntity(EntityManager em) {
        Universo universo = new Universo()
            .nombre(DEFAULT_NOMBRE)
            .descripcion(DEFAULT_DESCRIPCION);
        return universo;
    }

    @Before
    public void initTest() {
        universo = createEntity(em);
    }

    @Test
    @Transactional
    public void createUniverso() throws Exception {
        int databaseSizeBeforeCreate = universoRepository.findAll().size();

        // Create the Universo
        restUniversoMockMvc.perform(post("/api/universos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(universo)))
            .andExpect(status().isCreated());

        // Validate the Universo in the database
        List<Universo> universoList = universoRepository.findAll();
        assertThat(universoList).hasSize(databaseSizeBeforeCreate + 1);
        Universo testUniverso = universoList.get(universoList.size() - 1);
        assertThat(testUniverso.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testUniverso.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    public void createUniversoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = universoRepository.findAll().size();

        // Create the Universo with an existing ID
        universo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUniversoMockMvc.perform(post("/api/universos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(universo)))
            .andExpect(status().isBadRequest());

        // Validate the Universo in the database
        List<Universo> universoList = universoRepository.findAll();
        assertThat(universoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = universoRepository.findAll().size();
        // set the field null
        universo.setNombre(null);

        // Create the Universo, which fails.

        restUniversoMockMvc.perform(post("/api/universos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(universo)))
            .andExpect(status().isBadRequest());

        List<Universo> universoList = universoRepository.findAll();
        assertThat(universoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescripcionIsRequired() throws Exception {
        int databaseSizeBeforeTest = universoRepository.findAll().size();
        // set the field null
        universo.setDescripcion(null);

        // Create the Universo, which fails.

        restUniversoMockMvc.perform(post("/api/universos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(universo)))
            .andExpect(status().isBadRequest());

        List<Universo> universoList = universoRepository.findAll();
        assertThat(universoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUniversos() throws Exception {
        // Initialize the database
        universoRepository.saveAndFlush(universo);

        // Get all the universoList
        restUniversoMockMvc.perform(get("/api/universos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(universo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())));
    }

    @Test
    @Transactional
    public void getUniverso() throws Exception {
        // Initialize the database
        universoRepository.saveAndFlush(universo);

        // Get the universo
        restUniversoMockMvc.perform(get("/api/universos/{id}", universo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(universo.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUniverso() throws Exception {
        // Get the universo
        restUniversoMockMvc.perform(get("/api/universos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUniverso() throws Exception {
        // Initialize the database
        universoRepository.saveAndFlush(universo);
        int databaseSizeBeforeUpdate = universoRepository.findAll().size();

        // Update the universo
        Universo updatedUniverso = universoRepository.findOne(universo.getId());
        updatedUniverso
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION);

        restUniversoMockMvc.perform(put("/api/universos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUniverso)))
            .andExpect(status().isOk());

        // Validate the Universo in the database
        List<Universo> universoList = universoRepository.findAll();
        assertThat(universoList).hasSize(databaseSizeBeforeUpdate);
        Universo testUniverso = universoList.get(universoList.size() - 1);
        assertThat(testUniverso.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testUniverso.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void updateNonExistingUniverso() throws Exception {
        int databaseSizeBeforeUpdate = universoRepository.findAll().size();

        // Create the Universo

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUniversoMockMvc.perform(put("/api/universos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(universo)))
            .andExpect(status().isCreated());

        // Validate the Universo in the database
        List<Universo> universoList = universoRepository.findAll();
        assertThat(universoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUniverso() throws Exception {
        // Initialize the database
        universoRepository.saveAndFlush(universo);
        int databaseSizeBeforeDelete = universoRepository.findAll().size();

        // Get the universo
        restUniversoMockMvc.perform(delete("/api/universos/{id}", universo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Universo> universoList = universoRepository.findAll();
        assertThat(universoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Universo.class);
        Universo universo1 = new Universo();
        universo1.setId(1L);
        Universo universo2 = new Universo();
        universo2.setId(universo1.getId());
        assertThat(universo1).isEqualTo(universo2);
        universo2.setId(2L);
        assertThat(universo1).isNotEqualTo(universo2);
        universo1.setId(null);
        assertThat(universo1).isNotEqualTo(universo2);
    }
}
