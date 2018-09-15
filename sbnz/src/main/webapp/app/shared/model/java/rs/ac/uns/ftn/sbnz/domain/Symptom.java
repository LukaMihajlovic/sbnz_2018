package rs.ac.uns.ftn.sbnz.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Symptom.
 */
@Entity
@Table(name = "symptom")
public class Symptom implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "lower_bound")
    private Float lowerBound;

    @Column(name = "upper_bound")
    private Float upperBound;

    @Column(name = "jhi_specific")
    public Boolean specific;

    @ManyToMany(mappedBy = "symptoms")
    @JsonIgnore
    private Set<MedicalCondition> conditions = new HashSet<>();

    @ManyToMany(mappedBy = "manifestedSymptoms")
    @JsonIgnore
    private Set<Diagnosis> diagnoses = new HashSet<>();

    public Symptom() {}

    public Symptom(@NotNull String name) {
        this.name = name;
    }

    public Symptom(@NotNull String name, Float lowerBound, Float upperBound, Boolean specific,
                   Set<MedicalCondition> conditions, Set<Diagnosis> diagnoses) {
        this.name = name;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.specific = specific;
        this.conditions = conditions;
        this.diagnoses = diagnoses;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Symptom name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getLowerBound() {
        return lowerBound;
    }

    public Symptom lowerBound(Float lowerBound) {
        this.lowerBound = lowerBound;
        return this;
    }

    public void setLowerBound(Float lowerBound) {
        this.lowerBound = lowerBound;
    }

    public Float getUpperBound() {
        return upperBound;
    }

    public Symptom upperBound(Float upperBound) {
        this.upperBound = upperBound;
        return this;
    }

    public void setUpperBound(Float upperBound) {
        this.upperBound = upperBound;
    }

    public Boolean isSpecific() {
        return specific;
    }

    public Symptom specific(Boolean specific) {
        this.specific = specific;
        return this;
    }

    public void setSpecific(Boolean specific) {
        this.specific = specific;
    }

    public Set<MedicalCondition> getConditions() {
        return conditions;
    }

    public Symptom conditions(Set<MedicalCondition> medicalConditions) {
        this.conditions = medicalConditions;
        return this;
    }

    public Symptom addConditions(MedicalCondition medicalCondition) {
        this.conditions.add(medicalCondition);
        medicalCondition.getSymptoms().add(this);
        return this;
    }

    public Symptom removeConditions(MedicalCondition medicalCondition) {
        this.conditions.remove(medicalCondition);
        medicalCondition.getSymptoms().remove(this);
        return this;
    }

    public void setConditions(Set<MedicalCondition> medicalConditions) {
        this.conditions = medicalConditions;
    }

    public Set<Diagnosis> getDiagnoses() {
        return diagnoses;
    }

    public Symptom diagnoses(Set<Diagnosis> diagnoses) {
        this.diagnoses = diagnoses;
        return this;
    }

    public Symptom addDiagnoses(Diagnosis diagnosis) {
        this.diagnoses.add(diagnosis);
        diagnosis.getManifestedSymptoms().add(this);
        return this;
    }

    public Symptom removeDiagnoses(Diagnosis diagnosis) {
        this.diagnoses.remove(diagnosis);
        diagnosis.getManifestedSymptoms().remove(this);
        return this;
    }

    public void setDiagnoses(Set<Diagnosis> diagnoses) {
        this.diagnoses = diagnoses;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Symptom symptom = (Symptom) o;
        return Objects.equals(name, symptom.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getName());
    }

    @Override
    public String toString() {
        return "Symptom{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", lowerBound=" + getLowerBound() +
            ", upperBound=" + getUpperBound() +
            ", specific='" + isSpecific() + "'" +
            "}";
    }
}
