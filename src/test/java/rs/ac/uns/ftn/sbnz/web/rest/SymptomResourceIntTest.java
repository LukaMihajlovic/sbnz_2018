package rs.ac.uns.ftn.sbnz.web.rest;

import rs.ac.uns.ftn.sbnz.SbnzApp;

import rs.ac.uns.ftn.sbnz.domain.Symptom;
import rs.ac.uns.ftn.sbnz.repository.SymptomRepository;
import rs.ac.uns.ftn.sbnz.service.SymptomService;
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
 * Test class for the SymptomResource REST controller.
 *
 * @see SymptomResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SbnzApp.class)
public class SymptomResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Float DEFAULT_LOW = 1F;
    private static final Float UPDATED_LOW = 2F;

    private static final Float DEFAULT_HIGH = 1F;
    private static final Float UPDATED_HIGH = 2F;

    private static final Boolean DEFAULT_SPEC = false;
    private static final Boolean UPDATED_SPEC = true;

    @Autowired
    private SymptomRepository symptomRepository;

    @Autowired
    private SymptomService symptomService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSymptomMockMvc;

    private Symptom symptom;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SymptomResource symptomResource = new SymptomResource(symptomService);
        this.restSymptomMockMvc = MockMvcBuilders.standaloneSetup(symptomResource)
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
    public static Symptom createEntity(EntityManager em) {
        Symptom symptom = new Symptom()
            .name(DEFAULT_NAME)
            .low(DEFAULT_LOW)
            .high(DEFAULT_HIGH)
            .spec(DEFAULT_SPEC);
        return symptom;
    }

    @Before
    public void initTest() {
        symptom = createEntity(em);
    }

    @Test
    @Transactional
    public void createSymptom() throws Exception {
        int databaseSizeBeforeCreate = symptomRepository.findAll().size();

        // Create the Symptom
        restSymptomMockMvc.perform(post("/api/symptoms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(symptom)))
            .andExpect(status().isCreated());

        // Validate the Symptom in the database
        List<Symptom> symptomList = symptomRepository.findAll();
        assertThat(symptomList).hasSize(databaseSizeBeforeCreate + 1);
        Symptom testSymptom = symptomList.get(symptomList.size() - 1);
        assertThat(testSymptom.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSymptom.getLow()).isEqualTo(DEFAULT_LOW);
        assertThat(testSymptom.getHigh()).isEqualTo(DEFAULT_HIGH);
        assertThat(testSymptom.isSpec()).isEqualTo(DEFAULT_SPEC);
    }

    @Test
    @Transactional
    public void createSymptomWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = symptomRepository.findAll().size();

        // Create the Symptom with an existing ID
        symptom.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSymptomMockMvc.perform(post("/api/symptoms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(symptom)))
            .andExpect(status().isBadRequest());

        // Validate the Symptom in the database
        List<Symptom> symptomList = symptomRepository.findAll();
        assertThat(symptomList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSymptoms() throws Exception {
        // Initialize the database
        symptomRepository.saveAndFlush(symptom);

        // Get all the symptomList
        restSymptomMockMvc.perform(get("/api/symptoms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(symptom.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].low").value(hasItem(DEFAULT_LOW.doubleValue())))
            .andExpect(jsonPath("$.[*].high").value(hasItem(DEFAULT_HIGH.doubleValue())))
            .andExpect(jsonPath("$.[*].spec").value(hasItem(DEFAULT_SPEC.booleanValue())));
    }

    @Test
    @Transactional
    public void getSymptom() throws Exception {
        // Initialize the database
        symptomRepository.saveAndFlush(symptom);

        // Get the symptom
        restSymptomMockMvc.perform(get("/api/symptoms/{id}", symptom.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(symptom.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.low").value(DEFAULT_LOW.doubleValue()))
            .andExpect(jsonPath("$.high").value(DEFAULT_HIGH.doubleValue()))
            .andExpect(jsonPath("$.spec").value(DEFAULT_SPEC.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSymptom() throws Exception {
        // Get the symptom
        restSymptomMockMvc.perform(get("/api/symptoms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSymptom() throws Exception {
        // Initialize the database
        symptomService.save(symptom);

        int databaseSizeBeforeUpdate = symptomRepository.findAll().size();

        // Update the symptom
        Symptom updatedSymptom = symptomRepository.findOne(symptom.getId());
        // Disconnect from session so that the updates on updatedSymptom are not directly saved in db
        em.detach(updatedSymptom);
        updatedSymptom
            .name(UPDATED_NAME)
            .low(UPDATED_LOW)
            .high(UPDATED_HIGH)
            .spec(UPDATED_SPEC);

        restSymptomMockMvc.perform(put("/api/symptoms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSymptom)))
            .andExpect(status().isOk());

        // Validate the Symptom in the database
        List<Symptom> symptomList = symptomRepository.findAll();
        assertThat(symptomList).hasSize(databaseSizeBeforeUpdate);
        Symptom testSymptom = symptomList.get(symptomList.size() - 1);
        assertThat(testSymptom.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSymptom.getLow()).isEqualTo(UPDATED_LOW);
        assertThat(testSymptom.getHigh()).isEqualTo(UPDATED_HIGH);
        assertThat(testSymptom.isSpec()).isEqualTo(UPDATED_SPEC);
    }

    @Test
    @Transactional
    public void updateNonExistingSymptom() throws Exception {
        int databaseSizeBeforeUpdate = symptomRepository.findAll().size();

        // Create the Symptom

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSymptomMockMvc.perform(put("/api/symptoms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(symptom)))
            .andExpect(status().isCreated());

        // Validate the Symptom in the database
        List<Symptom> symptomList = symptomRepository.findAll();
        assertThat(symptomList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSymptom() throws Exception {
        // Initialize the database
        symptomService.save(symptom);

        int databaseSizeBeforeDelete = symptomRepository.findAll().size();

        // Get the symptom
        restSymptomMockMvc.perform(delete("/api/symptoms/{id}", symptom.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Symptom> symptomList = symptomRepository.findAll();
        assertThat(symptomList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Symptom.class);
        Symptom symptom1 = new Symptom();
        symptom1.setId(1L);
        Symptom symptom2 = new Symptom();
        symptom2.setId(symptom1.getId());
        assertThat(symptom1).isEqualTo(symptom2);
        symptom2.setId(2L);
        assertThat(symptom1).isNotEqualTo(symptom2);
        symptom1.setId(null);
        assertThat(symptom1).isNotEqualTo(symptom2);
    }
}
