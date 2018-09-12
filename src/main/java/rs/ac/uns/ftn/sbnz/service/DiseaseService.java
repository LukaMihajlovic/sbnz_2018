package rs.ac.uns.ftn.sbnz.service;

import rs.ac.uns.ftn.sbnz.domain.Disease;
import rs.ac.uns.ftn.sbnz.repository.DiseaseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing Disease.
 */
@Service
@Transactional
public class DiseaseService {

    private final Logger log = LoggerFactory.getLogger(DiseaseService.class);

    private final DiseaseRepository diseaseRepository;

    public DiseaseService(DiseaseRepository diseaseRepository) {
        this.diseaseRepository = diseaseRepository;
    }

    /**
     * Save a disease.
     *
     * @param disease the entity to save
     * @return the persisted entity
     */
    public Disease save(Disease disease) {
        log.debug("Request to save Disease : {}", disease);
        return diseaseRepository.save(disease);
    }

    /**
     * Get all the diseases.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Disease> findAll() {
        log.debug("Request to get all Diseases");
        return diseaseRepository.findAllWithEagerRelationships();
    }

    /**
     * Get one disease by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Disease findOne(Long id) {
        log.debug("Request to get Disease : {}", id);
        return diseaseRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the disease by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Disease : {}", id);
        diseaseRepository.delete(id);
    }
}
