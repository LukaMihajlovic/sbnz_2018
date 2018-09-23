package rs.ac.uns.ftn.sbnz.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import rs.ac.uns.ftn.sbnz.domain.Anamnesis;
import rs.ac.uns.ftn.sbnz.service.AnamnesisService;
import rs.ac.uns.ftn.sbnz.service.RunService;
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
 * REST controller for managing Anamnesis.
 */
@RestController
@RequestMapping("/api")
public class AnamnesisResource {

    private final Logger log = LoggerFactory.getLogger(AnamnesisResource.class);

    private static final String ENTITY_NAME = "anamnesis";

    private final AnamnesisService anamnesisService;

    @Autowired
    private RunService runService;

    public AnamnesisResource(AnamnesisService anamnesisService) {
        this.anamnesisService = anamnesisService;
    }

    /**
     * POST  /anamneses : Create a new anamnesis.
     *
     * @param anamnesis the anamnesis to create
     * @return the ResponseEntity with status 201 (Created) and with body the new anamnesis, or with status 400 (Bad Request) if the anamnesis has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/anamneses")
    @Timed
    public ResponseEntity<Anamnesis> createAnamnesis(@RequestBody Anamnesis anamnesis) throws URISyntaxException {
        log.debug("REST request to save Anamnesis : {}", anamnesis);
        if (anamnesis.getId() != null) {
            throw new BadRequestAlertException("A new anamnesis cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Anamnesis result = anamnesisService.save(anamnesis);
        return ResponseEntity.created(new URI("/api/anamneses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /anamneses : Updates an existing anamnesis.
     *
     * @param anamnesis the anamnesis to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated anamnesis,
     * or with status 400 (Bad Request) if the anamnesis is not valid,
     * or with status 500 (Internal Server Error) if the anamnesis couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/anamneses")
    @Timed
    public ResponseEntity<Anamnesis> updateAnamnesis(@RequestBody Anamnesis anamnesis) throws URISyntaxException {
        log.debug("REST request to update Anamnesis : {}", anamnesis);
        if (anamnesis.getId() == null) {
            return createAnamnesis(anamnesis);
        }
        Anamnesis result = anamnesisService.save(anamnesis);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, anamnesis.getId().toString()))
            .body(result);
    }

    /**
     * GET  /anamneses : get all the anamneses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of anamneses in body
     */
    @GetMapping("/anamneses")
    @Timed
    public List<Anamnesis> getAllAnamneses() {
        log.debug("REST request to get all Anamneses");
        return anamnesisService.findAll();
        }

    /**
     * GET  /anamneses/:id : get the "id" anamnesis.
     *
     * @param id the id of the anamnesis to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the anamnesis, or with status 404 (Not Found)
     */
    @GetMapping("/anamneses/{id}")
    @Timed
    public ResponseEntity<Anamnesis> getAnamnesis(@PathVariable Long id) {
        log.debug("REST request to get Anamnesis : {}", id);
        Anamnesis anamnesis = anamnesisService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(anamnesis));
    }

    /**
     * DELETE  /anamneses/:id : delete the "id" anamnesis.
     *
     * @param id the id of the anamnesis to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/anamneses/{id}")
    @Timed
    public ResponseEntity<Void> deleteAnamnesis(@PathVariable Long id) {
        log.debug("REST request to delete Anamnesis : {}", id);
        anamnesisService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    @GetMapping("/anamneses/realtime")
    @Timed
    public void realtime() {
        runService.realtimeSimulation();
    }

}
