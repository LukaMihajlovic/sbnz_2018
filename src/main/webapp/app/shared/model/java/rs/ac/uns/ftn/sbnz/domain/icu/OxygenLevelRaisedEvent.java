package rs.ac.uns.ftn.sbnz.domain.icu;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

@Role(Role.Type.EVENT)
@Expires("16m")
public class OxygenLevelRaisedEvent {

    public int patientId;

    public OxygenLevelRaisedEvent(int patientId) {
        this.patientId = patientId;
    }
}
