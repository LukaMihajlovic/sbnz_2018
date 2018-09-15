package rs.ac.uns.ftn.sbnz.service.dto;

import rs.ac.uns.ftn.sbnz.domain.MedicalCondition;

public class EnquiryResult implements Comparable<EnquiryResult> {

    private MedicalCondition medicalCondition;
    public int count;

    public EnquiryResult() {}

    public EnquiryResult(MedicalCondition medicalCondition, int count) {
        this.medicalCondition = medicalCondition;
        this.count = count;
    }

    public int compareTo(EnquiryResult o) {
        return (count - o.count);
    }

    public MedicalCondition getMedicalCondition() {
        return medicalCondition;
    }

    public void setMedicalCondition(MedicalCondition medicalCondition) {
        this.medicalCondition = medicalCondition;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
