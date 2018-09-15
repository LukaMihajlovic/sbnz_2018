package rs.ac.uns.ftn.sbnz.service;

import rs.ac.uns.ftn.sbnz.domain.Anamnesis;
import rs.ac.uns.ftn.sbnz.repository.AnamnesisRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing Anamnesis.
 */
@Service
@Transactional
public class AnamnesisService {

    private final Logger log = LoggerFactory.getLogger(AnamnesisService.class);

    private final AnamnesisRepository anamnesisRepository;

    public AnamnesisService(AnamnesisRepository anamnesisRepository) {
        this.anamnesisRepository = anamnesisRepository;
    }

    /**
     * Save a anamnesis.
     *
     * @param anamnesis the entity to save
     * @return the persisted entity
     */
    public Anamnesis save(Anamnesis anamnesis) {
        log.debug("Request to save Anamnesis : {}", anamnesis);
        return anamnesisRepository.save(anamnesis);
    }

    /**
     * Get all the anamneses.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Anamnesis> findAll() {
        log.debug("Request to get all Anamneses");
        return anamnesisRepository.findAllWithEagerRelationships();
    }

    /**
     * Get one anamnesis by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Anamnesis findOne(Long id) {
        log.debug("Request to get Anamnesis : {}", id);
        return anamnesisRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the anamnesis by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Anamnesis : {}", id);
        anamnesisRepository.delete(id);
    }
}
