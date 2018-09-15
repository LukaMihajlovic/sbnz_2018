package rs.ac.uns.ftn.sbnz.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A MedicalCondition.
 */
@Entity
@Table(name = "medical_condition")
public class MedicalCondition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    public String name;

    @Column(name = "probability")
    private Integer probability;

    @ManyToMany
    @JoinTable(name = "medical_condition_symptoms",
               joinColumns = @JoinColumn(name = "medical_conditions_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "symptoms_id", referencedColumnName = "id"))
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Symptom> symptoms = new HashSet<>();

    @ManyToMany(mappedBy = "diagnosedConditions")
//    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
    private Set<Diagnosis> diagnoses = new HashSet<>();

    public MedicalCondition() {}

    public MedicalCondition(String name) {
        this.name = name;
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

    public MedicalCondition name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getProbability() {
        return probability;
    }

    public MedicalCondition probability(Integer probability) {
        this.probability = probability;
        return this;
    }

    public void setProbability(Integer probability) {
        this.probability = probability;
    }

    public Set<Symptom> getSymptoms() {
        return symptoms;
    }

    public MedicalCondition symptoms(Set<Symptom> symptoms) {
        this.symptoms = symptoms;
        return this;
    }

    public MedicalCondition addSymptoms(Symptom symptom) {
        this.symptoms.add(symptom);
        symptom.getConditions().add(this);
        return this;
    }

    public MedicalCondition removeSymptoms(Symptom symptom) {
        this.symptoms.remove(symptom);
        symptom.getConditions().remove(this);
        return this;
    }

    public void setSymptoms(Set<Symptom> symptoms) {
        this.symptoms = symptoms;
    }

    public Set<Diagnosis> getDiagnoses() {
        return diagnoses;
    }

    public MedicalCondition diagnoses(Set<Diagnosis> diagnoses) {
        this.diagnoses = diagnoses;
        return this;
    }

    public MedicalCondition addDiagnoses(Diagnosis diagnosis) {
        this.diagnoses.add(diagnosis);
        diagnosis.getDiagnosedConditions().add(this);
        return this;
    }

    public MedicalCondition removeDiagnoses(Diagnosis diagnosis) {
        this.diagnoses.remove(diagnosis);
        diagnosis.getDiagnosedConditions().remove(this);
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
        MedicalCondition that = (MedicalCondition) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "MedicalCondition{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", probability=" + getProbability() +
            "}";
    }
}
