package rs.ac.uns.ftn.sbnz.domain.icu;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

import java.util.HashSet;
import java.util.Set;

@Expires("168h")
@Role(Role.Type.EVENT)
public class ICUPatient {

    private static int idGenerator = 0;

    public int id;
    private Set<String> diagnosedConditions;

    public ICUPatient() {
        this.id = ++idGenerator;
        this.diagnosedConditions = new HashSet<>();
    }

    public ICUPatient(Set<String> diagnosedConditions) {
        this.diagnosedConditions = diagnosedConditions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<String> getDiagnosedConditions() {
        return diagnosedConditions;
    }

    public void setDiagnosedConditions(Set<String> diagnosedConditions) {
        this.diagnosedConditions = diagnosedConditions;
    }

    @Override
    public String toString() {
        return "ICUPatient{" +
            "id=" + id +
            ", diagnosedConditions=" + diagnosedConditions +
            '}';
    }
}
