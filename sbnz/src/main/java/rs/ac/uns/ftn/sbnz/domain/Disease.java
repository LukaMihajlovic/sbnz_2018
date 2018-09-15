package rs.ac.uns.ftn.sbnz.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Disease.
 */
@Entity
@Table(name = "disease")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Disease implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "factor")
    private Integer factor;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "disease_symptom",
               joinColumns = @JoinColumn(name="diseases_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="symptoms_id", referencedColumnName="id"))
    private Set<Symptom> symptoms = new HashSet<>();

    @ManyToMany(mappedBy = "diseases")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Diagnosis> diagnoses = new HashSet<>();

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

    public Disease name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFactor() {
        return factor;
    }

    public Disease factor(Integer factor) {
        this.factor = factor;
        return this;
    }

    public void setFactor(Integer factor) {
        this.factor = factor;
    }

    public Set<Symptom> getSymptoms() {
        return symptoms;
    }

    public Disease symptoms(Set<Symptom> symptoms) {
        this.symptoms = symptoms;
        return this;
    }

    public Disease addSymptom(Symptom symptom) {
        this.symptoms.add(symptom);
        symptom.getDiseases().add(this);
        return this;
    }

    public Disease removeSymptom(Symptom symptom) {
        this.symptoms.remove(symptom);
        symptom.getDiseases().remove(this);
        return this;
    }

    public void setSymptoms(Set<Symptom> symptoms) {
        this.symptoms = symptoms;
    }

    public Set<Diagnosis> getDiagnoses() {
        return diagnoses;
    }

    public Disease diagnoses(Set<Diagnosis> diagnoses) {
        this.diagnoses = diagnoses;
        return this;
    }

    public Disease addDiagnosis(Diagnosis diagnosis) {
        this.diagnoses.add(diagnosis);
        diagnosis.getDiseases().add(this);
        return this;
    }

    public Disease removeDiagnosis(Diagnosis diagnosis) {
        this.diagnoses.remove(diagnosis);
        diagnosis.getDiseases().remove(this);
        return this;
    }

    public void setDiagnoses(Set<Diagnosis> diagnoses) {
        this.diagnoses = diagnoses;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Disease disease = (Disease) o;
        if (disease.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), disease.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Disease{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", factor=" + getFactor() +
            "}";
    }
}
