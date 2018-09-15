package rs.ac.uns.ftn.sbnz.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the MedicalCondition entity.
 */
public class MedicalConditionDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private Integer probability;

    private Set<SymptomDTO> symptoms = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getProbability() {
        return probability;
    }

    public void setProbability(Integer probability) {
        this.probability = probability;
    }

    public Set<SymptomDTO> getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(Set<SymptomDTO> symptoms) {
        this.symptoms = symptoms;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MedicalConditionDTO medicalConditionDTO = (MedicalConditionDTO) o;
        if (medicalConditionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), medicalConditionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MedicalConditionDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", probability=" + getProbability() +
            "}";
    }
}
