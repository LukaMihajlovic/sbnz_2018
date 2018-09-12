package rs.ac.uns.ftn.sbnz.web.rest;

import com.codahale.metrics.annotation.Timed;
import rs.ac.uns.ftn.sbnz.domain.Uiawq;

import rs.ac.uns.ftn.sbnz.repository.UiawqRepository;
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
 * REST controller for managing Uiawq.
 */
@RestController
@RequestMapping("/api")
public class UiawqResource {

    private final Logger log = LoggerFactory.getLogger(UiawqResource.class);

    private static final String ENTITY_NAME = "uiawq";

    private final UiawqRepository uiawqRepository;

    public UiawqResource(UiawqRepository uiawqRepository) {
        this.uiawqRepository = uiawqRepository;
    }

    /**
     * POST  /uiawqs : Create a new uiawq.
     *
     * @param uiawq the uiawq to create
     * @return the ResponseEntity with status 201 (Created) and with body the new uiawq, or with status 400 (Bad Request) if the uiawq has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/uiawqs")
    @Timed
    public ResponseEntity<Uiawq> createUiawq(@RequestBody Uiawq uiawq) throws URISyntaxException {
        log.debug("REST request to save Uiawq : {}", uiawq);
        if (uiawq.getId() != null) {
            throw new BadRequestAlertException("A new uiawq cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Uiawq result = uiawqRepository.save(uiawq);
        return ResponseEntity.created(new URI("/api/uiawqs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /uiawqs : Updates an existing uiawq.
     *
     * @param uiawq the uiawq to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated uiawq,
     * or with status 400 (Bad Request) if the uiawq is not valid,
     * or with status 500 (Internal Server Error) if the uiawq couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/uiawqs")
    @Timed
    public ResponseEntity<Uiawq> updateUiawq(@RequestBody Uiawq uiawq) throws URISyntaxException {
        log.debug("REST request to update Uiawq : {}", uiawq);
        if (uiawq.getId() == null) {
            return createUiawq(uiawq);
        }
        Uiawq result = uiawqRepository.save(uiawq);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, uiawq.getId().toString()))
            .body(result);
    }

    /**
     * GET  /uiawqs : get all the uiawqs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of uiawqs in body
     */
    @GetMapping("/uiawqs")
    @Timed
    public List<Uiawq> getAllUiawqs() {
        log.debug("REST request to get all Uiawqs");
        return uiawqRepository.findAll();
        }

    /**
     * GET  /uiawqs/:id : get the "id" uiawq.
     *
     * @param id the id of the uiawq to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the uiawq, or with status 404 (Not Found)
     */
    @GetMapping("/uiawqs/{id}")
    @Timed
    public ResponseEntity<Uiawq> getUiawq(@PathVariable Long id) {
        log.debug("REST request to get Uiawq : {}", id);
        Uiawq uiawq = uiawqRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(uiawq));
    }

    /**
     * DELETE  /uiawqs/:id : delete the "id" uiawq.
     *
     * @param id the id of the uiawq to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/uiawqs/{id}")
    @Timed
    public ResponseEntity<Void> deleteUiawq(@PathVariable Long id) {
        log.debug("REST request to delete Uiawq : {}", id);
        uiawqRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
