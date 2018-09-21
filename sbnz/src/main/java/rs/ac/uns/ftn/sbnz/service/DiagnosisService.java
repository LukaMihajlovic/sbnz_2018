package rs.ac.uns.ftn.sbnz.service;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.springframework.beans.factory.annotation.Autowired;
import rs.ac.uns.ftn.sbnz.domain.Diagnosis;
import rs.ac.uns.ftn.sbnz.domain.Patient;
import rs.ac.uns.ftn.sbnz.repository.DiagnosisRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.sbnz.security.SecurityUtils;

import java.util.*;

/**
 * Service Implementation for managing Diagnosis.
 */
@Service
@Transactional
public class DiagnosisService {

    private final Logger log = LoggerFactory.getLogger(DiagnosisService.class);

    private final DiagnosisRepository diagnosisRepository;

    @Autowired
    private PatientService patientService;

    @Autowired
    private HashMap<String,KieSession> kieSessions;

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

    public Set<Diagnosis> findAllWithEagerRelationshipsByAnamnesisId(Long id) {
        return diagnosisRepository.findAllWithEagerRelationshipsByAnamnesisId(id);
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

    public HashSet<Patient> getAddictReport() {


        Optional<String> userLogin = SecurityUtils.getCurrentUserLogin();

        if (!userLogin.isPresent() && !kieSessions.containsKey(userLogin.get()))
            return new HashSet<>();

        KieSession kieSession = kieSessions.get(userLogin.get());
        List<Patient> allPatients = patientService.findAll();
        for (Patient p : allPatients){
            kieSession.insert(p);
        }


        HashSet<Patient> patients = new HashSet<>();
        QueryResults results = kieSession.getQueryResults("Zavisnici");
        for (QueryResultsRow r: results) {
            Patient patient = (Patient)r.get("$p");
            System.out.println(patient);
            patients.add(patient);
        }

        QueryResults queryResults = kieSession.getQueryResults("Svi pacijenti");
        for (QueryResultsRow queryResult: queryResults) {
            Patient p = (Patient)queryResult.get("$p");
            kieSession.delete(kieSession.getFactHandle(p));
        }



        return patients;
    }

    public HashSet<Patient> getImunReport() {
        Optional<String> userLogin = SecurityUtils.getCurrentUserLogin();

        if (!userLogin.isPresent() && !kieSessions.containsKey(userLogin.get()))
            return new HashSet<>();

        KieSession kieSession = kieSessions.get(userLogin.get());

        List<Patient> allPatients = patientService.findAll();
        for (Patient p : allPatients){
            kieSession.insert(p);
        }

        HashSet<Patient> patients = new HashSet<>();
        QueryResults results = kieSession.getQueryResults("Imunitet");
        for (QueryResultsRow r: results) {
            Patient patient = (Patient)r.get("$p");
            System.out.println(patient);
            patients.add(patient);
        }

        QueryResults queryResults = kieSession.getQueryResults("Svi pacijenti");
        for (QueryResultsRow queryResult: queryResults) {
            Patient p = (Patient)queryResult.get("$p");
            kieSession.delete(kieSession.getFactHandle(p));
        }

        return patients;
    }




    public HashSet<Patient> getChronicReport() {
        Optional<String> userLogin = SecurityUtils.getCurrentUserLogin();

        if (!userLogin.isPresent() && !kieSessions.containsKey(userLogin.get()))
            return new HashSet<>();

        KieSession kieSession = kieSessions.get(userLogin.get());

        List<Patient> allPatients = patientService.findAll();
        for (Patient p : allPatients){
            kieSession.insert(p);
        }

        HashSet<Patient> patients = new HashSet<>();
        QueryResults results = kieSession.getQueryResults("Hronicni izvestaj");
        for (QueryResultsRow r: results) {
            Patient patient = (Patient)r.get("$p");
            System.out.println(patient);
            patients.add(patient);
        }

        QueryResults queryResults = kieSession.getQueryResults("Svi pacijenti");
        for (QueryResultsRow queryResult: queryResults) {
            Patient p = (Patient)queryResult.get("$p");
            kieSession.delete(kieSession.getFactHandle(p));
        }

        return patients;
    }
}
