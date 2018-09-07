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
 * A Anamnesis.
 */
@Entity
@Table(name = "anamnesis")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Anamnesis implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "anamnesis")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Diagnosis> diagnoses = new HashSet<>();

    @OneToOne
    @JoinColumn(unique = true)
    private Diagnosis currentDiagnosis;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "anamnesis_allergies_ingredient",
               joinColumns = @JoinColumn(name="anamneses_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="allergies_ingredients_id", referencedColumnName="id"))
    private Set<Ingredient> allergiesIngredients = new HashSet<>();

    @ManyToMany(mappedBy = "anamneses")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "anamnesis_allergies_drug",
        joinColumns = @JoinColumn(name="anamneses_id", referencedColumnName="id"),
        inverseJoinColumns = @JoinColumn(name="allergies_drugs_id", referencedColumnName="id"))
    private Set<Drug> allergiesDrugs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Diagnosis> getDiagnoses() {
        return diagnoses;
    }

    public Anamnesis diagnoses(Set<Diagnosis> diagnoses) {
        this.diagnoses = diagnoses;
        return this;
    }

    public Anamnesis addDiagnosis(Diagnosis diagnosis) {
        this.diagnoses.add(diagnosis);
        //diagnosis.setAnamnesis(this);
        return this;
    }

    public Anamnesis removeDiagnosis(Diagnosis diagnosis) {
        this.diagnoses.remove(diagnosis);
        //diagnosis.setAnamnesis(null);
        return this;
    }

    public void setDiagnoses(Set<Diagnosis> diagnoses) {
        this.diagnoses = diagnoses;
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

    public Set<Ingredient> getAllergiesIngredients() {
        return allergiesIngredients;
    }

    public Anamnesis allergiesIngredients(Set<Ingredient> ingredients) {
        this.allergiesIngredients = ingredients;
        return this;
    }

    public Anamnesis addAllergiesIngredient(Ingredient ingredient) {
        this.allergiesIngredients.add(ingredient);
        ingredient.getAnamneses().add(this);
        return this;
    }

    public Anamnesis removeAllergiesIngredient(Ingredient ingredient) {
        this.allergiesIngredients.remove(ingredient);
        ingredient.getAnamneses().remove(this);
        return this;
    }

    public void setAllergiesIngredients(Set<Ingredient> ingredients) {
        this.allergiesIngredients = ingredients;
    }

    public Set<Drug> getAllergiesDrugs() {
        return allergiesDrugs;
    }

    public Anamnesis allergiesDrugs(Set<Drug> drugs) {
        this.allergiesDrugs = drugs;
        return this;
    }

    public Anamnesis addAllergiesDrugs(Drug drug) {
        this.allergiesDrugs.add(drug);
        drug.getAnamneses().add(this);
        return this;
    }

    public Anamnesis removeAllergiesDrugs(Drug drug) {
        this.allergiesDrugs.remove(drug);
        drug.getAnamneses().remove(this);
        return this;
    }

    public void setAllergiesDrugs(Set<Drug> drugs) {
        this.allergiesDrugs = drugs;
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
