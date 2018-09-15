package rs.ac.uns.ftn.sbnz.service;

import org.kie.api.runtime.ClassObjectFilter;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.springframework.beans.factory.annotation.Autowired;
import rs.ac.uns.ftn.sbnz.domain.MedicalCondition;
import rs.ac.uns.ftn.sbnz.domain.Patient;
import rs.ac.uns.ftn.sbnz.domain.Symptom;
import rs.ac.uns.ftn.sbnz.repository.MedicalConditionRepository;
import rs.ac.uns.ftn.sbnz.security.SecurityUtils;
import rs.ac.uns.ftn.sbnz.service.dto.Enquiry;
import rs.ac.uns.ftn.sbnz.service.dto.EnquiryResult;
import rs.ac.uns.ftn.sbnz.service.dto.MedicalConditionDTO;
import rs.ac.uns.ftn.sbnz.service.dto.SymptomDTO;
import rs.ac.uns.ftn.sbnz.service.mapper.MedicalConditionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.sbnz.service.mapper.SymptomMapper;

import java.util.*;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing MedicalCondition.
 */
@Service
@Transactional
public class MedicalConditionService {

    private final Logger log = LoggerFactory.getLogger(MedicalConditionService.class);

    private final MedicalConditionRepository medicalConditionRepository;

    private final MedicalConditionMapper medicalConditionMapper;

    @Autowired
    private HashMap<String, KieSession> kieSessions;

    @Autowired
    private SymptomMapper symptomMapper;

    public MedicalConditionService(MedicalConditionRepository medicalConditionRepository, MedicalConditionMapper medicalConditionMapper) {
        this.medicalConditionRepository = medicalConditionRepository;
        this.medicalConditionMapper = medicalConditionMapper;
    }

    /**
     * Save a medicalCondition.
     *
     * @param medicalConditionDTO the entity to save
     * @return the persisted entity
     */
    public MedicalConditionDTO save(MedicalConditionDTO medicalConditionDTO) {
        log.debug("Request to save MedicalCondition : {}", medicalConditionDTO);
        MedicalCondition medicalCondition = medicalConditionMapper.toEntity(medicalConditionDTO);
        medicalCondition = medicalConditionRepository.save(medicalCondition);
        return medicalConditionMapper.toDto(medicalCondition);
    }

    public MedicalCondition save(MedicalCondition medicalCondition) {
        log.debug("Request to save MedicalCondition : {}", medicalCondition);
        medicalCondition = medicalConditionRepository.save(medicalCondition);
        return medicalCondition;
    }

    /**
     * Get all the medicalConditions.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<MedicalConditionDTO> findAll() {
        log.debug("Request to get all MedicalConditions");
        return medicalConditionRepository.findAllWithEagerRelationships().stream()
            .map(medicalConditionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the MedicalCondition with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<MedicalConditionDTO> findAllWithEagerRelationships(Pageable pageable) {
        return medicalConditionRepository.findAllWithEagerRelationships(pageable).map(medicalConditionMapper::toDto);
    }


    /**
     * Get one medicalCondition by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<MedicalConditionDTO> findOne(Long id) {
        log.debug("Request to get MedicalCondition : {}", id);
        return medicalConditionRepository.findOneWithEagerRelationships(id)
            .map(medicalConditionMapper::toDto);
    }

    /**
     * Get one medicalCondition by name.
     *
     * @param name the name of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<MedicalCondition> findOneByName(String name) {
        log.debug("Request to get MedicalCondition by name: {}", name);
        return medicalConditionRepository.findOneByNameWithEagerRelationships(name);
    }

    /**
     * Delete the medicalCondition by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete MedicalCondition : {}", id);
        medicalConditionRepository.deleteById(id);
    }

    public List<SymptomDTO> findSymptomsForMedicalConditionName(String conditionName) {
        Optional<String> userLogin = SecurityUtils.getCurrentUserLogin();

        if (!userLogin.isPresent() && !kieSessions.containsKey(userLogin.get()))
            return new ArrayList<>();

        KieSession kieSession = kieSessions.get(userLogin.get());

        QueryResults results = kieSession.getQueryResults("Find symptoms for specific medical condition", conditionName);
        List<Symptom> symptoms = new ArrayList<>();
        for (QueryResultsRow r: results) {
            symptoms = (ArrayList<Symptom>)r.get("$sortedSymptoms");
        }

        return symptoms.stream()
            .map(symptomMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    public List<MedicalCondition> findAllEntities() {
        return medicalConditionRepository.findAllWithEagerRelationships();
    }

    public List<EnquiryResult> findMedicalConditionsForSymptoms(Enquiry enquiry) {
        Optional<String> userLogin = SecurityUtils.getCurrentUserLogin();

        if (!userLogin.isPresent() && !kieSessions.containsKey(userLogin.get()))
            return new ArrayList<>();

        KieSession kieSession = kieSessions.get(userLogin.get());

        QueryResults results = kieSession.getQueryResults("Find medical conditions associated to provided symptoms", enquiry);
        List<EnquiryResult> enquiryResults = new ArrayList<>();
        for (QueryResultsRow r: results) {
            enquiryResults.add(new EnquiryResult((MedicalCondition)r.get("$mc"), (Integer)r.get("$count")));
        }

        Collections.sort(enquiryResults);

        return enquiryResults;
    }

    public void retractFromSession(Long id) {
        for (String username: kieSessions.keySet()) {
            KieSession kieSession =  kieSessions.get(username);
            MedicalCondition mc;

            QueryResults results = kieSession.getQueryResults("Get Medical Condition by Id", id);
            for (QueryResultsRow r: results) {
                mc = (MedicalCondition)r.get("$mc");
                kieSession.delete(kieSession.getFactHandle(mc));
                log.info("Medical condition: " + mc + " retracted from all sessions.");
            }
        }

    }
}
