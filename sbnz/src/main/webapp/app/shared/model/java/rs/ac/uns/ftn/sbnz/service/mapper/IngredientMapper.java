package rs.ac.uns.ftn.sbnz.service.mapper;

import rs.ac.uns.ftn.sbnz.domain.*;
import rs.ac.uns.ftn.sbnz.service.dto.IngredientDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Ingredient and its DTO IngredientDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface IngredientMapper extends EntityMapper<IngredientDTO, Ingredient> {


    @Mapping(target = "medications", ignore = true)
    @Mapping(target = "anamneses", ignore = true)
    Ingredient toEntity(IngredientDTO ingredientDTO);

    default Ingredient fromId(Long id) {
        if (id == null) {
            return null;
        }
        Ingredient ingredient = new Ingredient();
        ingredient.setId(id);
        return ingredient;
    }
}
