package com.multic.web.rest;

import com.multic.MulticApp;

import com.multic.domain.Estudiante;
import com.multic.repository.EstudianteRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.multic.domain.enumeration.Escenario;
import com.multic.repository.ActividadRepository;
import com.multic.repository.ActividadxEstudianteRepository;
import com.multic.repository.AvatarRepository;
import com.multic.repository.PlanetaRepository;
/**
 * Test class for the EstudianteResource REST controller.
 *
 * @see EstudianteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MulticApp.class)
public class EstudianteResourceIntTest {

    private static final LocalDate DEFAULT_FECHA_NAC = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_NAC = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_COLEGIO = "AAAAAAAAAA";
    private static final String UPDATED_COLEGIO = "BBBBBBBBBB";

    private static final Escenario DEFAULT_ESCENARIO = Escenario.SUPERHEROES;
    private static final Escenario UPDATED_ESCENARIO = Escenario.DEPORTISTAS;

    private static final String DEFAULT_GENERO = "AAAAAAAAAA";
    private static final String UPDATED_GENERO = "BBBBBBBBBB";

    @Autowired
    private EstudianteRepository estudianteRepository;
    
    @Autowired
    private AvatarRepository avatarRepo;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEstudianteMockMvc;

    private Estudiante estudiante;

    @Autowired
    private ActividadRepository actividadRepository;
    
    @Autowired
    private PlanetaRepository planetaRepository;
    
    @Autowired
    private ActividadxEstudianteRepository axeRepositry;
    
    
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EstudianteResource estudianteResource = new EstudianteResource(estudianteRepository, avatarRepo
            , actividadRepository, planetaRepository, axeRepositry);
        this.restEstudianteMockMvc = MockMvcBuilders.standaloneSetup(estudianteResource)
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
    public static Estudiante createEntity(EntityManager em) {
        Estudiante estudiante = new Estudiante()
            .fechaNac(DEFAULT_FECHA_NAC)
            .colegio(DEFAULT_COLEGIO)
            .escenario(DEFAULT_ESCENARIO)
            .genero(DEFAULT_GENERO);
        return estudiante;
    }

    @Before
    public void initTest() {
        estudiante = createEntity(em);
    }

    @Test
    @Transactional
    public void createEstudiante() throws Exception {
        int databaseSizeBeforeCreate = estudianteRepository.findAll().size();

        // Create the Estudiante
        restEstudianteMockMvc.perform(post("/api/estudiantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estudiante)))
            .andExpect(status().isCreated());

        // Validate the Estudiante in the database
        List<Estudiante> estudianteList = estudianteRepository.findAll();
        assertThat(estudianteList).hasSize(databaseSizeBeforeCreate + 1);
        Estudiante testEstudiante = estudianteList.get(estudianteList.size() - 1);
        assertThat(testEstudiante.getFechaNac()).isEqualTo(DEFAULT_FECHA_NAC);
        assertThat(testEstudiante.getColegio()).isEqualTo(DEFAULT_COLEGIO);
        assertThat(testEstudiante.getEscenario()).isEqualTo(DEFAULT_ESCENARIO);
        assertThat(testEstudiante.getGenero()).isEqualTo(DEFAULT_GENERO);
    }

    @Test
    @Transactional
    public void createEstudianteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = estudianteRepository.findAll().size();

        // Create the Estudiante with an existing ID
        estudiante.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEstudianteMockMvc.perform(post("/api/estudiantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estudiante)))
            .andExpect(status().isBadRequest());

        // Validate the Estudiante in the database
        List<Estudiante> estudianteList = estudianteRepository.findAll();
        assertThat(estudianteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFechaNacIsRequired() throws Exception {
        int databaseSizeBeforeTest = estudianteRepository.findAll().size();
        // set the field null
        estudiante.setFechaNac(null);

        // Create the Estudiante, which fails.

        restEstudianteMockMvc.perform(post("/api/estudiantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estudiante)))
            .andExpect(status().isBadRequest());

        List<Estudiante> estudianteList = estudianteRepository.findAll();
        assertThat(estudianteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEstudiantes() throws Exception {
        // Initialize the database
        estudianteRepository.saveAndFlush(estudiante);

        // Get all the estudianteList
        restEstudianteMockMvc.perform(get("/api/estudiantes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estudiante.getId().intValue())))
            .andExpect(jsonPath("$.[*].fechaNac").value(hasItem(DEFAULT_FECHA_NAC.toString())))
            .andExpect(jsonPath("$.[*].colegio").value(hasItem(DEFAULT_COLEGIO.toString())))
            .andExpect(jsonPath("$.[*].escenario").value(hasItem(DEFAULT_ESCENARIO.toString())))
            .andExpect(jsonPath("$.[*].genero").value(hasItem(DEFAULT_GENERO.toString())));
    }

    @Test
    @Transactional
    public void getEstudiante() throws Exception {
        // Initialize the database
        estudianteRepository.saveAndFlush(estudiante);

        // Get the estudiante
        restEstudianteMockMvc.perform(get("/api/estudiantes/{id}", estudiante.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(estudiante.getId().intValue()))
            .andExpect(jsonPath("$.fechaNac").value(DEFAULT_FECHA_NAC.toString()))
            .andExpect(jsonPath("$.colegio").value(DEFAULT_COLEGIO.toString()))
            .andExpect(jsonPath("$.escenario").value(DEFAULT_ESCENARIO.toString()))
            .andExpect(jsonPath("$.genero").value(DEFAULT_GENERO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEstudiante() throws Exception {
        // Get the estudiante
        restEstudianteMockMvc.perform(get("/api/estudiantes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEstudiante() throws Exception {
        // Initialize the database
        estudianteRepository.saveAndFlush(estudiante);
        int databaseSizeBeforeUpdate = estudianteRepository.findAll().size();

        // Update the estudiante
        Estudiante updatedEstudiante = estudianteRepository.findOne(estudiante.getId());
        updatedEstudiante
            .fechaNac(UPDATED_FECHA_NAC)
            .colegio(UPDATED_COLEGIO)
            .escenario(UPDATED_ESCENARIO)
            .genero(UPDATED_GENERO);

        restEstudianteMockMvc.perform(put("/api/estudiantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEstudiante)))
            .andExpect(status().isOk());

        // Validate the Estudiante in the database
        List<Estudiante> estudianteList = estudianteRepository.findAll();
        assertThat(estudianteList).hasSize(databaseSizeBeforeUpdate);
        Estudiante testEstudiante = estudianteList.get(estudianteList.size() - 1);
        assertThat(testEstudiante.getFechaNac()).isEqualTo(UPDATED_FECHA_NAC);
        assertThat(testEstudiante.getColegio()).isEqualTo(UPDATED_COLEGIO);
        assertThat(testEstudiante.getEscenario()).isEqualTo(UPDATED_ESCENARIO);
        assertThat(testEstudiante.getGenero()).isEqualTo(UPDATED_GENERO);
    }

    @Test
    @Transactional
    public void updateNonExistingEstudiante() throws Exception {
        int databaseSizeBeforeUpdate = estudianteRepository.findAll().size();

        // Create the Estudiante

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEstudianteMockMvc.perform(put("/api/estudiantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estudiante)))
            .andExpect(status().isCreated());

        // Validate the Estudiante in the database
        List<Estudiante> estudianteList = estudianteRepository.findAll();
        assertThat(estudianteList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEstudiante() throws Exception {
        // Initialize the database
        estudianteRepository.saveAndFlush(estudiante);
        int databaseSizeBeforeDelete = estudianteRepository.findAll().size();

        // Get the estudiante
        restEstudianteMockMvc.perform(delete("/api/estudiantes/{id}", estudiante.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Estudiante> estudianteList = estudianteRepository.findAll();
        assertThat(estudianteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Estudiante.class);
        Estudiante estudiante1 = new Estudiante();
        estudiante1.setId(1L);
        Estudiante estudiante2 = new Estudiante();
        estudiante2.setId(estudiante1.getId());
        assertThat(estudiante1).isEqualTo(estudiante2);
        estudiante2.setId(2L);
        assertThat(estudiante1).isNotEqualTo(estudiante2);
        estudiante1.setId(null);
        assertThat(estudiante1).isNotEqualTo(estudiante2);
    }
}
