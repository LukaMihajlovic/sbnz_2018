package rs.ac.uns.ftn.sbnz.web.rest;

import com.codahale.metrics.annotation.Timed;
import rs.ac.uns.ftn.sbnz.domain.Drug;
import rs.ac.uns.ftn.sbnz.service.DrugService;
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
 * REST controller for managing Drug.
 */
@RestController
@RequestMapping("/api")
public class DrugResource {

    private final Logger log = LoggerFactory.getLogger(DrugResource.class);

    private static final String ENTITY_NAME = "drug";

    private final DrugService drugService;

    public DrugResource(DrugService drugService) {
        this.drugService = drugService;
    }

    /**
     * POST  /drugs : Create a new drug.
     *
     * @param drug the drug to create
     * @return the ResponseEntity with status 201 (Created) and with body the new drug, or with status 400 (Bad Request) if the drug has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/drugs")
    @Timed
    public ResponseEntity<Drug> createDrug(@RequestBody Drug drug) throws URISyntaxException {
        log.debug("REST request to save Drug : {}", drug);
        if (drug.getId() != null) {
            throw new BadRequestAlertException("A new drug cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Drug result = drugService.save(drug);
        return ResponseEntity.created(new URI("/api/drugs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /drugs : Updates an existing drug.
     *
     * @param drug the drug to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated drug,
     * or with status 400 (Bad Request) if the drug is not valid,
     * or with status 500 (Internal Server Error) if the drug couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/drugs")
    @Timed
    public ResponseEntity<Drug> updateDrug(@RequestBody Drug drug) throws URISyntaxException {
        log.debug("REST request to update Drug : {}", drug);
        if (drug.getId() == null) {
            return createDrug(drug);
        }
        Drug result = drugService.save(drug);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, drug.getId().toString()))
            .body(result);
    }

    /**
     * GET  /drugs : get all the drugs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of drugs in body
     */
    @GetMapping("/drugs")
    @Timed
    public List<Drug> getAllDrugs() {
        log.debug("REST request to get all Drugs");
        return drugService.findAll();
        }

    /**
     * GET  /drugs/:id : get the "id" drug.
     *
     * @param id the id of the drug to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the drug, or with status 404 (Not Found)
     */
    @GetMapping("/drugs/{id}")
    @Timed
    public ResponseEntity<Drug> getDrug(@PathVariable Long id) {
        log.debug("REST request to get Drug : {}", id);
        Drug drug = drugService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(drug));
    }

    /**
     * DELETE  /drugs/:id : delete the "id" drug.
     *
     * @param id the id of the drug to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/drugs/{id}")
    @Timed
    public ResponseEntity<Void> deleteDrug(@PathVariable Long id) {
        log.debug("REST request to delete Drug : {}", id);
        drugService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
