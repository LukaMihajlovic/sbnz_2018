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
 * A Symptom.
 */
@Entity
@Table(name = "symptom")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Symptom implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "low")
    private Float low;

    @Column(name = "high")
    private Float high;

    @Column(name = "spec")
    public Boolean spec;

    @ManyToMany(mappedBy = "symptoms")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Diagnosis> diagnoses = new HashSet<>();

    @ManyToMany(mappedBy = "symptoms")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Disease> diseases = new HashSet<>();

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

    public Float getLow() {
        return low;
    }

    public Symptom low(Float low) {
        this.low = low;
        return this;
    }

    public void setLow(Float low) {
        this.low = low;
    }

    public Float getHigh() {
        return high;
    }

    public Symptom high(Float high) {
        this.high = high;
        return this;
    }

    public void setHigh(Float high) {
        this.high = high;
    }

    public Boolean isSpec() {
        return spec;
    }

    public Symptom spec(Boolean spec) {
        this.spec = spec;
        return this;
    }

    public void setSpec(Boolean spec) {
        this.spec = spec;
    }

    public Set<Diagnosis> getDiagnoses() {
        return diagnoses;
    }

    public Symptom diagnoses(Set<Diagnosis> diagnoses) {
        this.diagnoses = diagnoses;
        return this;
    }

    public Symptom addDiagnosis(Diagnosis diagnosis) {
        this.diagnoses.add(diagnosis);
        diagnosis.getSymptoms().add(this);
        return this;
    }

    public Symptom removeDiagnosis(Diagnosis diagnosis) {
        this.diagnoses.remove(diagnosis);
        diagnosis.getSymptoms().remove(this);
        return this;
    }

    public void setDiagnoses(Set<Diagnosis> diagnoses) {
        this.diagnoses = diagnoses;
    }

    public Set<Disease> getDiseases() {
        return diseases;
    }

    public Symptom diseases(Set<Disease> diseases) {
        this.diseases = diseases;
        return this;
    }

    public Symptom addDisease(Disease disease) {
        this.diseases.add(disease);
        disease.getSymptoms().add(this);
        return this;
    }

    public Symptom removeDisease(Disease disease) {
        this.diseases.remove(disease);
        disease.getSymptoms().remove(this);
        return this;
    }

    public void setDiseases(Set<Disease> diseases) {
        this.diseases = diseases;
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
        Symptom symptom = (Symptom) o;
        if (symptom.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), symptom.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Symptom{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", low=" + getLow() +
            ", high=" + getHigh() +
            ", spec='" + isSpec() + "'" +
            "}";
    }
}
