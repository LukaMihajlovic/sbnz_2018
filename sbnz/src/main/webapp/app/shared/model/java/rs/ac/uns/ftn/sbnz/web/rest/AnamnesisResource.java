package rs.ac.uns.ftn.sbnz.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import rs.ac.uns.ftn.sbnz.domain.Anamnesis;
import rs.ac.uns.ftn.sbnz.domain.Diagnosis;
import rs.ac.uns.ftn.sbnz.service.AnamnesisService;
import rs.ac.uns.ftn.sbnz.service.DiagnosisService;
import rs.ac.uns.ftn.sbnz.web.rest.errors.BadRequestAlertException;
import rs.ac.uns.ftn.sbnz.web.rest.util.HeaderUtil;
import rs.ac.uns.ftn.sbnz.service.dto.AnamnesisDTO;
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
 * REST controller for managing Anamnesis.
 */
@RestController
@RequestMapping("/api")
public class AnamnesisResource {

    private final Logger log = LoggerFactory.getLogger(AnamnesisResource.class);

    private static final String ENTITY_NAME = "anamnesis";

    private final AnamnesisService anamnesisService;

    @Autowired
    private DiagnosisService diagnosisService;

    public AnamnesisResource(AnamnesisService anamnesisService) {
        this.anamnesisService = anamnesisService;
    }

    /**
     * POST  /anamneses : Create a new anamnesis.
     *
     * @param anamnesisDTO the anamnesisDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new anamnesisDTO, or with status 400 (Bad Request) if the anamnesis has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/anamneses")
    @Timed
    public ResponseEntity<AnamnesisDTO> createAnamnesis(@RequestBody AnamnesisDTO anamnesisDTO) throws URISyntaxException {
        log.debug("REST request to save Anamnesis : {}", anamnesisDTO);
        if (anamnesisDTO.getId() != null) {
            throw new BadRequestAlertException("A new anamnesis cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AnamnesisDTO result = anamnesisService.save(anamnesisDTO);
        return ResponseEntity.created(new URI("/api/anamneses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /anamneses : Updates an existing anamnesis.
     *
     * @param anamnesisDTO the anamnesisDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated anamnesisDTO,
     * or with status 400 (Bad Request) if the anamnesisDTO is not valid,
     * or with status 500 (Internal Server Error) if the anamnesisDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/anamneses")
    @Timed
    public ResponseEntity<AnamnesisDTO> updateAnamnesis(@RequestBody AnamnesisDTO anamnesisDTO) throws URISyntaxException {
        log.debug("REST request to update Anamnesis : {}", anamnesisDTO);
        if (anamnesisDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AnamnesisDTO result = anamnesisService.save(anamnesisDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, anamnesisDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /anamneses : get all the anamneses.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of anamneses in body
     */
    @GetMapping("/anamneses")
    @Timed
    public List<AnamnesisDTO> getAllAnamneses(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Anamneses");
        return anamnesisService.findAll();
    }

    /**
     * GET  /anamneses/:id : get the "id" anamnesis.
     *
     * @param id the id of the anamnesisDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the anamnesisDTO, or with status 404 (Not Found)
     */
    @GetMapping("/anamneses/{id}")
    @Timed
    public ResponseEntity<AnamnesisDTO> getAnamnesis(@PathVariable Long id) {
        log.debug("REST request to get Anamnesis : {}", id);
        Optional<AnamnesisDTO> anamnesisDTO = anamnesisService.findOne(id);
        return ResponseUtil.wrapOrNotFound(anamnesisDTO);
    }

    /**
     * DELETE  /anamneses/:id : delete the "id" anamnesis.
     *
     * @param id the id of the anamnesisDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/anamneses/{id}")
    @Timed
    public ResponseEntity<Void> deleteAnamnesis(@PathVariable Long id) {
        log.debug("REST request to delete Anamnesis : {}", id);
        anamnesisService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @DeleteMapping("/anamneses/{id}/cancel-diagnosis")
    @Timed
    public ResponseEntity<Void> cancelDiagnosis(@PathVariable Long id) {
        log.debug("REST request to cancel diagnosis for anamnesis with id : {}", id);

        Anamnesis anamnesis = anamnesisService.findOneEntity(id).get();

        Diagnosis diagnosis = anamnesis.getCurrentDiagnosis();

        if (diagnosis != null) {
            Long diagnosisId = anamnesis.getCurrentDiagnosis().getId();

            anamnesis.setCurrentDiagnosis(null);
            anamnesisService.save(anamnesis);

            diagnosisService.delete(diagnosisId);
        }

        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
