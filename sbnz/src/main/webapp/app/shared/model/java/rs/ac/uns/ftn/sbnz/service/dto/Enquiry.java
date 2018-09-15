package rs.ac.uns.ftn.sbnz.service.dto;

import java.util.List;

public class Enquiry {

    public List<SymptomDTO> symptoms;

    public List<SymptomDTO> getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(List<SymptomDTO> symptoms) {
        this.symptoms = symptoms;
    }
}
