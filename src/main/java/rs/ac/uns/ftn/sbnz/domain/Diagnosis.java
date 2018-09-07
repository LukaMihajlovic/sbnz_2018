package rs.ac.uns.ftn.sbnz.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
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
