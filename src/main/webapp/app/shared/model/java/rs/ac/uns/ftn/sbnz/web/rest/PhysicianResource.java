package rs.ac.uns.ftn.sbnz.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import rs.ac.uns.ftn.sbnz.domain.Patient;
import rs.ac.uns.ftn.sbnz.domain.User;
import rs.ac.uns.ftn.sbnz.security.SecurityUtils;
import rs.ac.uns.ftn.sbnz.service.PhysicianService;
import rs.ac.uns.ftn.sbnz.service.UserService;
import rs.ac.uns.ftn.sbnz.service.mapper.PatientMapper;
import rs.ac.uns.ftn.sbnz.web.rest.errors.BadRequestAlertException;
import rs.ac.uns.ftn.sbnz.web.rest.util.HeaderUtil;
import rs.ac.uns.ftn.sbnz.service.dto.PhysicianDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Physician.
 */
@RestController
@RequestMapping("/api")
public class PhysicianResource {

    private final Logger log = LoggerFactory.getLogger(PhysicianResource.class);

    private static final String ENTITY_NAME = "physician";

    private final PhysicianService physicianService;

    private final UserService userService;

    @Autowired
    private PatientMapper patientMapper;

    public PhysicianResource(PhysicianService physicianService, UserService userService) {
        this.physicianService = physicianService;
        this.userService = userService;
    }

    /**
     * POST  /physicians : Create a new physician.
     *
     * @param physicianDTO the physicianDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new physicianDTO, or with status 400 (Bad Request) if the physician has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/physicians")
    @Timed
    public ResponseEntity<PhysicianDTO> createPhysician(@Valid @RequestBody PhysicianDTO physicianDTO) throws URISyntaxException {
        log.debug("REST request to save Physician : {}", physicianDTO);
        if (physicianDTO.getId() != null) {
            throw new BadRequestAlertException("A new physician cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PhysicianDTO result = physicianService.save(physicianDTO);
        return ResponseEntity.created(new URI("/api/physicians/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /physicians : Updates an existing physician.
     *
     * @param physicianDTO the physicianDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated physicianDTO,
     * or with status 400 (Bad Request) if the physicianDTO is not valid,
     * or with status 500 (Internal Server Error) if the physicianDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/physicians")
    @Timed
    public ResponseEntity<PhysicianDTO> updatePhysician(@Valid @RequestBody PhysicianDTO physicianDTO) throws URISyntaxException {
        log.debug("REST request to update Physician : {}", physicianDTO);
        if (physicianDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PhysicianDTO result = physicianService.save(physicianDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, physicianDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /physicians : get all the physicians.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of physicians in body
     */
    @GetMapping("/physicians")
    @Timed
    public List<PhysicianDTO> getAllPhysicians() {
        log.debug("REST request to get all Physicians");
        return physicianService.findAll();
    }

    /**
     * GET  /physicians/:id : get the "id" physician.
     *
     * @param id the id of the physicianDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the physicianDTO, or with status 404 (Not Found)
     */
    @GetMapping("/physicians/{id}")
    @Timed
    public ResponseEntity<PhysicianDTO> getPhysician(@PathVariable Long id) {
        log.debug("REST request to get Physician : {}", id);
        Optional<PhysicianDTO> physicianDTO = physicianService.findOne(id);
        return ResponseUtil.wrapOrNotFound(physicianDTO);
    }

    /**
     * GET  /physicians/signed-in : get the signed in physician.
     *
     */
    @GetMapping("/physicians/signed-in")
    @Timed
    public ResponseEntity<PhysicianDTO> getPhysicianSignedIn() {
        Optional<String> userLogin = SecurityUtils.getCurrentUserLogin();

        User user = userService.findOneByLogin(userLogin.get()).get();

        return ResponseUtil.wrapOrNotFound(physicianService.findOneByUserId(user.getId()));
    }

    /**
     * GET  /physicians/reports/chronic : get patients with possible chronic diseases.
     *
     * @return the ResponseEntity with status 200 (OK)
     */
    @GetMapping("/physicians/reports/chronic")
    @Timed
    public ResponseEntity<?> getChronicReport() {
        log.debug("REST request to get chronic report");

        return ResponseEntity.ok(physicianService.getChronicReport().stream()
            .map(patientMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new)));
    }

    /**
     * GET  /physicians/reports/addict : get patients with possible addictions.
     *
     * @return the ResponseEntity with status 200 (OK)
     */
    @GetMapping("/physicians/reports/addict")
    @Timed
    public ResponseEntity<?> getAddictReport() {
        log.debug("REST request to get addict report");

        return ResponseEntity.ok(physicianService.getAddictReport().stream()
            .map(patientMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new)));
    }

    /**
     * GET  /physicians/reports/immunodeficiency : get patients with possible immunodeficiency disorders.
     *
     * @return the ResponseEntity with status 200 (OK)
     */
    @GetMapping("/physicians/reports/immunodeficiency")
    @Timed
    public ResponseEntity<?> getImmunodeficiencyReport() {
        log.debug("REST request to get immunodeficiency report");

        return ResponseEntity.ok(physicianService.getImmunodeficiencytReport().stream()
            .map(patientMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new)));
    }

    /**
     * DELETE  /physicians/:id : delete the "id" physician.
     *
     * @param id the id of the physicianDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/physicians/{id}")
    @Timed
    public ResponseEntity<Void> deletePhysician(@PathVariable Long id) {
        log.debug("REST request to delete Physician : {}", id);

        String userLogin = SecurityUtils.getCurrentUserLogin().get();
        physicianService.delete(id);
        userService.deleteUser(userLogin);

        physicianService.disposePhysicianSession(userLogin);

        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
