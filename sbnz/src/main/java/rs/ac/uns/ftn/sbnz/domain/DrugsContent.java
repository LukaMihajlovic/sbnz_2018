package rs.ac.uns.ftn.sbnz.domain;

import java.util.List;

public class DrugsContent {

    public Long id;
    public List<Drug> drugs;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Drug> getDrugs() {
        return drugs;
    }

    public void setDrugs(List<Drug> drugs) {
        this.drugs = drugs;
    }
}
