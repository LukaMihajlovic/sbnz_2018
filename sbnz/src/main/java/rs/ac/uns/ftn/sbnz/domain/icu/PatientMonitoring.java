package rs.ac.uns.ftn.sbnz.domain.icu;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

import java.util.HashSet;
import java.util.Set;

@Expires("168h")
@Role(Role.Type.EVENT)
public class PatientMonitoring {

    private static int idGenerator = 0;

    public int id;
    private Set<String> diseases;

    public PatientMonitoring() {
        this.id = ++idGenerator;
        this.diseases = new HashSet<>();
    }

    public PatientMonitoring(Set<String> diseases) {
        this.diseases = diseases;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<String> getDiseases() {
        return diseases;
    }

    public void setDiseases(Set<String> diseases) {
        this.diseases = diseases;
    }

    @Override
    public String toString() {
        return "Patient{" +
            "id=" + id +
            ", diseases=" + diseases +
            '}';
    }
}
