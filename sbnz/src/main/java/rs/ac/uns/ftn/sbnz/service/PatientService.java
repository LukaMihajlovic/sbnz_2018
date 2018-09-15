package rs.ac.uns.ftn.sbnz.service;

import rs.ac.uns.ftn.sbnz.domain.Patient;
import rs.ac.uns.ftn.sbnz.repository.PatientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing Patient.
 */
@Service
@Transactional
public class PatientService {

    private final Logger log = LoggerFactory.getLogger(PatientService.class);

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    /**
     * Save a patient.
     *
     * @param patient the entity to save
     * @return the persisted entity
     */
    public Patient save(Patient patient) {
        log.debug("Request to save Patient : {}", patient);
        return patientRepository.save(patient);
    }

    /**
     * Get all the patients.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Patient> findAll() {
        log.debug("Request to get all Patients");
        return patientRepository.findAll();
    }


    /**
     *  get all the patients where Anamnesis is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Patient> findAllWhereAnamnesisIsNull() {
        log.debug("Request to get all patients where Anamnesis is null");
        return StreamSupport
            .stream(patientRepository.findAll().spliterator(), false)
            .filter(patient -> patient.getAnamnesis() == null)
            .collect(Collectors.toList());
    }

    /**
     * Get one patient by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Patient findOne(Long id) {
        log.debug("Request to get Patient : {}", id);
        return patientRepository.findOne(id);
    }

    /**
     * Delete the patient by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Patient : {}", id);
        patientRepository.delete(id);
    }
}
