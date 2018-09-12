package rs.ac.uns.ftn.sbnz.service.mapper;

import rs.ac.uns.ftn.sbnz.domain.*;
import rs.ac.uns.ftn.sbnz.service.dto.PhysicianDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Physician and its DTO PhysicianDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface PhysicianMapper extends EntityMapper<PhysicianDTO, Physician> {

    @Mapping(source = "user.id", target = "userId")
    PhysicianDTO toDto(Physician physician);

    @Mapping(source = "userId", target = "user")
    @Mapping(target = "diagnoses", ignore = true)
    Physician toEntity(PhysicianDTO physicianDTO);

    default Physician fromId(Long id) {
        if (id == null) {
            return null;
        }
        Physician physician = new Physician();
        physician.setId(id);
        return physician;
    }
}
