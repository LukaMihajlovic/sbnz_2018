package rs.ac.uns.ftn.sbnz.service;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.springframework.beans.factory.annotation.Autowired;
import rs.ac.uns.ftn.sbnz.domain.MedicalCondition;
import rs.ac.uns.ftn.sbnz.domain.Symptom;
import rs.ac.uns.ftn.sbnz.repository.SymptomRepository;
import rs.ac.uns.ftn.sbnz.service.dto.SymptomDTO;
import rs.ac.uns.ftn.sbnz.service.mapper.SymptomMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing Symptom.
 */
@Service
@Transactional
public class SymptomService {

    private final Logger log = LoggerFactory.getLogger(SymptomService.class);

    private final SymptomRepository symptomRepository;

    private final SymptomMapper symptomMapper;

    @Autowired
    private HashMap<String, KieSession> kieSessions;

    public SymptomService(SymptomRepository symptomRepository, SymptomMapper symptomMapper) {
        this.symptomRepository = symptomRepository;
        this.symptomMapper = symptomMapper;
    }

    /**
     * Save a symptom.
     *
     * @param symptomDTO the entity to save
     * @return the persisted entity
     */
    public SymptomDTO save(SymptomDTO symptomDTO) {
        log.debug("Request to save Symptom : {}", symptomDTO);
        Symptom symptom = symptomMapper.toEntity(symptomDTO);
        symptom = symptomRepository.save(symptom);
        return symptomMapper.toDto(symptom);
    }

    /**
     * Get all the symptoms.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<SymptomDTO> findAll() {
        log.debug("Request to get all Symptoms");
        return symptomRepository.findAll().stream()
            .map(symptomMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one symptom by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<SymptomDTO> findOne(Long id) {
        log.debug("Request to get Symptom : {}", id);
        return symptomRepository.findById(id)
            .map(symptomMapper::toDto);
    }

    /**
     * Delete the symptom by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Symptom : {}", id);
        symptomRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Symptom> findAllEntities() {
        log.debug("Request to get all Symptoms");
        return symptomRepository.findAll();
    }

    public void retractFromSession(Long id) {
        for (String username: kieSessions.keySet()) {
            KieSession kieSession =  kieSessions.get(username);
            Symptom s;

            QueryResults results = kieSession.getQueryResults("Get Symptom by Id", id);
            for (QueryResultsRow r: results) {
                s = (Symptom)r.get("$s");
                kieSession.delete(kieSession.getFactHandle(s));
                log.info("Symptom: " + s + " retracted from all sessions.");
            }
        }
    }
}
