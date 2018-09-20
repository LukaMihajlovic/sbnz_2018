package rs.ac.uns.ftn.sbnz.service;

import org.kie.api.runtime.ClassObjectFilter;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.springframework.beans.factory.annotation.Autowired;
import rs.ac.uns.ftn.sbnz.domain.Disease;
import rs.ac.uns.ftn.sbnz.domain.SFToClient;
import rs.ac.uns.ftn.sbnz.domain.Symptom;
import rs.ac.uns.ftn.sbnz.domain.SymptomFinder;
import rs.ac.uns.ftn.sbnz.repository.DiseaseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.sbnz.security.SecurityUtils;

import java.util.*;

/**
 * Service Implementation for managing Disease.
 */
@Service
@Transactional
public class DiseaseService {

    private final Logger log = LoggerFactory.getLogger(DiseaseService.class);

    private final DiseaseRepository diseaseRepository;


    @Autowired
    private HashMap<String,KieSession> kieSessions;

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

    public Disease findByName(String name){
        return diseaseRepository.findByName(name);
    }

    public List<SFToClient> findDiseaseGivenSymptoms(SymptomFinder sf) {
        Optional<String> userLogin = SecurityUtils.getCurrentUserLogin();

        if (!userLogin.isPresent() && !kieSessions.containsKey(userLogin.get()))
            return new ArrayList<>();

        KieSession kieSession = kieSessions.get(userLogin.get());

        QueryResults results = kieSession.getQueryResults("Nadji bolesti", sf);
        List<SFToClient> sft = new ArrayList<>();
        for (QueryResultsRow result: results) {
            Disease d = (Disease)result.get("$disease");
            Integer i = (Integer)result.get("$counter");
            sft.add(new SFToClient(d,i));
        }

        return sft;
    }

    public List<Symptom> findSymptomsFromGivenDisease(String diseaseName) {
        Optional<String> userLogin = SecurityUtils.getCurrentUserLogin();

        if (!userLogin.isPresent() && !kieSessions.containsKey(userLogin.get()))
            return new ArrayList<>();

        KieSession kieSession = kieSessions.get(userLogin.get());
        Collection<?> facts = kieSession.getObjects(new ClassObjectFilter(Disease.class));
        System.out.println("IMA DISEASE" + facts.size());

        QueryResults results = kieSession.getQueryResults("Nadji simptome", diseaseName);
        List<Symptom> symptoms = new ArrayList<>();
        for (QueryResultsRow r: results) {
            symptoms = (ArrayList<Symptom>)r.get("$sortedSymptoms");
        }
        System.out.println("IMA IMA "+symptoms.size());

        return symptoms;
    }
}
