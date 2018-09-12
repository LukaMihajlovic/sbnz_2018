package rs.ac.uns.ftn.sbnz.service;

import org.kie.api.runtime.KieContainer;
import rs.ac.uns.ftn.sbnz.domain.Anamnesis;
import rs.ac.uns.ftn.sbnz.domain.Diagnosis;
import rs.ac.uns.ftn.sbnz.domain.User;
import rs.ac.uns.ftn.sbnz.repository.AnamnesisRepository;
import rs.ac.uns.ftn.sbnz.security.SecurityUtils;
import rs.ac.uns.ftn.sbnz.service.dto.AnamnesisDTO;
import rs.ac.uns.ftn.sbnz.service.dto.ExaminationDTO;
import rs.ac.uns.ftn.sbnz.service.dto.PhysicianDTO;
import rs.ac.uns.ftn.sbnz.service.mapper.AnamnesisMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.sbnz.service.mapper.PhysicianMapper;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing Anamnesis.
 */
@Service
@Transactional
public class AnamnesisService {

    private final Logger log = LoggerFactory.getLogger(AnamnesisService.class);

    private final AnamnesisRepository anamnesisRepository;

    private final AnamnesisMapper anamnesisMapper;

    private final UserService userService;

    private final DiagnosisService diagnosisService;

    public AnamnesisService(AnamnesisRepository anamnesisRepository, AnamnesisMapper anamnesisMapper,
                            UserService userService, PhysicianMapper physicianMapper,
                            DiagnosisService diagnosisService) {
        this.anamnesisRepository = anamnesisRepository;
        this.anamnesisMapper = anamnesisMapper;
        this.userService = userService;
        this.diagnosisService = diagnosisService;
    }

    /**
     * Save a anamnesis.
     *
     * @param anamnesisDTO the entity to save
     * @return the persisted entity
     */
    public AnamnesisDTO save(AnamnesisDTO anamnesisDTO) {
        log.debug("Request to save Anamnesis : {}", anamnesisDTO);
        Anamnesis anamnesis = anamnesisMapper.toEntity(anamnesisDTO);
        anamnesis = anamnesisRepository.save(anamnesis);
        return anamnesisMapper.toDto(anamnesis);
    }

    public Anamnesis save(Anamnesis anamnesis) {
        return anamnesisRepository.save(anamnesis);
    }

    /**
     * Get all the anamneses.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<AnamnesisDTO> findAll() {
        log.debug("Request to get all Anamneses");
        return anamnesisRepository.findAllWithEagerRelationships().stream()
            .map(anamnesisMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the Anamnesis with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<AnamnesisDTO> findAllWithEagerRelationships(Pageable pageable) {
        return anamnesisRepository.findAllWithEagerRelationships(pageable).map(anamnesisMapper::toDto);
    }


    /**
     * Get one anamnesis by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<AnamnesisDTO> findOne(Long id) {
        log.debug("Request to get Anamnesis : {}", id);
        return anamnesisRepository.findOneWithEagerRelationships(id)
            .map(anamnesisMapper::toDto);
    }

    /**
     * Get one anamnesis by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Anamnesis> findOneEntity(Long id) {
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
        anamnesisRepository.deleteById(id);
    }

    /**
     * Create anamnesis and set physician.
     *
     * @return the entity
     */
    public AnamnesisDTO createAnamnesis() {
        return save(anamnesisMapper.toDto(new Anamnesis()));
    }

    public void updateHistory(Long anamnesisId) {
        Anamnesis anamnesis = anamnesisRepository.getOne(anamnesisId);
        anamnesis.getHistories().add(anamnesis.getCurrentDiagnosis());
        save(anamnesisMapper.toDto(anamnesis));
    }

    public void loadHistory(Anamnesis anamnesis) {
        anamnesis.setHistories(diagnosisService.findAllWithEagerRelationshipsByAnamnesisId(anamnesis.getId()));
    }
}
