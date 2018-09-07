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
 * A Ingredient.
 */
@Entity
@Table(name = "ingredient")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Ingredient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "allergiesIngredients")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Anamnesis> anamneses = new HashSet<>();

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

    public Ingredient name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Anamnesis> getAnamneses() {
        return anamneses;
    }

    public Ingredient anamneses(Set<Anamnesis> anamneses) {
        this.anamneses = anamneses;
        return this;
    }

    public Ingredient addAnamnesis(Anamnesis anamnesis) {
        this.anamneses.add(anamnesis);
        //anamnesis.getIngredients().add(this);
        return this;
    }

    public Ingredient removeAnamnesis(Anamnesis anamnesis) {
        this.anamneses.remove(anamnesis);
        //anamnesis.getIngredients().remove(this);
        return this;
    }

    public void setAnamneses(Set<Anamnesis> anamneses) {
        this.anamneses = anamneses;
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
        Ingredient ingredient = (Ingredient) o;
        if (ingredient.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ingredient.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Ingredient{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
