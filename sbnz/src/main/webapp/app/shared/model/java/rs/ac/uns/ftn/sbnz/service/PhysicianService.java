package rs.ac.uns.ftn.sbnz.service;

import org.hibernate.Query;
import org.kie.api.runtime.ClassObjectFilter;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.springframework.beans.factory.annotation.Autowired;
import rs.ac.uns.ftn.sbnz.domain.Patient;
import rs.ac.uns.ftn.sbnz.domain.Physician;
import rs.ac.uns.ftn.sbnz.domain.icu.TachycardiaEvent;
import rs.ac.uns.ftn.sbnz.repository.PhysicianRepository;
import rs.ac.uns.ftn.sbnz.security.SecurityUtils;
import rs.ac.uns.ftn.sbnz.service.dto.PhysicianDTO;
import rs.ac.uns.ftn.sbnz.service.mapper.PhysicianMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing Physician.
 */
@Service
@Transactional
public class PhysicianService {

    private final Logger log = LoggerFactory.getLogger(PhysicianService.class);

    private final PhysicianRepository physicianRepository;

    private final PhysicianMapper physicianMapper;

    private final UserService userService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private HashMap<String, KieSession> kieSessions;

    public PhysicianService(PhysicianRepository physicianRepository, PhysicianMapper physicianMapper, UserService userService) {
        this.physicianRepository = physicianRepository;
        this.physicianMapper = physicianMapper;
        this.userService = userService;
    }

    /**
     * Save a physician.
     *
     * @param physicianDTO the entity to save
     * @return the persisted entity
     */
    public PhysicianDTO save(PhysicianDTO physicianDTO) {
        log.debug("Request to save Physician : {}", physicianDTO);
        Physician physician = physicianMapper.toEntity(physicianDTO);
        physician = physicianRepository.save(physician);
        return physicianMapper.toDto(physician);
    }

    /**
     * Get all the physicians.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PhysicianDTO> findAll() {
        log.debug("Request to get all Physicians");
        return physicianRepository.findAll().stream()
            .map(physicianMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one physician by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<PhysicianDTO> findOne(Long id) {
        log.debug("Request to get Physician : {}", id);
        return physicianRepository.findById(id)
            .map(physicianMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<Physician> findOneWithoutDTO(Long id) {
        log.debug("Request to get Physician : {}", id);
        return physicianRepository.findById(id);
    }

    /**
     * Get one physician by user id.
     *
     * @param id the id of the user entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<PhysicianDTO> findOneByUserId(Long id) {
        log.debug("Request to get Physician with user ID: {}", id);
        return physicianRepository.findByUserId(id)
            .map(physicianMapper::toDto);
    }


    @Transactional(readOnly = true)
    public Optional<Physician> findOneByUserLogin(String login) {
        Long userId = userService.findOneByLogin(login).get().getId();

        return physicianRepository.findByUserId(userId);
    }

    /**
     * Delete the physician by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Physician : {}", id);
        physicianRepository.deleteById(id);
    }

    public HashSet<Patient> getChronicReport() {
        Optional<String> userLogin = SecurityUtils.getCurrentUserLogin();

        if (!userLogin.isPresent() && !kieSessions.containsKey(userLogin.get()))
            return new HashSet<>();

        KieSession kieSession = kieSessions.get(userLogin.get());

//        Collection<?> newEvents = kieSession.getObjects(new ClassObjectFilter(Patient.class));

        log.info("Inserting PATIENTS into kie session");
        insertPatients();

        HashSet<Patient> patients = new HashSet<>();
        QueryResults results = kieSession.getQueryResults("Assemble chronic report");
        for (QueryResultsRow r: results) {
            Patient patient = (Patient)r.get("$p");
            System.out.println(patient);
            patients.add(patient);
        }

        log.info("Retracting PATIENTS from kie session");
        retractPatients();

        return patients;
    }

    public HashSet<Patient> getAddictReport() {
        Optional<String> userLogin = SecurityUtils.getCurrentUserLogin();

        if (!userLogin.isPresent() && !kieSessions.containsKey(userLogin.get()))
            return new HashSet<>();

        KieSession kieSession = kieSessions.get(userLogin.get());

        log.info("Inserting PATIENTS into kie session");
        insertPatients();

        HashSet<Patient> patients = new HashSet<>();
        QueryResults results = kieSession.getQueryResults("Assemble addiction report");
        for (QueryResultsRow r: results) {
            Patient patient = (Patient)r.get("$p");
            System.out.println(patient);
            patients.add(patient);
        }

        log.info("Retracting PATIENTS from kie session");
        retractPatients();

        return patients;
    }

    public HashSet<Patient> getImmunodeficiencytReport() {
        Optional<String> userLogin = SecurityUtils.getCurrentUserLogin();

        if (!userLogin.isPresent() && !kieSessions.containsKey(userLogin.get()))
            return new HashSet<>();

        KieSession kieSession = kieSessions.get(userLogin.get());

        log.info("Inserting PATIENTS into kie session");
        insertPatients();

        HashSet<Patient> patients = new HashSet<>();
        QueryResults results = kieSession.getQueryResults("Assemble immunodeficiency report");
        for (QueryResultsRow r: results) {
            Patient patient = (Patient)r.get("$p");
            System.out.println(patient);
            patients.add(patient);
        }

        log.info("Retracting PATIENTS from kie session");
        retractPatients();

        return patients;
    }

    private void insertPatients() {
        Optional<String> userLogin = SecurityUtils.getCurrentUserLogin();

        if (!userLogin.isPresent())
            return;

        KieSession kieSession = kieSessions.get(userLogin.get());

        List<Patient> patients = patientService.findAllEntities();
        patients.stream().forEach(p -> kieSession.insert(p));
    }

    private void retractPatients() {
        Optional<String> userLogin = SecurityUtils.getCurrentUserLogin();

        if (!userLogin.isPresent())
            return;

        KieSession kieSession = kieSessions.get(userLogin.get());

        QueryResults queryResults = kieSession.getQueryResults("Get all Patient facts");
        for (QueryResultsRow queryResult: queryResults) {
            Patient p = (Patient)queryResult.get("$p");
            kieSession.delete(kieSession.getFactHandle(p));
        }
    }

    public void disposePhysicianSession(String userLogin) {
        if (kieSessions.containsKey(userLogin)) {
            kieSessions.get(userLogin).dispose();
            kieSessions.remove(userLogin);
        }

        log.info("Disposing KieSession for user: " + userLogin);
    }
}
