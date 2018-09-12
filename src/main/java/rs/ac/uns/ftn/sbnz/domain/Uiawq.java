package rs.ac.uns.ftn.sbnz.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Uiawq.
 */
@Entity
@Table(name = "uiawq")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Uiawq implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "qwe")
    private String qwe;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQwe() {
        return qwe;
    }

    public Uiawq qwe(String qwe) {
        this.qwe = qwe;
        return this;
    }

    public void setQwe(String qwe) {
        this.qwe = qwe;
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
        Uiawq uiawq = (Uiawq) o;
        if (uiawq.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), uiawq.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Uiawq{" +
            "id=" + getId() +
            ", qwe='" + getQwe() + "'" +
            "}";
    }
}
