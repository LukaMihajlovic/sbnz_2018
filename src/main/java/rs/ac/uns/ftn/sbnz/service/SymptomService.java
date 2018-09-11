package rs.ac.uns.ftn.sbnz.service;

import rs.ac.uns.ftn.sbnz.domain.Symptom;
import rs.ac.uns.ftn.sbnz.repository.SymptomRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing Symptom.
 */
@Service
@Transactional
public class SymptomService {

    private final Logger log = LoggerFactory.getLogger(SymptomService.class);

    private final SymptomRepository symptomRepository;

    public SymptomService(SymptomRepository symptomRepository) {
        this.symptomRepository = symptomRepository;
    }

    /**
     * Save a symptom.
     *
     * @param symptom the entity to save
     * @return the persisted entity
     */
    public Symptom save(Symptom symptom) {
        log.debug("Request to save Symptom : {}", symptom);
        return symptomRepository.save(symptom);
    }

    /**
     * Get all the symptoms.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Symptom> findAll() {
        log.debug("Request to get all Symptoms");
        return symptomRepository.findAll();
    }

    /**
     * Get one symptom by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Symptom findOne(Long id) {
        log.debug("Request to get Symptom : {}", id);
        return symptomRepository.findOne(id);
    }

    /**
     * Delete the symptom by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Symptom : {}", id);
        symptomRepository.delete(id);
    }
}
