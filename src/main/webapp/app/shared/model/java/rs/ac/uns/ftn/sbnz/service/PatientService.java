package rs.ac.uns.ftn.sbnz.service;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.springframework.beans.factory.annotation.Autowired;
import rs.ac.uns.ftn.sbnz.domain.Anamnesis;
import rs.ac.uns.ftn.sbnz.domain.Ingredient;
import rs.ac.uns.ftn.sbnz.domain.Medication;
import rs.ac.uns.ftn.sbnz.domain.Patient;
import rs.ac.uns.ftn.sbnz.repository.PatientRepository;
import rs.ac.uns.ftn.sbnz.security.SecurityUtils;
import rs.ac.uns.ftn.sbnz.service.dto.PatientDTO;
import rs.ac.uns.ftn.sbnz.service.dto.Prescription;
import rs.ac.uns.ftn.sbnz.service.dto.PrescriptionRisk;
import rs.ac.uns.ftn.sbnz.service.mapper.IngredientMapper;
import rs.ac.uns.ftn.sbnz.service.mapper.MedicationMapper;
import rs.ac.uns.ftn.sbnz.service.mapper.PatientMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Patient.
 */
@Service
@Transactional
public class PatientService {

    private final Logger log = LoggerFactory.getLogger(PatientService.class);

    private final PatientRepository patientRepository;

    private final PatientMapper patientMapper;

    private final MedicationMapper medicationMapper;

    private final IngredientMapper ingredientMapper;

    @Autowired
    private HashMap<String, KieSession> kieSessions;

    public PatientService(PatientRepository patientRepository, PatientMapper patientMapper, MedicationMapper medicationMapper, IngredientMapper ingredientMapper) {
        this.patientRepository = patientRepository;
        this.patientMapper = patientMapper;
        this.medicationMapper = medicationMapper;
        this.ingredientMapper = ingredientMapper;
    }

    /**
     * Save a patient.
     *
     * @param patientDTO the entity to save
     * @return the persisted entity
     */
    public PatientDTO save(PatientDTO patientDTO) {
        log.debug("Request to save Patient : {}", patientDTO);
        Patient patient = patientMapper.toEntity(patientDTO);
        patient = patientRepository.save(patient);
        return patientMapper.toDto(patient);
    }

    /**
     * Get all the patients.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PatientDTO> findAll() {
        log.debug("Request to get all Patients");
        return patientRepository.findAll().stream()
            .map(patientMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Transactional(readOnly = true)
    public List<Patient> findAllEntities() {
        log.debug("Request to get all Patients");
        return patientRepository.findAll();
    }


    /**
     * Get one patient by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<PatientDTO> findOne(Long id) {
        log.debug("Request to get Patient : {}", id);
        return patientRepository.findById(id)
            .map(patientMapper::toDto);
    }

    /**
     * Delete the patient by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Patient : {}", id);
        patientRepository.deleteById(id);
    }

    public PrescriptionRisk validatePrescription(Anamnesis anamnesis, Prescription prescription) {
        Optional<String> userLogin = SecurityUtils.getCurrentUserLogin();

        if (!userLogin.isPresent() && !kieSessions.containsKey(userLogin.get()))
            return null;

        KieSession kieSession = kieSessions.get(userLogin.get());

        kieSession.insert(anamnesis);
        kieSession.insert(prescription);

        QueryResults results = kieSession.getQueryResults("Prescription validation", anamnesis.getId());
        List<Medication> medication = new ArrayList<>();
        List<Ingredient> ingredients = new ArrayList<>();
        for (QueryResultsRow r : results) {
            medication = (ArrayList<Medication>) r.get("$criticalMedication");
            ingredients = (ArrayList<Ingredient>) r.get("$criticalIngredients");
        }

        kieSession.delete(kieSession.getFactHandle(anamnesis));
        kieSession.delete(kieSession.getFactHandle(prescription));

        PrescriptionRisk pr = new PrescriptionRisk();
        pr.setCriticalMedication(medication.stream().map(medicationMapper::toDto).collect(Collectors.toCollection(LinkedList::new)));
        pr.setCriticalIngredients(ingredients.stream().map(ingredientMapper::toDto).collect(Collectors.toCollection(LinkedList::new)));

        return pr;
    }
}
