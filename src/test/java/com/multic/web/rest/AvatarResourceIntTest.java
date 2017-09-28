package com.multic.web.rest;

import com.multic.MulticApp;

import com.multic.domain.Avatar;
import com.multic.repository.AvatarRepository;
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
 * Test class for the AvatarResource REST controller.
 *
 * @see AvatarResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MulticApp.class)
public class AvatarResourceIntTest {

    private static final Integer DEFAULT_MONEDAS = 1;
    private static final Integer UPDATED_MONEDAS = 2;

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_CABELLO = "AAAAAAAAAA";
    private static final String UPDATED_CABELLO = "BBBBBBBBBB";

    private static final String DEFAULT_ROPA = "AAAAAAAAAA";
    private static final String UPDATED_ROPA = "BBBBBBBBBB";

    private static final String DEFAULT_GAFAS = "AAAAAAAAAA";
    private static final String UPDATED_GAFAS = "BBBBBBBBBB";

    @Autowired
    private AvatarRepository avatarRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAvatarMockMvc;

    private Avatar avatar;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AvatarResource avatarResource = new AvatarResource(avatarRepository);
        this.restAvatarMockMvc = MockMvcBuilders.standaloneSetup(avatarResource)
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
    public static Avatar createEntity(EntityManager em) {
        Avatar avatar = new Avatar()
            .monedas(DEFAULT_MONEDAS)
            .nombre(DEFAULT_NOMBRE)
            .cabello(DEFAULT_CABELLO)
            .ropa(DEFAULT_ROPA)
            .gafas(DEFAULT_GAFAS);
        return avatar;
    }

    @Before
    public void initTest() {
        avatar = createEntity(em);
    }

    @Test
    @Transactional
    public void createAvatar() throws Exception {
        int databaseSizeBeforeCreate = avatarRepository.findAll().size();

        // Create the Avatar
        restAvatarMockMvc.perform(post("/api/avatars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(avatar)))
            .andExpect(status().isCreated());

        // Validate the Avatar in the database
        List<Avatar> avatarList = avatarRepository.findAll();
        assertThat(avatarList).hasSize(databaseSizeBeforeCreate + 1);
        Avatar testAvatar = avatarList.get(avatarList.size() - 1);
        assertThat(testAvatar.getMonedas()).isEqualTo(DEFAULT_MONEDAS);
        assertThat(testAvatar.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testAvatar.getCabello()).isEqualTo(DEFAULT_CABELLO);
        assertThat(testAvatar.getRopa()).isEqualTo(DEFAULT_ROPA);
        assertThat(testAvatar.getGafas()).isEqualTo(DEFAULT_GAFAS);
    }

    @Test
    @Transactional
    public void createAvatarWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = avatarRepository.findAll().size();

        // Create the Avatar with an existing ID
        avatar.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAvatarMockMvc.perform(post("/api/avatars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(avatar)))
            .andExpect(status().isBadRequest());

        // Validate the Avatar in the database
        List<Avatar> avatarList = avatarRepository.findAll();
        assertThat(avatarList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAvatars() throws Exception {
        // Initialize the database
        avatarRepository.saveAndFlush(avatar);

        // Get all the avatarList
        restAvatarMockMvc.perform(get("/api/avatars?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(avatar.getId().intValue())))
            .andExpect(jsonPath("$.[*].monedas").value(hasItem(DEFAULT_MONEDAS)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].cabello").value(hasItem(DEFAULT_CABELLO.toString())))
            .andExpect(jsonPath("$.[*].ropa").value(hasItem(DEFAULT_ROPA.toString())))
            .andExpect(jsonPath("$.[*].gafas").value(hasItem(DEFAULT_GAFAS.toString())));
    }

    @Test
    @Transactional
    public void getAvatar() throws Exception {
        // Initialize the database
        avatarRepository.saveAndFlush(avatar);

        // Get the avatar
        restAvatarMockMvc.perform(get("/api/avatars/{id}", avatar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(avatar.getId().intValue()))
            .andExpect(jsonPath("$.monedas").value(DEFAULT_MONEDAS))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.cabello").value(DEFAULT_CABELLO.toString()))
            .andExpect(jsonPath("$.ropa").value(DEFAULT_ROPA.toString()))
            .andExpect(jsonPath("$.gafas").value(DEFAULT_GAFAS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAvatar() throws Exception {
        // Get the avatar
        restAvatarMockMvc.perform(get("/api/avatars/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAvatar() throws Exception {
        // Initialize the database
        avatarRepository.saveAndFlush(avatar);
        int databaseSizeBeforeUpdate = avatarRepository.findAll().size();

        // Update the avatar
        Avatar updatedAvatar = avatarRepository.findOne(avatar.getId());
        updatedAvatar
            .monedas(UPDATED_MONEDAS)
            .nombre(UPDATED_NOMBRE)
            .cabello(UPDATED_CABELLO)
            .ropa(UPDATED_ROPA)
            .gafas(UPDATED_GAFAS);

        restAvatarMockMvc.perform(put("/api/avatars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAvatar)))
            .andExpect(status().isOk());

        // Validate the Avatar in the database
        List<Avatar> avatarList = avatarRepository.findAll();
        assertThat(avatarList).hasSize(databaseSizeBeforeUpdate);
        Avatar testAvatar = avatarList.get(avatarList.size() - 1);
        assertThat(testAvatar.getMonedas()).isEqualTo(UPDATED_MONEDAS);
        assertThat(testAvatar.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testAvatar.getCabello()).isEqualTo(UPDATED_CABELLO);
        assertThat(testAvatar.getRopa()).isEqualTo(UPDATED_ROPA);
        assertThat(testAvatar.getGafas()).isEqualTo(UPDATED_GAFAS);
    }

    @Test
    @Transactional
    public void updateNonExistingAvatar() throws Exception {
        int databaseSizeBeforeUpdate = avatarRepository.findAll().size();

        // Create the Avatar

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAvatarMockMvc.perform(put("/api/avatars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(avatar)))
            .andExpect(status().isCreated());

        // Validate the Avatar in the database
        List<Avatar> avatarList = avatarRepository.findAll();
        assertThat(avatarList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAvatar() throws Exception {
        // Initialize the database
        avatarRepository.saveAndFlush(avatar);
        int databaseSizeBeforeDelete = avatarRepository.findAll().size();

        // Get the avatar
        restAvatarMockMvc.perform(delete("/api/avatars/{id}", avatar.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Avatar> avatarList = avatarRepository.findAll();
        assertThat(avatarList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Avatar.class);
        Avatar avatar1 = new Avatar();
        avatar1.setId(1L);
        Avatar avatar2 = new Avatar();
        avatar2.setId(avatar1.getId());
        assertThat(avatar1).isEqualTo(avatar2);
        avatar2.setId(2L);
        assertThat(avatar1).isNotEqualTo(avatar2);
        avatar1.setId(null);
        assertThat(avatar1).isNotEqualTo(avatar2);
    }
}
