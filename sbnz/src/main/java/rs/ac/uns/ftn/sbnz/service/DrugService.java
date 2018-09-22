package rs.ac.uns.ftn.sbnz.service;

import com.codahale.metrics.annotation.Timed;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import rs.ac.uns.ftn.sbnz.domain.*;
import rs.ac.uns.ftn.sbnz.repository.DrugRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.sbnz.security.SecurityUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Drug.
 */
@Service
@Transactional
public class DrugService {

    private final Logger log = LoggerFactory.getLogger(DrugService.class);

    private final DrugRepository drugRepository;

    @Autowired
    private HashMap<String,KieSession> kieSessions;



    public DrugService(DrugRepository drugRepository) {
        this.drugRepository = drugRepository;
    }

    /**
     * Save a drug.
     *
     * @param drug the entity to save
     * @return the persisted entity
     */
    public Drug save(Drug drug) {
        log.debug("Request to save Drug : {}", drug);
        return drugRepository.save(drug);
    }

    /**
     * Get all the drugs.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Drug> findAll() {
        log.debug("Request to get all Drugs");
        return drugRepository.findAllWithEagerRelationships();
    }

    /**
     * Get one drug by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Drug findOne(Long id) {
        log.debug("Request to get Drug : {}", id);
        return drugRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the drug by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Drug : {}", id);
        drugRepository.delete(id);
    }

    public AllergiesToClient validateDrugs(Anamnesis anamnesis, DrugsContent dc) {
        Optional<String> userLogin = SecurityUtils.getCurrentUserLogin();

        if (!userLogin.isPresent() && !kieSessions.containsKey(userLogin.get()))
            return null;

        KieSession kieSession = kieSessions.get(userLogin.get());
        kieSession.insert(anamnesis);
        kieSession.insert(dc);
        QueryResults results = kieSession.getQueryResults("Provera alergije", anamnesis.getId());
        List<Drug> drugs = new ArrayList<>();
        List<Ingredient> ingredients = new ArrayList<>();
        for (QueryResultsRow r : results) {
            drugs = (ArrayList<Drug>) r.get("$drugsAll");
            ingredients = (ArrayList<Ingredient>) r.get("$ingredientsAll");
        }

        kieSession.delete(kieSession.getFactHandle(anamnesis));
        kieSession.delete(kieSession.getFactHandle(dc));

        AllergiesToClient pr = new AllergiesToClient();
        pr.setDugs(drugs);
        pr.setIngredients(ingredients);

        return pr;
    }
}
