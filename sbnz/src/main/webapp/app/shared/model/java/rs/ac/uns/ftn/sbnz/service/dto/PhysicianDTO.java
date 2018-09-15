package rs.ac.uns.ftn.sbnz.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import rs.ac.uns.ftn.sbnz.domain.enumeration.Specialty;

/**
 * A DTO for the Physician entity.
 */
public class PhysicianDTO implements Serializable {

    private Long id;

    @NotNull
    private Specialty specialty;

    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Specialty getSpecialty() {
        return specialty;
    }

    public void setSpecialty(Specialty specialty) {
        this.specialty = specialty;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PhysicianDTO physicianDTO = (PhysicianDTO) o;
        if (physicianDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), physicianDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PhysicianDTO{" +
            "id=" + getId() +
            ", specialty='" + getSpecialty() + "'" +
            ", user=" + getUserId() +
            "}";
    }
}
