package rs.ac.uns.ftn.sbnz.service;

import rs.ac.uns.ftn.sbnz.domain.Ingredient;
import rs.ac.uns.ftn.sbnz.repository.IngredientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing Ingredient.
 */
@Service
@Transactional
public class IngredientService {

    private final Logger log = LoggerFactory.getLogger(IngredientService.class);

    private final IngredientRepository ingredientRepository;

    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    /**
     * Save a ingredient.
     *
     * @param ingredient the entity to save
     * @return the persisted entity
     */
    public Ingredient save(Ingredient ingredient) {
        log.debug("Request to save Ingredient : {}", ingredient);
        return ingredientRepository.save(ingredient);
    }

    /**
     * Get all the ingredients.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Ingredient> findAll() {
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
    public Ingredient findOne(Long id) {
        log.debug("Request to get Ingredient : {}", id);
        return ingredientRepository.findOne(id);
    }

    /**
     * Delete the ingredient by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Ingredient : {}", id);
        ingredientRepository.delete(id);
    }
}
