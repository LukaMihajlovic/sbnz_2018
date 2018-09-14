package rs.ac.uns.ftn.sbnz.web.rest;

import rs.ac.uns.ftn.sbnz.SbnzApp;

import rs.ac.uns.ftn.sbnz.domain.Anamnesis;
import rs.ac.uns.ftn.sbnz.repository.AnamnesisRepository;
import rs.ac.uns.ftn.sbnz.service.AnamnesisService;
import rs.ac.uns.ftn.sbnz.web.rest.errors.ExceptionTranslator;

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

import static rs.ac.uns.ftn.sbnz.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AnamnesisResource REST controller.
 *
 * @see AnamnesisResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SbnzApp.class)
public class AnamnesisResourceIntTest {

    private static final String DEFAULT_JMBG = "AAAAAAAAAA";
    private static final String UPDATED_JMBG = "BBBBBBBBBB";

    @Autowired
    private AnamnesisRepository anamnesisRepository;

    @Autowired
    private AnamnesisService anamnesisService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAnamnesisMockMvc;

    private Anamnesis anamnesis;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AnamnesisResource anamnesisResource = new AnamnesisResource(anamnesisService);
        this.restAnamnesisMockMvc = MockMvcBuilders.standaloneSetup(anamnesisResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Anamnesis createEntity(EntityManager em) {
        Anamnesis anamnesis = new Anamnesis()
            .jmbg(DEFAULT_JMBG);
        return anamnesis;
    }

    @Before
    public void initTest() {
        anamnesis = createEntity(em);
    }

    @Test
    @Transactional
    public void createAnamnesis() throws Exception {
        int databaseSizeBeforeCreate = anamnesisRepository.findAll().size();

        // Create the Anamnesis
        restAnamnesisMockMvc.perform(post("/api/anamneses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anamnesis)))
            .andExpect(status().isCreated());

        // Validate the Anamnesis in the database
        List<Anamnesis> anamnesisList = anamnesisRepository.findAll();
        assertThat(anamnesisList).hasSize(databaseSizeBeforeCreate + 1);
        Anamnesis testAnamnesis = anamnesisList.get(anamnesisList.size() - 1);
        assertThat(testAnamnesis.getJmbg()).isEqualTo(DEFAULT_JMBG);
    }

    @Test
    @Transactional
    public void createAnamnesisWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = anamnesisRepository.findAll().size();

        // Create the Anamnesis with an existing ID
        anamnesis.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnamnesisMockMvc.perform(post("/api/anamneses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anamnesis)))
            .andExpect(status().isBadRequest());

        // Validate the Anamnesis in the database
        List<Anamnesis> anamnesisList = anamnesisRepository.findAll();
        assertThat(anamnesisList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAnamneses() throws Exception {
        // Initialize the database
        anamnesisRepository.saveAndFlush(anamnesis);

        // Get all the anamnesisList
        restAnamnesisMockMvc.perform(get("/api/anamneses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(anamnesis.getId().intValue())))
            .andExpect(jsonPath("$.[*].jmbg").value(hasItem(DEFAULT_JMBG.toString())));
    }

    @Test
    @Transactional
    public void getAnamnesis() throws Exception {
        // Initialize the database
        anamnesisRepository.saveAndFlush(anamnesis);

        // Get the anamnesis
        restAnamnesisMockMvc.perform(get("/api/anamneses/{id}", anamnesis.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(anamnesis.getId().intValue()))
            .andExpect(jsonPath("$.jmbg").value(DEFAULT_JMBG.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAnamnesis() throws Exception {
        // Get the anamnesis
        restAnamnesisMockMvc.perform(get("/api/anamneses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAnamnesis() throws Exception {
        // Initialize the database
        anamnesisService.save(anamnesis);

        int databaseSizeBeforeUpdate = anamnesisRepository.findAll().size();

        // Update the anamnesis
        Anamnesis updatedAnamnesis = anamnesisRepository.findOne(anamnesis.getId());
        // Disconnect from session so that the updates on updatedAnamnesis are not directly saved in db
        em.detach(updatedAnamnesis);
        updatedAnamnesis
            .jmbg(UPDATED_JMBG);

        restAnamnesisMockMvc.perform(put("/api/anamneses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAnamnesis)))
            .andExpect(status().isOk());

        // Validate the Anamnesis in the database
        List<Anamnesis> anamnesisList = anamnesisRepository.findAll();
        assertThat(anamnesisList).hasSize(databaseSizeBeforeUpdate);
        Anamnesis testAnamnesis = anamnesisList.get(anamnesisList.size() - 1);
        assertThat(testAnamnesis.getJmbg()).isEqualTo(UPDATED_JMBG);
    }

    @Test
    @Transactional
    public void updateNonExistingAnamnesis() throws Exception {
        int databaseSizeBeforeUpdate = anamnesisRepository.findAll().size();

        // Create the Anamnesis

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAnamnesisMockMvc.perform(put("/api/anamneses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anamnesis)))
            .andExpect(status().isCreated());

        // Validate the Anamnesis in the database
        List<Anamnesis> anamnesisList = anamnesisRepository.findAll();
        assertThat(anamnesisList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAnamnesis() throws Exception {
        // Initialize the database
        anamnesisService.save(anamnesis);

        int databaseSizeBeforeDelete = anamnesisRepository.findAll().size();

        // Get the anamnesis
        restAnamnesisMockMvc.perform(delete("/api/anamneses/{id}", anamnesis.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Anamnesis> anamnesisList = anamnesisRepository.findAll();
        assertThat(anamnesisList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Anamnesis.class);
        Anamnesis anamnesis1 = new Anamnesis();
        anamnesis1.setId(1L);
        Anamnesis anamnesis2 = new Anamnesis();
        anamnesis2.setId(anamnesis1.getId());
        assertThat(anamnesis1).isEqualTo(anamnesis2);
        anamnesis2.setId(2L);
        assertThat(anamnesis1).isNotEqualTo(anamnesis2);
        anamnesis1.setId(null);
        assertThat(anamnesis1).isNotEqualTo(anamnesis2);
    }
}
