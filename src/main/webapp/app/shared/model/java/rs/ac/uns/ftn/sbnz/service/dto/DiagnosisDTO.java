package rs.ac.uns.ftn.sbnz.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Diagnosis entity.
 */
public class DiagnosisDTO implements Serializable {

    private Long id;

    private LocalDate dateOfDiagnosis;

    private Boolean inRecovery;

    private Long physicianId;

    private Long anamnesisId;

    private Set<MedicationDTO> prescriptions = new HashSet<>();

    private Set<SymptomDTO> manifestedSymptoms = new HashSet<>();

    private Set<MedicalConditionDTO> diagnosedConditions = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateOfDiagnosis() {
        return dateOfDiagnosis;
    }

    public void setDateOfDiagnosis(LocalDate dateOfDiagnosis) {
        this.dateOfDiagnosis = dateOfDiagnosis;
    }

    public Boolean isInRecovery() {
        return inRecovery;
    }

    public void setInRecovery(Boolean inRecovery) {
        this.inRecovery = inRecovery;
    }

    public Long getPhysicianId() {
        return physicianId;
    }

    public void setPhysicianId(Long physicianId) {
        this.physicianId = physicianId;
    }

    public Long getAnamnesisId() {
        return anamnesisId;
    }

    public void setAnamnesisId(Long anamnesisId) {
        this.anamnesisId = anamnesisId;
    }

    public Set<MedicationDTO> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(Set<MedicationDTO> medications) {
        this.prescriptions = medications;
    }

    public Set<SymptomDTO> getManifestedSymptoms() {
        return manifestedSymptoms;
    }

    public void setManifestedSymptoms(Set<SymptomDTO> symptoms) {
        this.manifestedSymptoms = symptoms;
    }

    public Set<MedicalConditionDTO> getDiagnosedConditions() {
        return diagnosedConditions;
    }

    public void setDiagnosedConditions(Set<MedicalConditionDTO> medicalConditions) {
        this.diagnosedConditions = medicalConditions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DiagnosisDTO diagnosisDTO = (DiagnosisDTO) o;
        if (diagnosisDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), diagnosisDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DiagnosisDTO{" +
            "id=" + getId() +
            ", dateOfDiagnosis='" + getDateOfDiagnosis() + "'" +
            ", inRecovery='" + isInRecovery() + "'" +
            ", physician=" + getPhysicianId() +
            ", anamnesis=" + getAnamnesisId() +
            "}";
    }
}
