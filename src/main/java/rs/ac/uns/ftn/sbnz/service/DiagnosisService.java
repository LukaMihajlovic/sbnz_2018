package rs.ac.uns.ftn.sbnz.service;

import rs.ac.uns.ftn.sbnz.domain.Diagnosis;
import rs.ac.uns.ftn.sbnz.repository.DiagnosisRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing Diagnosis.
 */
@Service
@Transactional
public class DiagnosisService {

    private final Logger log = LoggerFactory.getLogger(DiagnosisService.class);

    private final DiagnosisRepository diagnosisRepository;

    public DiagnosisService(DiagnosisRepository diagnosisRepository) {
        this.diagnosisRepository = diagnosisRepository;
    }

    /**
     * Save a diagnosis.
     *
     * @param diagnosis the entity to save
     * @return the persisted entity
     */
    public Diagnosis save(Diagnosis diagnosis) {
        log.debug("Request to save Diagnosis : {}", diagnosis);
        return diagnosisRepository.save(diagnosis);
    }

    /**
     * Get all the diagnoses.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Diagnosis> findAll() {
        log.debug("Request to get all Diagnoses");
        return diagnosisRepository.findAllWithEagerRelationships();
    }

    /**
     * Get one diagnosis by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Diagnosis findOne(Long id) {
        log.debug("Request to get Diagnosis : {}", id);
        return diagnosisRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the diagnosis by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Diagnosis : {}", id);
        diagnosisRepository.delete(id);
    }
}
