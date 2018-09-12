package rs.ac.uns.ftn.sbnz.service.mapper;

import rs.ac.uns.ftn.sbnz.domain.*;
import rs.ac.uns.ftn.sbnz.service.dto.MedicationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Medication and its DTO MedicationDTO.
 */
@Mapper(componentModel = "spring", uses = {IngredientMapper.class})
public interface MedicationMapper extends EntityMapper<MedicationDTO, Medication> {


    @Mapping(target = "anamneses", ignore = true)
    @Mapping(target = "diagnoses", ignore = true)
    Medication toEntity(MedicationDTO medicationDTO);

    default Medication fromId(Long id) {
        if (id == null) {
            return null;
        }
        Medication medication = new Medication();
        medication.setId(id);
        return medication;
    }
}
