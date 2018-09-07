package rs.ac.uns.ftn.sbnz.service;

import rs.ac.uns.ftn.sbnz.domain.Drug;
import rs.ac.uns.ftn.sbnz.repository.DrugRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing Drug.
 */
@Service
@Transactional
public class DrugService {

    private final Logger log = LoggerFactory.getLogger(DrugService.class);

    private final DrugRepository drugRepository;

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
}
