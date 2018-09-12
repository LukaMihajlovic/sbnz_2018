package rs.ac.uns.ftn.sbnz.service;

import rs.ac.uns.ftn.sbnz.domain.Anamnesis;
import rs.ac.uns.ftn.sbnz.domain.Diagnosis;
import rs.ac.uns.ftn.sbnz.domain.Physician;
import rs.ac.uns.ftn.sbnz.repository.DiagnosisRepository;
import rs.ac.uns.ftn.sbnz.security.SecurityUtils;
import rs.ac.uns.ftn.sbnz.service.dto.DiagnosisDTO;
import rs.ac.uns.ftn.sbnz.service.mapper.DiagnosisMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing Diagnosis.
 */
@Service
@Transactional
public class DiagnosisService {

    private final Logger log = LoggerFactory.getLogger(DiagnosisService.class);

    private final DiagnosisRepository diagnosisRepository;

    private final DiagnosisMapper diagnosisMapper;

    private final PhysicianService physicianService;

    public DiagnosisService(DiagnosisRepository diagnosisRepository, DiagnosisMapper diagnosisMapper, PhysicianService physicianService) {
        this.diagnosisRepository = diagnosisRepository;
        this.diagnosisMapper = diagnosisMapper;
        this.physicianService = physicianService;
    }

    /**
     * Save a diagnosis.
     *
     * @param diagnosisDTO the entity to save
     * @return the persisted entity
     */
    public DiagnosisDTO save(DiagnosisDTO diagnosisDTO) {
        log.debug("Request to save Diagnosis : {}", diagnosisDTO);
        Diagnosis diagnosis = diagnosisMapper.toEntity(diagnosisDTO);
        diagnosis = diagnosisRepository.save(diagnosis);
        return diagnosisMapper.toDto(diagnosis);
    }

    public Diagnosis save(Diagnosis diagnosis) {
        return diagnosisRepository.save(diagnosis);
    }

    /**
     * Get all the diagnoses.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<DiagnosisDTO> findAll() {
        log.debug("Request to get all Diagnoses");
        return diagnosisRepository.findAllWithEagerRelationships().stream()
            .map(diagnosisMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the Diagnosis with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<DiagnosisDTO> findAllWithEagerRelationships(Pageable pageable) {
        return diagnosisRepository.findAllWithEagerRelationships(pageable).map(diagnosisMapper::toDto);
    }

    /**
     * Get all the Diagnosis with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Set<Diagnosis> findAllWithEagerRelationshipsByAnamnesisId(Long id) {
        return diagnosisRepository.findAllWithEagerRelationshipsByAnamnesisId(id);
    }


    /**
     * Get one diagnosis by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<DiagnosisDTO> findOne(Long id) {
        log.debug("Request to get Diagnosis : {}", id);
        return diagnosisRepository.findOneWithEagerRelationships(id)
            .map(diagnosisMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<Diagnosis> findOneEntity(Long id) {
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
        diagnosisRepository.deleteById(id);
    }

    /**
     * Bind current diagnosis to anamnesis
     *
     * @param diagnosisDTO the diagnosisDTO
     * @param anamnesis the anamnesis entity
     */
    public void bindToAnamnesis(DiagnosisDTO diagnosisDTO, Anamnesis anamnesis) {
        diagnosisDTO.setDateOfDiagnosis(LocalDate.now());
        DiagnosisDTO result = save(diagnosisDTO);
        anamnesis.setCurrentDiagnosis(diagnosisMapper.toEntity(result));
    }

    public void bindToPhysician(Diagnosis diagnosis) {
        String userLogin = SecurityUtils.getCurrentUserLogin().get();

        Physician physician = physicianService.findOneByUserLogin(userLogin).get();
        diagnosis.setPhysician(physician);
    }
}
