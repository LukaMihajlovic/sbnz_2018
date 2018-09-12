package rs.ac.uns.ftn.sbnz.service.mapper;

import rs.ac.uns.ftn.sbnz.domain.*;
import rs.ac.uns.ftn.sbnz.service.dto.AnamnesisDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Anamnesis and its DTO AnamnesisDTO.
 */
@Mapper(componentModel = "spring", uses = {DiagnosisMapper.class, IngredientMapper.class, MedicationMapper.class})
public interface AnamnesisMapper extends EntityMapper<AnamnesisDTO, Anamnesis> {

    @Mapping(source = "currentDiagnosis.id", target = "currentDiagnosisId")
    AnamnesisDTO toDto(Anamnesis anamnesis);

    @Mapping(source = "currentDiagnosisId", target = "currentDiagnosis")
    @Mapping(target = "histories", ignore = true)
    Anamnesis toEntity(AnamnesisDTO anamnesisDTO);

    default Anamnesis fromId(Long id) {
        if (id == null) {
            return null;
        }
        Anamnesis anamnesis = new Anamnesis();
        anamnesis.setId(id);
        return anamnesis;
    }
}
