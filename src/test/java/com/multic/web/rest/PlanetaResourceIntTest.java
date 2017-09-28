package com.multic.web.rest;

import com.multic.MulticApp;

import com.multic.domain.Planeta;
import com.multic.repository.PlanetaRepository;
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
 * Test class for the PlanetaResource REST controller.
 *
 * @see PlanetaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MulticApp.class)
public class PlanetaResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final Integer DEFAULT_PROGRESO = 1;
    private static final Integer UPDATED_PROGRESO = 2;

    @Autowired
    private PlanetaRepository planetaRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPlanetaMockMvc;

    private Planeta planeta;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PlanetaResource planetaResource = new PlanetaResource(planetaRepository);
        this.restPlanetaMockMvc = MockMvcBuilders.standaloneSetup(planetaResource)
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
    public static Planeta createEntity(EntityManager em) {
        Planeta planeta = new Planeta()
            .nombre(DEFAULT_NOMBRE)
            .descripcion(DEFAULT_DESCRIPCION)
            .progreso(DEFAULT_PROGRESO);
        return planeta;
    }

    @Before
    public void initTest() {
        planeta = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlaneta() throws Exception {
        int databaseSizeBeforeCreate = planetaRepository.findAll().size();

        // Create the Planeta
        restPlanetaMockMvc.perform(post("/api/planetas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planeta)))
            .andExpect(status().isCreated());

        // Validate the Planeta in the database
        List<Planeta> planetaList = planetaRepository.findAll();
        assertThat(planetaList).hasSize(databaseSizeBeforeCreate + 1);
        Planeta testPlaneta = planetaList.get(planetaList.size() - 1);
        assertThat(testPlaneta.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testPlaneta.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testPlaneta.getProgreso()).isEqualTo(DEFAULT_PROGRESO);
    }

    @Test
    @Transactional
    public void createPlanetaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = planetaRepository.findAll().size();

        // Create the Planeta with an existing ID
        planeta.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlanetaMockMvc.perform(post("/api/planetas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planeta)))
            .andExpect(status().isBadRequest());

        // Validate the Planeta in the database
        List<Planeta> planetaList = planetaRepository.findAll();
        assertThat(planetaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPlanetas() throws Exception {
        // Initialize the database
        planetaRepository.saveAndFlush(planeta);

        // Get all the planetaList
        restPlanetaMockMvc.perform(get("/api/planetas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(planeta.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].progreso").value(hasItem(DEFAULT_PROGRESO)));
    }

    @Test
    @Transactional
    public void getPlaneta() throws Exception {
        // Initialize the database
        planetaRepository.saveAndFlush(planeta);

        // Get the planeta
        restPlanetaMockMvc.perform(get("/api/planetas/{id}", planeta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(planeta.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.progreso").value(DEFAULT_PROGRESO));
    }

    @Test
    @Transactional
    public void getNonExistingPlaneta() throws Exception {
        // Get the planeta
        restPlanetaMockMvc.perform(get("/api/planetas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlaneta() throws Exception {
        // Initialize the database
        planetaRepository.saveAndFlush(planeta);
        int databaseSizeBeforeUpdate = planetaRepository.findAll().size();

        // Update the planeta
        Planeta updatedPlaneta = planetaRepository.findOne(planeta.getId());
        updatedPlaneta
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .progreso(UPDATED_PROGRESO);

        restPlanetaMockMvc.perform(put("/api/planetas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPlaneta)))
            .andExpect(status().isOk());

        // Validate the Planeta in the database
        List<Planeta> planetaList = planetaRepository.findAll();
        assertThat(planetaList).hasSize(databaseSizeBeforeUpdate);
        Planeta testPlaneta = planetaList.get(planetaList.size() - 1);
        assertThat(testPlaneta.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testPlaneta.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testPlaneta.getProgreso()).isEqualTo(UPDATED_PROGRESO);
    }

    @Test
    @Transactional
    public void updateNonExistingPlaneta() throws Exception {
        int databaseSizeBeforeUpdate = planetaRepository.findAll().size();

        // Create the Planeta

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPlanetaMockMvc.perform(put("/api/planetas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planeta)))
            .andExpect(status().isCreated());

        // Validate the Planeta in the database
        List<Planeta> planetaList = planetaRepository.findAll();
        assertThat(planetaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePlaneta() throws Exception {
        // Initialize the database
        planetaRepository.saveAndFlush(planeta);
        int databaseSizeBeforeDelete = planetaRepository.findAll().size();

        // Get the planeta
        restPlanetaMockMvc.perform(delete("/api/planetas/{id}", planeta.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Planeta> planetaList = planetaRepository.findAll();
        assertThat(planetaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Planeta.class);
        Planeta planeta1 = new Planeta();
        planeta1.setId(1L);
        Planeta planeta2 = new Planeta();
        planeta2.setId(planeta1.getId());
        assertThat(planeta1).isEqualTo(planeta2);
        planeta2.setId(2L);
        assertThat(planeta1).isNotEqualTo(planeta2);
        planeta1.setId(null);
        assertThat(planeta1).isNotEqualTo(planeta2);
    }
}
