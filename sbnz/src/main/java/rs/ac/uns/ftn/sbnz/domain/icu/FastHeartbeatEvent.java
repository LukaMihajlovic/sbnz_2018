package rs.ac.uns.ftn.sbnz.domain.icu;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

@Expires("30m")
@Role(Role.Type.EVENT)
public class FastHeartbeatEvent {

    private int patientId;

    public FastHeartbeatEvent(int patientId) {
        this.patientId = patientId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }
}
