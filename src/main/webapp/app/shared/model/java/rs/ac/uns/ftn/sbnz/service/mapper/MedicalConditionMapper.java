package rs.ac.uns.ftn.sbnz.service.mapper;

import rs.ac.uns.ftn.sbnz.domain.*;
import rs.ac.uns.ftn.sbnz.service.dto.MedicalConditionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MedicalCondition and its DTO MedicalConditionDTO.
 */
@Mapper(componentModel = "spring", uses = {SymptomMapper.class})
public interface MedicalConditionMapper extends EntityMapper<MedicalConditionDTO, MedicalCondition> {


    @Mapping(target = "diagnoses", ignore = true)
    MedicalCondition toEntity(MedicalConditionDTO medicalConditionDTO);

    default MedicalCondition fromId(Long id) {
        if (id == null) {
            return null;
        }
        MedicalCondition medicalCondition = new MedicalCondition();
        medicalCondition.setId(id);
        return medicalCondition;
    }
}
