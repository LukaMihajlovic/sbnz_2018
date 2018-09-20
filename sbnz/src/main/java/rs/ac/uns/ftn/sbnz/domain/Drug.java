package rs.ac.uns.ftn.sbnz.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import rs.ac.uns.ftn.sbnz.domain.enumeration.DrugType;

/**
 * A Drug.
 */
@Entity
@Table(name = "drug")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Drug implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private DrugType type;

    @ManyToMany
    @JoinTable(name = "drug_ingredient",
               joinColumns = @JoinColumn(name="drugs_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="ingredients_id", referencedColumnName="id"))
    private Set<Ingredient> ingredients = new HashSet<>();

    @ManyToMany(mappedBy = "allergiesDrugs")
    @JsonIgnore
    private Set<Anamnesis> anamneses = new HashSet<>();


    @ManyToMany(mappedBy = "drugs")
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

    public Drug name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DrugType getType() {
        return type;
    }

    public Drug type(DrugType type) {
        this.type = type;
        return this;
    }

    public void setType(DrugType type) {
        this.type = type;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public Drug ingredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
        return this;
    }

    public Drug addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
        ingredient.getDrugs().add(this);
        return this;
    }

    public Drug removeIngredient(Ingredient ingredient) {
        this.ingredients.remove(ingredient);
        ingredient.getDrugs().remove(this);
        return this;
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Set<Anamnesis> getAnamneses() {
        return anamneses;
    }

    public Drug anamneses(Set<Anamnesis> anamneses) {
        this.anamneses = anamneses;
        return this;
    }

    public Drug addAnamnesis(Anamnesis anamnesis) {
        this.anamneses.add(anamnesis);
        anamnesis.getAllergiesDrugs().add(this);
        return this;
    }

    public Drug removeAnamnesis(Anamnesis anamnesis) {
        this.anamneses.remove(anamnesis);
        anamnesis.getAllergiesDrugs().remove(this);
        return this;
    }

    public void setAnamneses(Set<Anamnesis> anamneses) {
        this.anamneses = anamneses;
    }

    public Set<Diagnosis> getDiagnoses() {
        return diagnoses;
    }

    public Drug diagnoses(Set<Diagnosis> diagnoses) {
        this.diagnoses = diagnoses;
        return this;
    }

    public Drug addDiagnosis(Diagnosis diagnosis) {
        this.diagnoses.add(diagnosis);
        diagnosis.getDrugs().add(this);
        return this;
    }

    public Drug removeDiagnosis(Diagnosis diagnosis) {
        this.diagnoses.remove(diagnosis);
        diagnosis.getDrugs().remove(this);
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
        Drug drug = (Drug) o;
        if (drug.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), drug.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Drug{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
