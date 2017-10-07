package com.multic.web.rest;

import com.multic.MulticApp;

import com.multic.domain.ActividadxEstudiante;
import com.multic.repository.ActividadxEstudianteRepository;
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
 * Test class for the ActividadxEstudianteResource REST controller.
 *
 * @see ActividadxEstudianteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MulticApp.class)
public class ActividadxEstudianteResourceIntTest {

    private static final Boolean DEFAULT_ACERTO = false;
    private static final Boolean UPDATED_ACERTO = true;

    private static final Integer DEFAULT_CANTAYUDA = 1;
    private static final Integer UPDATED_CANTAYUDA = 2;

    private static final Integer DEFAULT_TIEMPO = 1;
    private static final Integer UPDATED_TIEMPO = 2;

    @Autowired
    private ActividadxEstudianteRepository actividadxEstudianteRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restActividadxEstudianteMockMvc;

    private ActividadxEstudiante actividadxEstudiante;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ActividadxEstudianteResource actividadxEstudianteResource = new ActividadxEstudianteResource(actividadxEstudianteRepository);
        this.restActividadxEstudianteMockMvc = MockMvcBuilders.standaloneSetup(actividadxEstudianteResource)
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
    public static ActividadxEstudiante createEntity(EntityManager em) {
        ActividadxEstudiante actividadxEstudiante = new ActividadxEstudiante()
            .acerto(DEFAULT_ACERTO)
            .cantayuda(DEFAULT_CANTAYUDA)
            .tiempo(DEFAULT_TIEMPO);
        return actividadxEstudiante;
    }

    @Before
    public void initTest() {
        actividadxEstudiante = createEntity(em);
    }

    @Test
    @Transactional
    public void createActividadxEstudiante() throws Exception {
        int databaseSizeBeforeCreate = actividadxEstudianteRepository.findAll().size();

        // Create the ActividadxEstudiante
        restActividadxEstudianteMockMvc.perform(post("/api/actividadx-estudiantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(actividadxEstudiante)))
            .andExpect(status().isCreated());

        // Validate the ActividadxEstudiante in the database
        List<ActividadxEstudiante> actividadxEstudianteList = actividadxEstudianteRepository.findAll();
        assertThat(actividadxEstudianteList).hasSize(databaseSizeBeforeCreate + 1);
        ActividadxEstudiante testActividadxEstudiante = actividadxEstudianteList.get(actividadxEstudianteList.size() - 1);
        assertThat(testActividadxEstudiante.isAcerto()).isEqualTo(DEFAULT_ACERTO);
        assertThat(testActividadxEstudiante.getCantayuda()).isEqualTo(DEFAULT_CANTAYUDA);
        assertThat(testActividadxEstudiante.getTiempo()).isEqualTo(DEFAULT_TIEMPO);
    }

    @Test
    @Transactional
    public void createActividadxEstudianteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = actividadxEstudianteRepository.findAll().size();

        // Create the ActividadxEstudiante with an existing ID
        actividadxEstudiante.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restActividadxEstudianteMockMvc.perform(post("/api/actividadx-estudiantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(actividadxEstudiante)))
            .andExpect(status().isBadRequest());

        // Validate the ActividadxEstudiante in the database
        List<ActividadxEstudiante> actividadxEstudianteList = actividadxEstudianteRepository.findAll();
        assertThat(actividadxEstudianteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllActividadxEstudiantes() throws Exception {
        // Initialize the database
        actividadxEstudianteRepository.saveAndFlush(actividadxEstudiante);

        // Get all the actividadxEstudianteList
        restActividadxEstudianteMockMvc.perform(get("/api/actividadx-estudiantes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(actividadxEstudiante.getId().intValue())))
            .andExpect(jsonPath("$.[*].acerto").value(hasItem(DEFAULT_ACERTO.booleanValue())))
            .andExpect(jsonPath("$.[*].cantayuda").value(hasItem(DEFAULT_CANTAYUDA)))
            .andExpect(jsonPath("$.[*].tiempo").value(hasItem(DEFAULT_TIEMPO)));
    }

    @Test
    @Transactional
    public void getActividadxEstudiante() throws Exception {
        // Initialize the database
        actividadxEstudianteRepository.saveAndFlush(actividadxEstudiante);

        // Get the actividadxEstudiante
        restActividadxEstudianteMockMvc.perform(get("/api/actividadx-estudiantes/{id}", actividadxEstudiante.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(actividadxEstudiante.getId().intValue()))
            .andExpect(jsonPath("$.acerto").value(DEFAULT_ACERTO.booleanValue()))
            .andExpect(jsonPath("$.cantayuda").value(DEFAULT_CANTAYUDA))
            .andExpect(jsonPath("$.tiempo").value(DEFAULT_TIEMPO));
    }

    @Test
    @Transactional
    public void getNonExistingActividadxEstudiante() throws Exception {
        // Get the actividadxEstudiante
        restActividadxEstudianteMockMvc.perform(get("/api/actividadx-estudiantes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateActividadxEstudiante() throws Exception {
        // Initialize the database
        actividadxEstudianteRepository.saveAndFlush(actividadxEstudiante);
        int databaseSizeBeforeUpdate = actividadxEstudianteRepository.findAll().size();

        // Update the actividadxEstudiante
        ActividadxEstudiante updatedActividadxEstudiante = actividadxEstudianteRepository.findOne(actividadxEstudiante.getId());
        updatedActividadxEstudiante
            .acerto(UPDATED_ACERTO)
            .cantayuda(UPDATED_CANTAYUDA)
            .tiempo(UPDATED_TIEMPO);

        restActividadxEstudianteMockMvc.perform(put("/api/actividadx-estudiantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedActividadxEstudiante)))
            .andExpect(status().isOk());

        // Validate the ActividadxEstudiante in the database
        List<ActividadxEstudiante> actividadxEstudianteList = actividadxEstudianteRepository.findAll();
        assertThat(actividadxEstudianteList).hasSize(databaseSizeBeforeUpdate);
        ActividadxEstudiante testActividadxEstudiante = actividadxEstudianteList.get(actividadxEstudianteList.size() - 1);
        assertThat(testActividadxEstudiante.isAcerto()).isEqualTo(UPDATED_ACERTO);
        assertThat(testActividadxEstudiante.getCantayuda()).isEqualTo(UPDATED_CANTAYUDA);
        assertThat(testActividadxEstudiante.getTiempo()).isEqualTo(UPDATED_TIEMPO);
    }

    @Test
    @Transactional
    public void updateNonExistingActividadxEstudiante() throws Exception {
        int databaseSizeBeforeUpdate = actividadxEstudianteRepository.findAll().size();

        // Create the ActividadxEstudiante

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restActividadxEstudianteMockMvc.perform(put("/api/actividadx-estudiantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(actividadxEstudiante)))
            .andExpect(status().isCreated());

        // Validate the ActividadxEstudiante in the database
        List<ActividadxEstudiante> actividadxEstudianteList = actividadxEstudianteRepository.findAll();
        assertThat(actividadxEstudianteList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteActividadxEstudiante() throws Exception {
        // Initialize the database
        actividadxEstudianteRepository.saveAndFlush(actividadxEstudiante);
        int databaseSizeBeforeDelete = actividadxEstudianteRepository.findAll().size();

        // Get the actividadxEstudiante
        restActividadxEstudianteMockMvc.perform(delete("/api/actividadx-estudiantes/{id}", actividadxEstudiante.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ActividadxEstudiante> actividadxEstudianteList = actividadxEstudianteRepository.findAll();
        assertThat(actividadxEstudianteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActividadxEstudiante.class);
        ActividadxEstudiante actividadxEstudiante1 = new ActividadxEstudiante();
        actividadxEstudiante1.setId(1L);
        ActividadxEstudiante actividadxEstudiante2 = new ActividadxEstudiante();
        actividadxEstudiante2.setId(actividadxEstudiante1.getId());
        assertThat(actividadxEstudiante1).isEqualTo(actividadxEstudiante2);
        actividadxEstudiante2.setId(2L);
        assertThat(actividadxEstudiante1).isNotEqualTo(actividadxEstudiante2);
        actividadxEstudiante1.setId(null);
        assertThat(actividadxEstudiante1).isNotEqualTo(actividadxEstudiante2);
    }
}
