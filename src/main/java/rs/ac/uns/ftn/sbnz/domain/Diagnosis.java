package rs.ac.uns.ftn.sbnz.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Diagnosis.
 */
@Entity
@Table(name = "diagnosis")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Diagnosis implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_date")
    private LocalDate date;

    @Column(name = "jhi_recovery")
    private Boolean recovery;

    @ManyToOne
    private Doctor doctor;

    @ManyToOne
    private Anamnesis anamnesis;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "diagnosis_drugs",
               joinColumns = @JoinColumn(name="diagnoses_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="drugs_id", referencedColumnName="id"))
    private Set<Drug> drugs = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "diagnosis_symptoms",
               joinColumns = @JoinColumn(name="diagnoses_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="symptoms_id", referencedColumnName="id"))
    private Set<Symptom> symptoms = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "diagnosis_disease",
               joinColumns = @JoinColumn(name="diagnoses_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="diseases_id", referencedColumnName="id"))
    private Set<Disease> diseases = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public Diagnosis date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Boolean isRecovery() {
        return recovery;
    }

    public Diagnosis recovery(Boolean recovery) {
        this.recovery = recovery;
        return this;
    }

    public void setRecovery(Boolean recovery) {
        this.recovery = recovery;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public Diagnosis doctor(Doctor doctor) {
        this.doctor = doctor;
        return this;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Anamnesis getAnamnesis() {
        return anamnesis;
    }

    public Diagnosis anamnesis(Anamnesis anamnesis) {
        this.anamnesis = anamnesis;
        return this;
    }

    public void setAnamnesis(Anamnesis anamnesis) {
        this.anamnesis = anamnesis;
    }

    public Set<Drug> getDrugs() {
        return drugs;
    }

    public Diagnosis drugs(Set<Drug> drugs) {
        this.drugs = drugs;
        return this;
    }

    public Diagnosis addDrugs(Drug drug) {
        this.drugs.add(drug);
        drug.getDiagnoses().add(this);
        return this;
    }

    public Diagnosis removeDrugs(Drug drug) {
        this.drugs.remove(drug);
        drug.getDiagnoses().remove(this);
        return this;
    }

    public void setDrugs(Set<Drug> drugs) {
        this.drugs = drugs;
    }

    public Set<Symptom> getSymptoms() {
        return symptoms;
    }

    public Diagnosis symptoms(Set<Symptom> symptoms) {
        this.symptoms = symptoms;
        return this;
    }

    public Diagnosis addSymptoms(Symptom symptom) {
        this.symptoms.add(symptom);
        symptom.getDiagnoses().add(this);
        return this;
    }

    public Diagnosis removeSymptoms(Symptom symptom) {
        this.symptoms.remove(symptom);
        symptom.getDiagnoses().remove(this);
        return this;
    }

    public void setSymptoms(Set<Symptom> symptoms) {
        this.symptoms = symptoms;
    }

    public Set<Disease> getDiseases() {
        return diseases;
    }

    public Diagnosis diseases(Set<Disease> diseases) {
        this.diseases = diseases;
        return this;
    }

    public Diagnosis addDisease(Disease disease) {
        this.diseases.add(disease);
        disease.getDiagnoses().add(this);
        return this;
    }

    public Diagnosis removeDisease(Disease disease) {
        this.diseases.remove(disease);
        disease.getDiagnoses().remove(this);
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
        Diagnosis diagnosis = (Diagnosis) o;
        if (diagnosis.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), diagnosis.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Diagnosis{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", recovery='" + isRecovery() + "'" +
            "}";
    }
}
