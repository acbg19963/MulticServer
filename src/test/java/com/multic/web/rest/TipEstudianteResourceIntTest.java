package com.multic.web.rest;

import com.multic.MulticApp;

import com.multic.domain.TipEstudiante;
import com.multic.repository.TipEstudianteRepository;
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
 * Test class for the TipEstudianteResource REST controller.
 *
 * @see TipEstudianteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MulticApp.class)
public class TipEstudianteResourceIntTest {

    private static final String DEFAULT_TIP = "AAAAAAAAAA";
    private static final String UPDATED_TIP = "BBBBBBBBBB";

    @Autowired
    private TipEstudianteRepository tipEstudianteRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTipEstudianteMockMvc;

    private TipEstudiante tipEstudiante;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TipEstudianteResource tipEstudianteResource = new TipEstudianteResource(tipEstudianteRepository);
        this.restTipEstudianteMockMvc = MockMvcBuilders.standaloneSetup(tipEstudianteResource)
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
    public static TipEstudiante createEntity(EntityManager em) {
        TipEstudiante tipEstudiante = new TipEstudiante()
            .tip(DEFAULT_TIP);
        return tipEstudiante;
    }

    @Before
    public void initTest() {
        tipEstudiante = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipEstudiante() throws Exception {
        int databaseSizeBeforeCreate = tipEstudianteRepository.findAll().size();

        // Create the TipEstudiante
        restTipEstudianteMockMvc.perform(post("/api/tip-estudiantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipEstudiante)))
            .andExpect(status().isCreated());

        // Validate the TipEstudiante in the database
        List<TipEstudiante> tipEstudianteList = tipEstudianteRepository.findAll();
        assertThat(tipEstudianteList).hasSize(databaseSizeBeforeCreate + 1);
        TipEstudiante testTipEstudiante = tipEstudianteList.get(tipEstudianteList.size() - 1);
        assertThat(testTipEstudiante.getTip()).isEqualTo(DEFAULT_TIP);
    }

    @Test
    @Transactional
    public void createTipEstudianteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipEstudianteRepository.findAll().size();

        // Create the TipEstudiante with an existing ID
        tipEstudiante.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipEstudianteMockMvc.perform(post("/api/tip-estudiantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipEstudiante)))
            .andExpect(status().isBadRequest());

        // Validate the TipEstudiante in the database
        List<TipEstudiante> tipEstudianteList = tipEstudianteRepository.findAll();
        assertThat(tipEstudianteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTipIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipEstudianteRepository.findAll().size();
        // set the field null
        tipEstudiante.setTip(null);

        // Create the TipEstudiante, which fails.

        restTipEstudianteMockMvc.perform(post("/api/tip-estudiantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipEstudiante)))
            .andExpect(status().isBadRequest());

        List<TipEstudiante> tipEstudianteList = tipEstudianteRepository.findAll();
        assertThat(tipEstudianteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipEstudiantes() throws Exception {
        // Initialize the database
        tipEstudianteRepository.saveAndFlush(tipEstudiante);

        // Get all the tipEstudianteList
        restTipEstudianteMockMvc.perform(get("/api/tip-estudiantes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipEstudiante.getId().intValue())))
            .andExpect(jsonPath("$.[*].tip").value(hasItem(DEFAULT_TIP.toString())));
    }

    @Test
    @Transactional
    public void getTipEstudiante() throws Exception {
        // Initialize the database
        tipEstudianteRepository.saveAndFlush(tipEstudiante);

        // Get the tipEstudiante
        restTipEstudianteMockMvc.perform(get("/api/tip-estudiantes/{id}", tipEstudiante.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tipEstudiante.getId().intValue()))
            .andExpect(jsonPath("$.tip").value(DEFAULT_TIP.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTipEstudiante() throws Exception {
        // Get the tipEstudiante
        restTipEstudianteMockMvc.perform(get("/api/tip-estudiantes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipEstudiante() throws Exception {
        // Initialize the database
        tipEstudianteRepository.saveAndFlush(tipEstudiante);
        int databaseSizeBeforeUpdate = tipEstudianteRepository.findAll().size();

        // Update the tipEstudiante
        TipEstudiante updatedTipEstudiante = tipEstudianteRepository.findOne(tipEstudiante.getId());
        updatedTipEstudiante
            .tip(UPDATED_TIP);

        restTipEstudianteMockMvc.perform(put("/api/tip-estudiantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTipEstudiante)))
            .andExpect(status().isOk());

        // Validate the TipEstudiante in the database
        List<TipEstudiante> tipEstudianteList = tipEstudianteRepository.findAll();
        assertThat(tipEstudianteList).hasSize(databaseSizeBeforeUpdate);
        TipEstudiante testTipEstudiante = tipEstudianteList.get(tipEstudianteList.size() - 1);
        assertThat(testTipEstudiante.getTip()).isEqualTo(UPDATED_TIP);
    }

    @Test
    @Transactional
    public void updateNonExistingTipEstudiante() throws Exception {
        int databaseSizeBeforeUpdate = tipEstudianteRepository.findAll().size();

        // Create the TipEstudiante

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTipEstudianteMockMvc.perform(put("/api/tip-estudiantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipEstudiante)))
            .andExpect(status().isCreated());

        // Validate the TipEstudiante in the database
        List<TipEstudiante> tipEstudianteList = tipEstudianteRepository.findAll();
        assertThat(tipEstudianteList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTipEstudiante() throws Exception {
        // Initialize the database
        tipEstudianteRepository.saveAndFlush(tipEstudiante);
        int databaseSizeBeforeDelete = tipEstudianteRepository.findAll().size();

        // Get the tipEstudiante
        restTipEstudianteMockMvc.perform(delete("/api/tip-estudiantes/{id}", tipEstudiante.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TipEstudiante> tipEstudianteList = tipEstudianteRepository.findAll();
        assertThat(tipEstudianteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipEstudiante.class);
        TipEstudiante tipEstudiante1 = new TipEstudiante();
        tipEstudiante1.setId(1L);
        TipEstudiante tipEstudiante2 = new TipEstudiante();
        tipEstudiante2.setId(tipEstudiante1.getId());
        assertThat(tipEstudiante1).isEqualTo(tipEstudiante2);
        tipEstudiante2.setId(2L);
        assertThat(tipEstudiante1).isNotEqualTo(tipEstudiante2);
        tipEstudiante1.setId(null);
        assertThat(tipEstudiante1).isNotEqualTo(tipEstudiante2);
    }
}
