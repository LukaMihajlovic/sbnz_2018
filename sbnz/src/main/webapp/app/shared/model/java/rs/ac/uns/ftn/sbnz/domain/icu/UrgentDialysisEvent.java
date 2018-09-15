package rs.ac.uns.ftn.sbnz.domain.icu;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

@Expires("24h")
@Role(Role.Type.EVENT)
public class UrgentDialysisEvent {

    private int patientId;

    public UrgentDialysisEvent(int patientId) {
        this.patientId = patientId;
    }
}
