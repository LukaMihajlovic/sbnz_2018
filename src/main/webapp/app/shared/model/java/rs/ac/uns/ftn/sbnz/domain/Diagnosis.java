package rs.ac.uns.ftn.sbnz.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

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
public class Diagnosis implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_of_diagnosis")
    private LocalDate dateOfDiagnosis;

    @Column(name = "in_recovery")
    private Boolean inRecovery;

    @ManyToOne
    @JsonIgnoreProperties("diagnoses")
    public Physician physician;

    @ManyToOne
    @JsonIgnoreProperties("histories")
    @JsonIgnore
    private Anamnesis anamnesis;

    @ManyToMany
    @JoinTable(name = "diagnosis_prescription",
               joinColumns = @JoinColumn(name = "diagnoses_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "prescriptions_id", referencedColumnName = "id"))
    private Set<Medication> prescriptions = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "diagnosis_manifested_symptoms",
               joinColumns = @JoinColumn(name = "diagnoses_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "manifested_symptoms_id", referencedColumnName = "id"))
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Symptom> manifestedSymptoms = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "diagnosis_diagnosed_conditions",
               joinColumns = @JoinColumn(name = "diagnoses_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "diagnosed_conditions_id", referencedColumnName = "id"))
    @LazyCollection(LazyCollectionOption.FALSE)
    public Set<MedicalCondition> diagnosedConditions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateOfDiagnosis() {
        return dateOfDiagnosis;
    }

    public Diagnosis dateOfDiagnosis(LocalDate dateOfDiagnosis) {
        this.dateOfDiagnosis = dateOfDiagnosis;
        return this;
    }

    public void setDateOfDiagnosis(LocalDate dateOfDiagnosis) {
        this.dateOfDiagnosis = dateOfDiagnosis;
    }

    public Boolean isInRecovery() {
        return inRecovery;
    }

    public Diagnosis inRecovery(Boolean inRecovery) {
        this.inRecovery = inRecovery;
        return this;
    }

    public void setInRecovery(Boolean inRecovery) {
        this.inRecovery = inRecovery;
    }

    public Physician getPhysician() {
        return physician;
    }

    public Diagnosis physician(Physician physician) {
        this.physician = physician;
        return this;
    }

    public void setPhysician(Physician physician) {
        this.physician = physician;
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

    public Set<Medication> getPrescriptions() {
        return prescriptions;
    }

    public Diagnosis prescriptions(Set<Medication> medications) {
        this.prescriptions = medications;
        return this;
    }

    public Diagnosis addPrescription(Medication medication) {
        this.prescriptions.add(medication);
        medication.getDiagnoses().add(this);
        return this;
    }

    public Diagnosis removePrescription(Medication medication) {
        this.prescriptions.remove(medication);
        medication.getDiagnoses().remove(this);
        return this;
    }

    public void setPrescriptions(Set<Medication> medications) {
        this.prescriptions = medications;
    }

    public Set<Symptom> getManifestedSymptoms() {
        return manifestedSymptoms;
    }

    public Diagnosis manifestedSymptoms(Set<Symptom> symptoms) {
        this.manifestedSymptoms = symptoms;
        return this;
    }

    public Diagnosis addManifestedSymptoms(Symptom symptom) {
        this.manifestedSymptoms.add(symptom);
        symptom.getDiagnoses().add(this);
        return this;
    }

    public Diagnosis removeManifestedSymptoms(Symptom symptom) {
        this.manifestedSymptoms.remove(symptom);
        symptom.getDiagnoses().remove(this);
        return this;
    }

    public void setManifestedSymptoms(Set<Symptom> symptoms) {
        this.manifestedSymptoms = symptoms;
    }

    public Set<MedicalCondition> getDiagnosedConditions() {
        return diagnosedConditions;
    }

    public Diagnosis diagnosedConditions(Set<MedicalCondition> medicalConditions) {
        this.diagnosedConditions = medicalConditions;
        return this;
    }

    public Diagnosis addDiagnosedConditions(MedicalCondition medicalCondition) {
        this.diagnosedConditions.add(medicalCondition);
        medicalCondition.getDiagnoses().add(this);
        return this;
    }

    public Diagnosis removeDiagnosedConditions(MedicalCondition medicalCondition) {
        this.diagnosedConditions.remove(medicalCondition);
        medicalCondition.getDiagnoses().remove(this);
        return this;
    }

    public void setDiagnosedConditions(Set<MedicalCondition> medicalConditions) {
        this.diagnosedConditions = medicalConditions;
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
            ", dateOfDiagnosis='" + getDateOfDiagnosis() + "'" +
            ", inRecovery='" + isInRecovery() + "'" +
            "}";
    }
}
