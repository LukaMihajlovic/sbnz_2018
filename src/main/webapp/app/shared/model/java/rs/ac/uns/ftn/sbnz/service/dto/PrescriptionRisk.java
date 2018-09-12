package rs.ac.uns.ftn.sbnz.service.dto;

import java.util.List;

public class PrescriptionRisk {

    public List<MedicationDTO> criticalMedication;
    public List<IngredientDTO> criticalIngredients;

    public List<MedicationDTO> getCriticalMedication() {
        return criticalMedication;
    }

    public void setCriticalMedication(List<MedicationDTO> criticalMedication) {
        this.criticalMedication = criticalMedication;
    }

    public List<IngredientDTO> getCriticalIngredients() {
        return criticalIngredients;
    }

    public void setCriticalIngredients(List<IngredientDTO> criticalIngredients) {
        this.criticalIngredients = criticalIngredients;
    }
}
