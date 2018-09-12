package rs.ac.uns.ftn.sbnz.service.mapper;

import rs.ac.uns.ftn.sbnz.domain.*;
import rs.ac.uns.ftn.sbnz.service.dto.DiagnosisDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Diagnosis and its DTO DiagnosisDTO.
 */
@Mapper(componentModel = "spring", uses = {PhysicianMapper.class, AnamnesisMapper.class, MedicationMapper.class, SymptomMapper.class, MedicalConditionMapper.class})
public interface DiagnosisMapper extends EntityMapper<DiagnosisDTO, Diagnosis> {

    @Mapping(source = "physician.id", target = "physicianId")
    @Mapping(source = "anamnesis.id", target = "anamnesisId")
    DiagnosisDTO toDto(Diagnosis diagnosis);

    @Mapping(source = "physicianId", target = "physician")
    @Mapping(source = "anamnesisId", target = "anamnesis")
    Diagnosis toEntity(DiagnosisDTO diagnosisDTO);

    default Diagnosis fromId(Long id) {
        if (id == null) {
            return null;
        }
        Diagnosis diagnosis = new Diagnosis();
        diagnosis.setId(id);
        return diagnosis;
    }
}
