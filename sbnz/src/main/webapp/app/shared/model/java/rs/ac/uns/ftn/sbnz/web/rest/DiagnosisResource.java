package rs.ac.uns.ftn.sbnz.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import rs.ac.uns.ftn.sbnz.domain.Anamnesis;
import rs.ac.uns.ftn.sbnz.domain.Diagnosis;
import rs.ac.uns.ftn.sbnz.domain.Symptom;
import rs.ac.uns.ftn.sbnz.service.AnamnesisService;
import rs.ac.uns.ftn.sbnz.service.DiagnosisService;
import rs.ac.uns.ftn.sbnz.service.PhysicianService;
import rs.ac.uns.ftn.sbnz.service.ReasonerService;
import rs.ac.uns.ftn.sbnz.service.dto.SymptomDTO;
import rs.ac.uns.ftn.sbnz.service.mapper.AnamnesisMapper;
import rs.ac.uns.ftn.sbnz.service.mapper.DiagnosisMapper;
import rs.ac.uns.ftn.sbnz.service.mapper.SymptomMapper;
import rs.ac.uns.ftn.sbnz.web.rest.errors.BadRequestAlertException;
import rs.ac.uns.ftn.sbnz.web.rest.util.HeaderUtil;
import rs.ac.uns.ftn.sbnz.service.dto.DiagnosisDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * REST controller for managing Diagnosis.
 */
@RestController
@RequestMapping("/api")
public class DiagnosisResource {

    private final Logger log = LoggerFactory.getLogger(DiagnosisResource.class);

    private static final String ENTITY_NAME = "diagnosis";

    private final DiagnosisService diagnosisService;

    private final DiagnosisMapper diagnosisMapper;

    private final ReasonerService reasonerService;

    private final AnamnesisService anamnesisService;

    private final AnamnesisMapper anamnesisMapper;

    private final PhysicianService physicianService;

    @Autowired
    private SymptomMapper symptomMapper;


    public DiagnosisResource(DiagnosisService diagnosisService, ReasonerService reasonerService,
                             AnamnesisService anamnesisService, AnamnesisMapper anamnesisMapper, DiagnosisMapper diagnosisMapper, PhysicianService physicianService
    ) {
        this.diagnosisService = diagnosisService;
        this.diagnosisMapper = diagnosisMapper;
        this.reasonerService = reasonerService;
        this.anamnesisService = anamnesisService;
        this.anamnesisMapper = anamnesisMapper;
        this.physicianService= physicianService;
    }

    /**
     * POST  /diagnoses : Create a new diagnosis.
     *
     * @param diagnosisDTO the diagnosisDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new diagnosisDTO, or with status 400 (Bad Request) if the diagnosis has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/diagnoses")
    @Timed
    public ResponseEntity<DiagnosisDTO> createDiagnosis(@RequestBody DiagnosisDTO diagnosisDTO) throws URISyntaxException {
        log.debug("REST request to save Diagnosis : {}", diagnosisDTO);
        if (diagnosisDTO.getId() != null) {
            throw new BadRequestAlertException("A new diagnosis cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DiagnosisDTO result = diagnosisService.save(diagnosisDTO);
        return ResponseEntity.created(new URI("/api/diagnoses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /diagnoses/:anamnesisId : Updates an existing diagnosis.
     *
     * @param diagnosis the diagnosisDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated diagnosisDTO,
     * or with status 400 (Bad Request) if the diagnosisDTO is not valid,
     * or with status 500 (Internal Server Error) if the diagnosisDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/diagnoses/{anamnesisId}")
    @Timed
    public ResponseEntity<DiagnosisDTO> updateDiagnosis(@RequestBody Diagnosis diagnosis, @PathVariable Long anamnesisId) throws URISyntaxException {

        diagnosisService.bindToPhysician(diagnosis);
        DiagnosisDTO diagnosisDTO = diagnosisMapper.toDto(diagnosis);

        anamnesisService.updateHistory(anamnesisId);

        log.debug("REST request to update Diagnosis : {}", diagnosisDTO);
        if (diagnosisDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DiagnosisDTO result = diagnosisService.save(diagnosisDTO);

        reasonerService.clearSession();

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, diagnosisDTO.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /diagnoses/most-compatible : Find most compatible medical condition.
     *
     * @param diagnosisDTO the diagnosisDTO sent from client
     * @return the ResponseEntity with status 200 (OK) and with body the updated examinationDTO,
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/diagnoses/most-compatible")
    @Timed
    public ResponseEntity<Anamnesis> findMostCompatibleMedicalCondition(@Valid @RequestBody DiagnosisDTO diagnosisDTO) throws URISyntaxException {

        System.out.println(diagnosisDTO);

        Diagnosis diagnosis = new Diagnosis();

        for (SymptomDTO s: diagnosisDTO.getManifestedSymptoms()) {
            s.setUpperBound(Float.parseFloat("0"));
        }

        HashSet<Symptom> manifestedSymptoms = diagnosisDTO.getManifestedSymptoms()
            .stream().map(symptomMapper::toEntity).collect(Collectors.toCollection(HashSet::new));
        diagnosis.setManifestedSymptoms(manifestedSymptoms);
        diagnosis.setDateOfDiagnosis(LocalDate.now());
        diagnosis.setInRecovery(diagnosisDTO.isInRecovery());
        diagnosis.setPhysician(physicianService.findOneWithoutDTO(diagnosisDTO.getPhysicianId()).get());

        Anamnesis anamnesis = anamnesisService.findOneEntity(diagnosisDTO.getAnamnesisId()).get();

        diagnosis.setAnamnesis(anamnesis);
        diagnosisService.save(diagnosis);

        anamnesis.setCurrentDiagnosis(diagnosis);
        anamnesisService.save(anamnesis);

//        anamnesis.ifPresent(anamnesis1 -> diagnosisService.bindToAnamnesis(diagnosisDTO, anamnesis1));

        anamnesisService.loadHistory(anamnesis);

        reasonerService.insertAnamnesis(anamnesis);

        anamnesisService.save(anamnesis);

        return ResponseEntity.ok().body(anamnesisService.findOneEntity(diagnosisDTO.getAnamnesisId()).get());
    }

    @PutMapping("/diagnoses/confirm")
    @Timed
    public ResponseEntity confirmDiagnosis(@Valid @RequestBody Diagnosis diagnosis) throws URISyntaxException {

        Diagnosis persistedDiagnosis = diagnosisService.findOneEntity(diagnosis.getId()).get();
        persistedDiagnosis.setPrescriptions(diagnosis.getPrescriptions());

        diagnosisService.save(persistedDiagnosis);

        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, diagnosis.getId().toString())).build();
    }

    /**
     * GET  /diagnoses : get all the diagnoses.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of diagnoses in body
     */
    @GetMapping("/diagnoses")
    @Timed
    public List<DiagnosisDTO> getAllDiagnoses(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Diagnoses");
        return diagnosisService.findAll();
    }

    /**
     * GET  /diagnoses/:id : get the "id" diagnosis.
     *
     * @param id the id of the diagnosisDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the diagnosisDTO, or with status 404 (Not Found)
     */
    @GetMapping("/diagnoses/{id}")
    @Timed
    public ResponseEntity<DiagnosisDTO> getDiagnosis(@PathVariable Long id) {
        log.debug("REST request to get Diagnosis : {}", id);
        Optional<DiagnosisDTO> diagnosisDTO = diagnosisService.findOne(id);
        return ResponseUtil.wrapOrNotFound(diagnosisDTO);
    }

    /**
     * DELETE  /diagnoses/:id : delete the "id" diagnosis.
     *
     * @param id the id of the diagnosisDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/diagnoses/{id}")
    @Timed
    public ResponseEntity<Void> deleteDiagnosis(@PathVariable Long id) {
        log.debug("REST request to delete Diagnosis : {}", id);
        diagnosisService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/diagnoses/realtime")
    @Timed
    public void realtime() {
        reasonerService.realtimeSimulation();
    }
}
