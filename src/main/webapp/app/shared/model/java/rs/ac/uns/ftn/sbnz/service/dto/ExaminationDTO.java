package rs.ac.uns.ftn.sbnz.service.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ExaminationDTO implements Serializable {

    private Long anamnesisId;
    private Set<SymptomDTO> manifestedSymptoms;
    private HashMap<String, Integer> determinedConditions;

    public ExaminationDTO() {
        this.manifestedSymptoms = new HashSet<>();
        this.determinedConditions = new HashMap<>();
    }

    public ExaminationDTO(Long anamnesisId, Set<SymptomDTO> manifestedSymptoms, HashMap<String, Integer> determinedConditions) {
        this.anamnesisId = anamnesisId;
        this.manifestedSymptoms = manifestedSymptoms;
        this.determinedConditions = determinedConditions;
    }

    public Long getAnamnesisId() {
        return anamnesisId;
    }

    public void setAnamnesisId(Long anamnesisId) {
        this.anamnesisId = anamnesisId;
    }

    public Set<SymptomDTO> getManifestedSymptoms() {
        return manifestedSymptoms;
    }

    public void setManifestedSymptoms(Set<SymptomDTO> manifestedSymptoms) {
        this.manifestedSymptoms = manifestedSymptoms;
    }

    public HashMap<String, Integer> getDeterminedConditions() {
        return determinedConditions;
    }

    public void setDeterminedConditions(HashMap<String, Integer> determinedConditions) {
        this.determinedConditions = determinedConditions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExaminationDTO that = (ExaminationDTO) o;
        return Objects.equals(anamnesisId, that.anamnesisId) &&
            Objects.equals(manifestedSymptoms, that.manifestedSymptoms) &&
            Objects.equals(determinedConditions, that.determinedConditions);
    }

    @Override
    public int hashCode() {

        return Objects.hash(anamnesisId, manifestedSymptoms, determinedConditions);
    }

    @Override
    public String toString() {
        return "ExaminationDTO{" +
            "anamnesisId=" + anamnesisId +
            ", manifestedSymptoms=" + manifestedSymptoms +
            ", determinedConditions=" + determinedConditions +
            '}';
    }
}
