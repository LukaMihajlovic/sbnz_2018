package rs.ac.uns.ftn.sbnz.domain.icu;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

@Role(Role.Type.EVENT)
@Expires("15h")
public class UrinationEvent {

    private int patientId;
    public int amount;

    public UrinationEvent() {}

    public UrinationEvent(int patientId, int amount) {
        this.patientId = patientId;
        this.amount = amount;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
