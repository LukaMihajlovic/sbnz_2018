package rs.ac.uns.ftn.sbnz.service.dto;

import rs.ac.uns.ftn.sbnz.domain.Medication;

import java.util.List;

public class Prescription {

    public Long id;
    public List<Medication> proposedMedication;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Medication> getProposedMedication() {
        return proposedMedication;
    }

    public void setProposedMedication(List<Medication> proposedMedication) {
        this.proposedMedication = proposedMedication;
    }
}
