package rs.ac.uns.ftn.sbnz.service;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.springframework.beans.factory.annotation.Autowired;
import rs.ac.uns.ftn.sbnz.domain.Medication;
import rs.ac.uns.ftn.sbnz.domain.Symptom;
import rs.ac.uns.ftn.sbnz.repository.MedicationRepository;
import rs.ac.uns.ftn.sbnz.service.dto.MedicationDTO;
import rs.ac.uns.ftn.sbnz.service.mapper.MedicationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing Medication.
 */
@Service
@Transactional
public class MedicationService {

    private final Logger log = LoggerFactory.getLogger(MedicationService.class);

    private final MedicationRepository medicationRepository;

    private final MedicationMapper medicationMapper;

    @Autowired
    private HashMap<String, KieSession> kieSessions;

    public MedicationService(MedicationRepository medicationRepository, MedicationMapper medicationMapper) {
        this.medicationRepository = medicationRepository;
        this.medicationMapper = medicationMapper;
    }

    /**
     * Save a medication.
     *
     * @param medicationDTO the entity to save
     * @return the persisted entity
     */
    public MedicationDTO save(MedicationDTO medicationDTO) {
        log.debug("Request to save Medication : {}", medicationDTO);
        Medication medication = medicationMapper.toEntity(medicationDTO);
        medication = medicationRepository.save(medication);
        return medicationMapper.toDto(medication);
    }

    /**
     * Get all the medications.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<MedicationDTO> findAll() {
        log.debug("Request to get all Medications");
        return medicationRepository.findAllWithEagerRelationships().stream()
            .map(medicationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Transactional(readOnly = true)
    public List<Medication> findAllEntities() {
        log.debug("Request to get all Medications");
        return medicationRepository.findAllWithEagerRelationships();
    }

    /**
     * Get all the Medication with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<MedicationDTO> findAllWithEagerRelationships(Pageable pageable) {
        return medicationRepository.findAllWithEagerRelationships(pageable).map(medicationMapper::toDto);
    }
    

    /**
     * Get one medication by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<MedicationDTO> findOne(Long id) {
        log.debug("Request to get Medication : {}", id);
        return medicationRepository.findOneWithEagerRelationships(id)
            .map(medicationMapper::toDto);
    }

    /**
     * Delete the medication by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Medication : {}", id);
        medicationRepository.deleteById(id);
    }

    public void retractFromSession(Long id) {
        for (String username: kieSessions.keySet()) {
            KieSession kieSession =  kieSessions.get(username);
            Medication m;

            QueryResults results = kieSession.getQueryResults("Get Medication by Id", id);
            for (QueryResultsRow r: results) {
                m = (Medication)r.get("$m");
                kieSession.delete(kieSession.getFactHandle(m));
                log.info("Medication: " + m + " retracted from all sessions.");
            }
        }
    }
}
