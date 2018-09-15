package rs.ac.uns.ftn.sbnz.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import rs.ac.uns.ftn.sbnz.service.MedicationService;
import rs.ac.uns.ftn.sbnz.service.mapper.MedicationMapper;
import rs.ac.uns.ftn.sbnz.web.rest.errors.BadRequestAlertException;
import rs.ac.uns.ftn.sbnz.web.rest.util.HeaderUtil;
import rs.ac.uns.ftn.sbnz.service.dto.MedicationDTO;
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

/**
 * REST controller for managing Medication.
 */
@RestController
@RequestMapping("/api")
public class MedicationResource {

    private final Logger log = LoggerFactory.getLogger(MedicationResource.class);

    private static final String ENTITY_NAME = "medication";

    private final MedicationService medicationService;

    @Autowired
    private HashMap<String, KieSession> kieSessions;

    @Autowired
    private MedicationMapper medicationMapper;

    public MedicationResource(MedicationService medicationService) {
        this.medicationService = medicationService;
    }

    /**
     * POST  /medications : Create a new medication.
     *
     * @param medicationDTO the medicationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new medicationDTO, or with status 400 (Bad Request) if the medication has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/medications")
    @Timed
    public ResponseEntity<MedicationDTO> createMedication(@Valid @RequestBody MedicationDTO medicationDTO) throws URISyntaxException {
        log.debug("REST request to save Medication : {}", medicationDTO);
        if (medicationDTO.getId() != null) {
            throw new BadRequestAlertException("A new medication cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MedicationDTO result = medicationService.save(medicationDTO);

        for (String username: kieSessions.keySet()) {
            kieSessions.get(username).insert(medicationMapper.toEntity(result));
        }

        return ResponseEntity.created(new URI("/api/medications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /medications : Updates an existing medication.
     *
     * @param medicationDTO the medicationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated medicationDTO,
     * or with status 400 (Bad Request) if the medicationDTO is not valid,
     * or with status 500 (Internal Server Error) if the medicationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/medications")
    @Timed
    public ResponseEntity<MedicationDTO> updateMedication(@Valid @RequestBody MedicationDTO medicationDTO) throws URISyntaxException {
        log.debug("REST request to update Medication : {}", medicationDTO);
        if (medicationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MedicationDTO result = medicationService.save(medicationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, medicationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /medications : get all the medications.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of medications in body
     */
    @GetMapping("/medications")
    @Timed
    public List<MedicationDTO> getAllMedications(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Medications");
        return medicationService.findAll();
    }

    /**
     * GET  /medications/:id : get the "id" medication.
     *
     * @param id the id of the medicationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the medicationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/medications/{id}")
    @Timed
    public ResponseEntity<MedicationDTO> getMedication(@PathVariable Long id) {
        log.debug("REST request to get Medication : {}", id);
        Optional<MedicationDTO> medicationDTO = medicationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(medicationDTO);
    }

    /**
     * DELETE  /medications/:id : delete the "id" medication.
     *
     * @param id the id of the medicationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/medications/{id}")
    @Timed
    public ResponseEntity<Void> deleteMedication(@PathVariable Long id) {
        log.debug("REST request to delete Medication : {}", id);
        medicationService.delete(id);
        medicationService.retractFromSession(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
