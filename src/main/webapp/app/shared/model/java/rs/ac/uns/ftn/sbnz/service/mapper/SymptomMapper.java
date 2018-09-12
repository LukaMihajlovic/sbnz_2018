package rs.ac.uns.ftn.sbnz.service.mapper;

import rs.ac.uns.ftn.sbnz.domain.*;
import rs.ac.uns.ftn.sbnz.service.dto.SymptomDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Symptom and its DTO SymptomDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SymptomMapper extends EntityMapper<SymptomDTO, Symptom> {


    @Mapping(target = "conditions", ignore = true)
    @Mapping(target = "diagnoses", ignore = true)
    Symptom toEntity(SymptomDTO symptomDTO);

    default Symptom fromId(Long id) {
        if (id == null) {
            return null;
        }
        Symptom symptom = new Symptom();
        symptom.setId(id);
        return symptom;
    }
}
