package rs.ac.uns.ftn.sbnz.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.tools.ant.taskdefs.Local;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Anamnesis.
 */
@Entity
@Table(name = "anamnesis")
public class Anamnesis implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(unique = true)
    private Diagnosis currentDiagnosis;

    @OneToMany(mappedBy = "anamnesis")
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Diagnosis> histories = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "anamnesis_ingredient_allergies",
               joinColumns = @JoinColumn(name = "anamneses_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "ingredient_allergies_id", referencedColumnName = "id"))
    private Set<Ingredient> ingredientAllergies = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "anamnesis_medication_allergies",
               joinColumns = @JoinColumn(name = "anamneses_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "medication_allergies_id", referencedColumnName = "id"))
    private Set<Medication> medicationAllergies = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Diagnosis getCurrentDiagnosis() {
        return currentDiagnosis;
    }

    public Anamnesis currentDiagnosis(Diagnosis diagnosis) {
        this.currentDiagnosis = diagnosis;
        return this;
    }

    public void setCurrentDiagnosis(Diagnosis diagnosis) {
        this.currentDiagnosis = diagnosis;
    }

    public Set<Diagnosis> getHistories() {
        return histories;
    }

    public Anamnesis histories(Set<Diagnosis> diagnoses) {
        this.histories = diagnoses;
        return this;
    }

    public Anamnesis addHistory(Diagnosis diagnosis) {
        this.histories.add(diagnosis);
        diagnosis.setAnamnesis(this);
        return this;
    }

    public Anamnesis removeHistory(Diagnosis diagnosis) {
        this.histories.remove(diagnosis);
        diagnosis.setAnamnesis(null);
        return this;
    }

    public void setHistories(Set<Diagnosis> diagnoses) {
        this.histories = diagnoses;
    }

    public Set<Ingredient> getIngredientAllergies() {
        return ingredientAllergies;
    }

    public Anamnesis ingredientAllergies(Set<Ingredient> ingredients) {
        this.ingredientAllergies = ingredients;
        return this;
    }

    public Anamnesis addIngredientAllergies(Ingredient ingredient) {
        this.ingredientAllergies.add(ingredient);
        ingredient.getAnamneses().add(this);
        return this;
    }

    public Anamnesis removeIngredientAllergies(Ingredient ingredient) {
        this.ingredientAllergies.remove(ingredient);
        ingredient.getAnamneses().remove(this);
        return this;
    }

    public void setIngredientAllergies(Set<Ingredient> ingredients) {
        this.ingredientAllergies = ingredients;
    }

    public Set<Medication> getMedicationAllergies() {
        return medicationAllergies;
    }

    public Anamnesis medicationAllergies(Set<Medication> medications) {
        this.medicationAllergies = medications;
        return this;
    }

    public Anamnesis addMedicationAllergies(Medication medication) {
        this.medicationAllergies.add(medication);
        medication.getAnamneses().add(this);
        return this;
    }

    public Anamnesis removeMedicationAllergies(Medication medication) {
        this.medicationAllergies.remove(medication);
        medication.getAnamneses().remove(this);
        return this;
    }

    public void setMedicationAllergies(Set<Medication> medications) {
        this.medicationAllergies = medications;
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
        Anamnesis anamnesis = (Anamnesis) o;
        if (anamnesis.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), anamnesis.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Anamnesis{" +
            "id=" + getId() +
            "}";
    }
}
