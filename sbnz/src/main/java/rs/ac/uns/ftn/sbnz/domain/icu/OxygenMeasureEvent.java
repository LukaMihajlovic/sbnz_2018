package rs.ac.uns.ftn.sbnz.domain.icu;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

import java.io.Serializable;
import java.util.Date;

@Role(Role.Type.EVENT)
@Expires("16m")
public class OxygenMeasureEvent implements Serializable {

    private static int generator = 0;

    private int id;
    public int patientId;
    private int level;
    public Date time;

    public OxygenMeasureEvent() {}

    public OxygenMeasureEvent(int level, int patientId) {
        this.id = ++generator;
        this.patientId = patientId;
        this.level = level;
    }

    public OxygenMeasureEvent(Date time,int level, int patientId) {
        this.id = ++generator;
        this.patientId = patientId;
        this.level = level;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }


    @Override
    public String toString() {
        return "OxygenMeasureEvent{" +
            "id=" + id +
            ", patientId=" + patientId +
            ", level=" + level +
            '}';
    }
}
