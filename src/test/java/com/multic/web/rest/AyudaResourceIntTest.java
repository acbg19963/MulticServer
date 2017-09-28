package com.multic.web.rest;

import com.multic.MulticApp;

import com.multic.domain.Ayuda;
import com.multic.repository.AyudaRepository;
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
 * Test class for the AyudaResource REST controller.
 *
 * @see AyudaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MulticApp.class)
public class AyudaResourceIntTest {

    private static final String DEFAULT_ENUNCIADO = "AAAAAAAAAA";
    private static final String UPDATED_ENUNCIADO = "BBBBBBBBBB";

    @Autowired
    private AyudaRepository ayudaRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAyudaMockMvc;

    private Ayuda ayuda;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AyudaResource ayudaResource = new AyudaResource(ayudaRepository);
        this.restAyudaMockMvc = MockMvcBuilders.standaloneSetup(ayudaResource)
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
    public static Ayuda createEntity(EntityManager em) {
        Ayuda ayuda = new Ayuda()
            .enunciado(DEFAULT_ENUNCIADO);
        return ayuda;
    }

    @Before
    public void initTest() {
        ayuda = createEntity(em);
    }

    @Test
    @Transactional
    public void createAyuda() throws Exception {
        int databaseSizeBeforeCreate = ayudaRepository.findAll().size();

        // Create the Ayuda
        restAyudaMockMvc.perform(post("/api/ayudas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ayuda)))
            .andExpect(status().isCreated());

        // Validate the Ayuda in the database
        List<Ayuda> ayudaList = ayudaRepository.findAll();
        assertThat(ayudaList).hasSize(databaseSizeBeforeCreate + 1);
        Ayuda testAyuda = ayudaList.get(ayudaList.size() - 1);
        assertThat(testAyuda.getEnunciado()).isEqualTo(DEFAULT_ENUNCIADO);
    }

    @Test
    @Transactional
    public void createAyudaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ayudaRepository.findAll().size();

        // Create the Ayuda with an existing ID
        ayuda.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAyudaMockMvc.perform(post("/api/ayudas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ayuda)))
            .andExpect(status().isBadRequest());

        // Validate the Ayuda in the database
        List<Ayuda> ayudaList = ayudaRepository.findAll();
        assertThat(ayudaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAyudas() throws Exception {
        // Initialize the database
        ayudaRepository.saveAndFlush(ayuda);

        // Get all the ayudaList
        restAyudaMockMvc.perform(get("/api/ayudas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ayuda.getId().intValue())))
            .andExpect(jsonPath("$.[*].enunciado").value(hasItem(DEFAULT_ENUNCIADO.toString())));
    }

    @Test
    @Transactional
    public void getAyuda() throws Exception {
        // Initialize the database
        ayudaRepository.saveAndFlush(ayuda);

        // Get the ayuda
        restAyudaMockMvc.perform(get("/api/ayudas/{id}", ayuda.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ayuda.getId().intValue()))
            .andExpect(jsonPath("$.enunciado").value(DEFAULT_ENUNCIADO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAyuda() throws Exception {
        // Get the ayuda
        restAyudaMockMvc.perform(get("/api/ayudas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAyuda() throws Exception {
        // Initialize the database
        ayudaRepository.saveAndFlush(ayuda);
        int databaseSizeBeforeUpdate = ayudaRepository.findAll().size();

        // Update the ayuda
        Ayuda updatedAyuda = ayudaRepository.findOne(ayuda.getId());
        updatedAyuda
            .enunciado(UPDATED_ENUNCIADO);

        restAyudaMockMvc.perform(put("/api/ayudas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAyuda)))
            .andExpect(status().isOk());

        // Validate the Ayuda in the database
        List<Ayuda> ayudaList = ayudaRepository.findAll();
        assertThat(ayudaList).hasSize(databaseSizeBeforeUpdate);
        Ayuda testAyuda = ayudaList.get(ayudaList.size() - 1);
        assertThat(testAyuda.getEnunciado()).isEqualTo(UPDATED_ENUNCIADO);
    }

    @Test
    @Transactional
    public void updateNonExistingAyuda() throws Exception {
        int databaseSizeBeforeUpdate = ayudaRepository.findAll().size();

        // Create the Ayuda

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAyudaMockMvc.perform(put("/api/ayudas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ayuda)))
            .andExpect(status().isCreated());

        // Validate the Ayuda in the database
        List<Ayuda> ayudaList = ayudaRepository.findAll();
        assertThat(ayudaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAyuda() throws Exception {
        // Initialize the database
        ayudaRepository.saveAndFlush(ayuda);
        int databaseSizeBeforeDelete = ayudaRepository.findAll().size();

        // Get the ayuda
        restAyudaMockMvc.perform(delete("/api/ayudas/{id}", ayuda.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Ayuda> ayudaList = ayudaRepository.findAll();
        assertThat(ayudaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ayuda.class);
        Ayuda ayuda1 = new Ayuda();
        ayuda1.setId(1L);
        Ayuda ayuda2 = new Ayuda();
        ayuda2.setId(ayuda1.getId());
        assertThat(ayuda1).isEqualTo(ayuda2);
        ayuda2.setId(2L);
        assertThat(ayuda1).isNotEqualTo(ayuda2);
        ayuda1.setId(null);
        assertThat(ayuda1).isNotEqualTo(ayuda2);
    }
}
