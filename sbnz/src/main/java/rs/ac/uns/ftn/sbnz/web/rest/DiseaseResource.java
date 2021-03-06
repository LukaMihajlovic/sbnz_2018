package rs.ac.uns.ftn.sbnz.web.rest;

import com.codahale.metrics.annotation.Timed;
import rs.ac.uns.ftn.sbnz.domain.Disease;
import rs.ac.uns.ftn.sbnz.domain.SFToClient;
import rs.ac.uns.ftn.sbnz.domain.Symptom;
import rs.ac.uns.ftn.sbnz.domain.SymptomFinder;
import rs.ac.uns.ftn.sbnz.service.DiseaseService;
import rs.ac.uns.ftn.sbnz.web.rest.errors.BadRequestAlertException;
import rs.ac.uns.ftn.sbnz.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Disease.
 */
@RestController
@RequestMapping("/api")
public class DiseaseResource {

    private final Logger log = LoggerFactory.getLogger(DiseaseResource.class);

    private static final String ENTITY_NAME = "disease";

    private final DiseaseService diseaseService;

    public DiseaseResource(DiseaseService diseaseService) {
        this.diseaseService = diseaseService;
    }

    /**
     * POST  /diseases : Create a new disease.
     *
     * @param disease the disease to create
     * @return the ResponseEntity with status 201 (Created) and with body the new disease, or with status 400 (Bad Request) if the disease has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/diseases")
    @Timed
    public ResponseEntity<Disease> createDisease(@RequestBody Disease disease) throws URISyntaxException {
        log.debug("REST request to save Disease : {}", disease);
        if (disease.getId() != null) {
            throw new BadRequestAlertException("A new disease cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Disease result = diseaseService.save(disease);
        return ResponseEntity.created(new URI("/api/diseases/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /diseases : Updates an existing disease.
     *
     * @param disease the disease to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated disease,
     * or with status 400 (Bad Request) if the disease is not valid,
     * or with status 500 (Internal Server Error) if the disease couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/diseases")
    @Timed
    public ResponseEntity<Disease> updateDisease(@RequestBody Disease disease) throws URISyntaxException {
        log.debug("REST request to update Disease : {}", disease);
        if (disease.getId() == null) {
            return createDisease(disease);
        }
        Disease result = diseaseService.save(disease);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, disease.getId().toString()))
            .body(result);
    }

    /**
     * GET  /diseases : get all the diseases.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of diseases in body
     */
    @GetMapping("/diseases")
    @Timed
    public List<Disease> getAllDiseases() {
        log.debug("REST request to get all Diseases");
        return diseaseService.findAll();
        }

    /**
     * GET  /diseases/:id : get the "id" disease.
     *
     * @param id the id of the disease to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the disease, or with status 404 (Not Found)
     */
    @GetMapping("/diseases/{id}")
    @Timed
    public ResponseEntity<Disease> getDisease(@PathVariable Long id) {
        log.debug("REST request to get Disease : {}", id);
        Disease disease = diseaseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(disease));
    }


    @PutMapping("/diseases-symptoms")
    @Timed
    public List<SFToClient> findDiseases(@RequestBody SymptomFinder sf) {
        List<SFToClient> retVal = diseaseService.findDiseaseGivenSymptoms(sf);
        return retVal;
    }

    @GetMapping("/diseases-symptoms/{diseaseName}")
    @Timed
    public List<Symptom> findSymptoms(@PathVariable String diseaseName) {
        log.debug("REST request to find symptoms for medical condition");
        return diseaseService.findSymptomsFromGivenDisease(diseaseName);
    }


    /**
     * DELETE  /diseases/:id : delete the "id" disease.
     *
     * @param id the id of the disease to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/diseases/{id}")
    @Timed
    public ResponseEntity<Void> deleteDisease(@PathVariable Long id) {
        log.debug("REST request to delete Disease : {}", id);
        diseaseService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
