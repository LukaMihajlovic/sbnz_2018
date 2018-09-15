package rs.ac.uns.ftn.sbnz.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import rs.ac.uns.ftn.sbnz.domain.Diagnosis;
import rs.ac.uns.ftn.sbnz.service.DiagnosisService;
import rs.ac.uns.ftn.sbnz.service.MedicalConditionService;
import rs.ac.uns.ftn.sbnz.service.dto.*;
import rs.ac.uns.ftn.sbnz.service.mapper.MedicalConditionMapper;
import rs.ac.uns.ftn.sbnz.web.rest.errors.BadRequestAlertException;
import rs.ac.uns.ftn.sbnz.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * REST controller for managing MedicalCondition.
 */
@RestController
@RequestMapping("/api")
public class MedicalConditionResource {

    private final Logger log = LoggerFactory.getLogger(MedicalConditionResource.class);

    private static final String ENTITY_NAME = "medicalCondition";

    private final MedicalConditionService medicalConditionService;

    @Autowired
    private HashMap<String, KieSession> kieSessions;

    @Autowired
    private MedicalConditionMapper medicalConditionMapper;

    @Autowired
    private DiagnosisService diagnosisService;

    public MedicalConditionResource(MedicalConditionService medicalConditionService) {
        this.medicalConditionService = medicalConditionService;
    }

    /**
     * POST  /medical-conditions : Create a new medicalCondition.
     *
     * @param medicalConditionDTO the medicalConditionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new medicalConditionDTO, or with status 400 (Bad Request) if the medicalCondition has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/medical-conditions")
    @Timed
    public ResponseEntity<MedicalConditionDTO> createMedicalCondition(@Valid @RequestBody MedicalConditionDTO medicalConditionDTO) throws URISyntaxException {
        log.debug("REST request to save MedicalCondition : {}", medicalConditionDTO);
        if (medicalConditionDTO.getId() != null) {
            throw new BadRequestAlertException("A new medicalCondition cannot already have an ID", ENTITY_NAME, "idexists");
        }

        MedicalConditionDTO result = medicalConditionService.save(medicalConditionDTO);

        for (String username: kieSessions.keySet()) {
            kieSessions.get(username).insert(medicalConditionMapper.toEntity(result));
        }

        return ResponseEntity.created(new URI("/api/medical-conditions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /medical-conditions : Updates an existing medicalCondition.
     *
     * @param medicalConditionDTO the medicalConditionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated medicalConditionDTO,
     * or with status 400 (Bad Request) if the medicalConditionDTO is not valid,
     * or with status 500 (Internal Server Error) if the medicalConditionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/medical-conditions")
    @Timed
    public ResponseEntity<MedicalConditionDTO> updateMedicalCondition(@Valid @RequestBody MedicalConditionDTO medicalConditionDTO) throws URISyntaxException {
        log.debug("REST request to update MedicalCondition : {}", medicalConditionDTO);
        if (medicalConditionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MedicalConditionDTO result = medicalConditionService.save(medicalConditionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, medicalConditionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /medical-conditions : get all the medicalConditions.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of medicalConditions in body
     */
    @GetMapping("/medical-conditions")
    @Timed
    public List<MedicalConditionDTO> getAllMedicalConditions(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all MedicalConditions");
        return medicalConditionService.findAll();
    }

    @GetMapping("/medical-conditions/enquiry/{conditionName}")
    @Timed
    public List<SymptomDTO> findSymptomsForMedicalCondition(@PathVariable String conditionName) {
        log.debug("REST request to find symptoms for medical condition");
        return medicalConditionService.findSymptomsForMedicalConditionName(conditionName);
    }

    @PutMapping("/medical-conditions/enquiry")
    @Timed
    public List<EnquiryResult> findMedicalConditionsForSymptoms(@RequestBody Enquiry enquiry) {
        log.debug("REST request to find medical conditions for symptoms");
        return medicalConditionService.findMedicalConditionsForSymptoms(enquiry);
    }

    /**
     * GET  /medical-conditions/:id : get the "id" medicalCondition.
     *
     * @param id the id of the medicalConditionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the medicalConditionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/medical-conditions/{id}")
    @Timed
    public ResponseEntity<MedicalConditionDTO> getMedicalCondition(@PathVariable Long id) {
        log.debug("REST request to get MedicalCondition : {}", id);
        Optional<MedicalConditionDTO> medicalConditionDTO = medicalConditionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(medicalConditionDTO);
    }

    /**
     * DELETE  /medical-conditions/:id : delete the "id" medicalCondition.
     *
     * @param id the id of the medicalConditionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/medical-conditions/{id}")
    @Timed
    public ResponseEntity<Void> deleteMedicalCondition(@PathVariable Long id) {
        log.debug("REST request to delete MedicalCondition : {}", id);

        medicalConditionService.delete(id);
        medicalConditionService.retractFromSession(id);

        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
