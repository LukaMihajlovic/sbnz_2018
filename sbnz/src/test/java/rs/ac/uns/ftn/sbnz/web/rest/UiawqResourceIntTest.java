package rs.ac.uns.ftn.sbnz.web.rest;

import rs.ac.uns.ftn.sbnz.SbnzApp;

import rs.ac.uns.ftn.sbnz.domain.Uiawq;
import rs.ac.uns.ftn.sbnz.repository.UiawqRepository;
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
 * Test class for the UiawqResource REST controller.
 *
 * @see UiawqResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SbnzApp.class)
public class UiawqResourceIntTest {

    private static final String DEFAULT_QWE = "AAAAAAAAAA";
    private static final String UPDATED_QWE = "BBBBBBBBBB";

    @Autowired
    private UiawqRepository uiawqRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUiawqMockMvc;

    private Uiawq uiawq;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UiawqResource uiawqResource = new UiawqResource(uiawqRepository);
        this.restUiawqMockMvc = MockMvcBuilders.standaloneSetup(uiawqResource)
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
    public static Uiawq createEntity(EntityManager em) {
        Uiawq uiawq = new Uiawq()
            .qwe(DEFAULT_QWE);
        return uiawq;
    }

    @Before
    public void initTest() {
        uiawq = createEntity(em);
    }

    @Test
    @Transactional
    public void createUiawq() throws Exception {
        int databaseSizeBeforeCreate = uiawqRepository.findAll().size();

        // Create the Uiawq
        restUiawqMockMvc.perform(post("/api/uiawqs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uiawq)))
            .andExpect(status().isCreated());

        // Validate the Uiawq in the database
        List<Uiawq> uiawqList = uiawqRepository.findAll();
        assertThat(uiawqList).hasSize(databaseSizeBeforeCreate + 1);
        Uiawq testUiawq = uiawqList.get(uiawqList.size() - 1);
        assertThat(testUiawq.getQwe()).isEqualTo(DEFAULT_QWE);
    }

    @Test
    @Transactional
    public void createUiawqWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = uiawqRepository.findAll().size();

        // Create the Uiawq with an existing ID
        uiawq.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUiawqMockMvc.perform(post("/api/uiawqs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uiawq)))
            .andExpect(status().isBadRequest());

        // Validate the Uiawq in the database
        List<Uiawq> uiawqList = uiawqRepository.findAll();
        assertThat(uiawqList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUiawqs() throws Exception {
        // Initialize the database
        uiawqRepository.saveAndFlush(uiawq);

        // Get all the uiawqList
        restUiawqMockMvc.perform(get("/api/uiawqs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(uiawq.getId().intValue())))
            .andExpect(jsonPath("$.[*].qwe").value(hasItem(DEFAULT_QWE.toString())));
    }

    @Test
    @Transactional
    public void getUiawq() throws Exception {
        // Initialize the database
        uiawqRepository.saveAndFlush(uiawq);

        // Get the uiawq
        restUiawqMockMvc.perform(get("/api/uiawqs/{id}", uiawq.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(uiawq.getId().intValue()))
            .andExpect(jsonPath("$.qwe").value(DEFAULT_QWE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUiawq() throws Exception {
        // Get the uiawq
        restUiawqMockMvc.perform(get("/api/uiawqs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUiawq() throws Exception {
        // Initialize the database
        uiawqRepository.saveAndFlush(uiawq);
        int databaseSizeBeforeUpdate = uiawqRepository.findAll().size();

        // Update the uiawq
        Uiawq updatedUiawq = uiawqRepository.findOne(uiawq.getId());
        // Disconnect from session so that the updates on updatedUiawq are not directly saved in db
        em.detach(updatedUiawq);
        updatedUiawq
            .qwe(UPDATED_QWE);

        restUiawqMockMvc.perform(put("/api/uiawqs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUiawq)))
            .andExpect(status().isOk());

        // Validate the Uiawq in the database
        List<Uiawq> uiawqList = uiawqRepository.findAll();
        assertThat(uiawqList).hasSize(databaseSizeBeforeUpdate);
        Uiawq testUiawq = uiawqList.get(uiawqList.size() - 1);
        assertThat(testUiawq.getQwe()).isEqualTo(UPDATED_QWE);
    }

    @Test
    @Transactional
    public void updateNonExistingUiawq() throws Exception {
        int databaseSizeBeforeUpdate = uiawqRepository.findAll().size();

        // Create the Uiawq

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUiawqMockMvc.perform(put("/api/uiawqs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uiawq)))
            .andExpect(status().isCreated());

        // Validate the Uiawq in the database
        List<Uiawq> uiawqList = uiawqRepository.findAll();
        assertThat(uiawqList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUiawq() throws Exception {
        // Initialize the database
        uiawqRepository.saveAndFlush(uiawq);
        int databaseSizeBeforeDelete = uiawqRepository.findAll().size();

        // Get the uiawq
        restUiawqMockMvc.perform(delete("/api/uiawqs/{id}", uiawq.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Uiawq> uiawqList = uiawqRepository.findAll();
        assertThat(uiawqList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Uiawq.class);
        Uiawq uiawq1 = new Uiawq();
        uiawq1.setId(1L);
        Uiawq uiawq2 = new Uiawq();
        uiawq2.setId(uiawq1.getId());
        assertThat(uiawq1).isEqualTo(uiawq2);
        uiawq2.setId(2L);
        assertThat(uiawq1).isNotEqualTo(uiawq2);
        uiawq1.setId(null);
        assertThat(uiawq1).isNotEqualTo(uiawq2);
    }
}
