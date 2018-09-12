package rs.ac.uns.ftn.sbnz.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Anamnesis entity.
 */
public class AnamnesisDTO implements Serializable {

    private Long id;

    private Long currentDiagnosisId;

    private Set<IngredientDTO> ingredientAllergies = new HashSet<>();

    private Set<MedicationDTO> medicationAllergies = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCurrentDiagnosisId() {
        return currentDiagnosisId;
    }

    public void setCurrentDiagnosisId(Long diagnosisId) {
        this.currentDiagnosisId = diagnosisId;
    }

    public Set<IngredientDTO> getIngredientAllergies() {
        return ingredientAllergies;
    }

    public void setIngredientAllergies(Set<IngredientDTO> ingredients) {
        this.ingredientAllergies = ingredients;
    }

    public Set<MedicationDTO> getMedicationAllergies() {
        return medicationAllergies;
    }

    public void setMedicationAllergies(Set<MedicationDTO> medications) {
        this.medicationAllergies = medications;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AnamnesisDTO anamnesisDTO = (AnamnesisDTO) o;
        if (anamnesisDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), anamnesisDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AnamnesisDTO{" +
            "id=" + getId() +
            ", currentDiagnosis=" + getCurrentDiagnosisId() +
            "}";
    }
}
