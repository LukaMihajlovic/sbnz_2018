package rs.ac.uns.ftn.sbnz.web.websocket.icu;

import org.springframework.context.ApplicationEvent;

import java.util.Date;

public class ICUEvent extends ApplicationEvent {

    private String event;
    private Date date;
    private String message;
    private int patientId;

    public ICUEvent(Object source, String event, Date date, String message, int patientId) {
        super(source);
        this.event = event;
        this.date = date;
        this.message = message;
        this.patientId = patientId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }
}
