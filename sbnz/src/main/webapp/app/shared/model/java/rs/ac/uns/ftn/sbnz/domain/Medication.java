package rs.ac.uns.ftn.sbnz.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import rs.ac.uns.ftn.sbnz.domain.enumeration.MedicationType;

/**
 * A Medication.
 */
@Entity
@Table(name = "medication")
public class Medication implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type", nullable = false)
    private MedicationType type;

    @ManyToMany
    @JoinTable(name = "medication_ingredients",
               joinColumns = @JoinColumn(name = "medications_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "ingredients_id", referencedColumnName = "id"))
    private Set<Ingredient> ingredients = new HashSet<>();

    @ManyToMany(mappedBy = "medicationAllergies")
    @JsonIgnore
    private Set<Anamnesis> anamneses = new HashSet<>();

    @ManyToMany(mappedBy = "prescriptions")
    @JsonIgnore
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

    public Medication name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MedicationType getType() {
        return type;
    }

    public Medication type(MedicationType type) {
        this.type = type;
        return this;
    }

    public void setType(MedicationType type) {
        this.type = type;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public Medication ingredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
        return this;
    }

    public Medication addIngredients(Ingredient ingredient) {
        this.ingredients.add(ingredient);
        ingredient.getMedications().add(this);
        return this;
    }

    public Medication removeIngredients(Ingredient ingredient) {
        this.ingredients.remove(ingredient);
        ingredient.getMedications().remove(this);
        return this;
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Set<Anamnesis> getAnamneses() {
        return anamneses;
    }

    public Medication anamneses(Set<Anamnesis> anamneses) {
        this.anamneses = anamneses;
        return this;
    }

    public Medication addAnamnesis(Anamnesis anamnesis) {
        this.anamneses.add(anamnesis);
        anamnesis.getMedicationAllergies().add(this);
        return this;
    }

    public Medication removeAnamnesis(Anamnesis anamnesis) {
        this.anamneses.remove(anamnesis);
        anamnesis.getMedicationAllergies().remove(this);
        return this;
    }

    public void setAnamneses(Set<Anamnesis> anamneses) {
        this.anamneses = anamneses;
    }

    public Set<Diagnosis> getDiagnoses() {
        return diagnoses;
    }

    public Medication diagnoses(Set<Diagnosis> diagnoses) {
        this.diagnoses = diagnoses;
        return this;
    }

    public Medication addDiagnoses(Diagnosis diagnosis) {
        this.diagnoses.add(diagnosis);
        diagnosis.getPrescriptions().add(this);
        return this;
    }

    public Medication removeDiagnoses(Diagnosis diagnosis) {
        this.diagnoses.remove(diagnosis);
        diagnosis.getPrescriptions().remove(this);
        return this;
    }

    public void setDiagnoses(Set<Diagnosis> diagnoses) {
        this.diagnoses = diagnoses;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    /*@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Medication medication = (Medication) o;
        if (medication.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), medication.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Medication that = (Medication) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Medication{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
