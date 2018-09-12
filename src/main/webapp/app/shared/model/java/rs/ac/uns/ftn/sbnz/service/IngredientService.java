package rs.ac.uns.ftn.sbnz.service;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.springframework.beans.factory.annotation.Autowired;
import rs.ac.uns.ftn.sbnz.domain.Ingredient;
import rs.ac.uns.ftn.sbnz.domain.Medication;
import rs.ac.uns.ftn.sbnz.repository.IngredientRepository;
import rs.ac.uns.ftn.sbnz.service.dto.IngredientDTO;
import rs.ac.uns.ftn.sbnz.service.mapper.IngredientMapper;
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
 * Service Implementation for managing Ingredient.
 */
@Service
@Transactional
public class IngredientService {

    private final Logger log = LoggerFactory.getLogger(IngredientService.class);

    private final IngredientRepository ingredientRepository;

    private final IngredientMapper ingredientMapper;

    @Autowired
    private HashMap<String, KieSession> kieSessions;

    public IngredientService(IngredientRepository ingredientRepository, IngredientMapper ingredientMapper) {
        this.ingredientRepository = ingredientRepository;
        this.ingredientMapper = ingredientMapper;
    }

    /**
     * Save a ingredient.
     *
     * @param ingredientDTO the entity to save
     * @return the persisted entity
     */
    public IngredientDTO save(IngredientDTO ingredientDTO) {
        log.debug("Request to save Ingredient : {}", ingredientDTO);
        Ingredient ingredient = ingredientMapper.toEntity(ingredientDTO);
        ingredient = ingredientRepository.save(ingredient);
        return ingredientMapper.toDto(ingredient);
    }

    /**
     * Get all the ingredients.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<IngredientDTO> findAll() {
        log.debug("Request to get all Ingredients");
        return ingredientRepository.findAll().stream()
            .map(ingredientMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Transactional(readOnly = true)
    public List<Ingredient> findAllEntities() {
        log.debug("Request to get all Ingredients");
        return ingredientRepository.findAll();
    }

    /**
     * Get one ingredient by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<IngredientDTO> findOne(Long id) {
        log.debug("Request to get Ingredient : {}", id);
        return ingredientRepository.findById(id)
            .map(ingredientMapper::toDto);
    }

    /**
     * Delete the ingredient by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Ingredient : {}", id);
        ingredientRepository.deleteById(id);
    }

    public void retractFromSession(Long id) {
        for (String username: kieSessions.keySet()) {
            KieSession kieSession =  kieSessions.get(username);
            Ingredient i;

            QueryResults results = kieSession.getQueryResults("Get Ingredient by Id", id);
            for (QueryResultsRow r: results) {
                i = (Ingredient)r.get("$i");
                kieSession.delete(kieSession.getFactHandle(i));
                log.info("Ingredient: " + i + " retracted from all sessions.");
            }
        }
    }
}
