package rs.ac.uns.ftn.sbnz.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Symptom entity.
 */
public class SymptomDTO implements Serializable {

    private Long id;

    @NotNull
    public String name;

    private Float lowerBound;

    private Float upperBound;

    private Boolean specific;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getLowerBound() {
        return lowerBound;
    }

    public void setLowerBound(Float lowerBound) {
        this.lowerBound = lowerBound;
    }

    public Float getUpperBound() {
        return upperBound;
    }

    public void setUpperBound(Float upperBound) {
        this.upperBound = upperBound;
    }

    public Boolean isSpecific() {
        return specific;
    }

    public void setSpecific(Boolean specific) {
        this.specific = specific;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SymptomDTO that = (SymptomDTO) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "SymptomDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", lowerBound=" + getLowerBound() +
            ", upperBound=" + getUpperBound() +
            ", specific='" + isSpecific() + "'" +
            "}";
    }
}
