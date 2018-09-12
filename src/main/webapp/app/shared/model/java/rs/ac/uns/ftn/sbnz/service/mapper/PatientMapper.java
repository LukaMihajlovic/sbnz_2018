package rs.ac.uns.ftn.sbnz.service.mapper;

import rs.ac.uns.ftn.sbnz.domain.*;
import rs.ac.uns.ftn.sbnz.service.dto.PatientDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Patient and its DTO PatientDTO.
 */
@Mapper(componentModel = "spring", uses = {AnamnesisMapper.class})
public interface PatientMapper extends EntityMapper<PatientDTO, Patient> {

    @Mapping(source = "anamnesis.id", target = "anamnesisId")
    PatientDTO toDto(Patient patient);

    @Mapping(source = "anamnesisId", target = "anamnesis")
    Patient toEntity(PatientDTO patientDTO);

    default Patient fromId(Long id) {
        if (id == null) {
            return null;
        }
        Patient patient = new Patient();
        patient.setId(id);
        return patient;
    }
}
