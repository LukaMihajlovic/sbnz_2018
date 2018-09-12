package rs.ac.uns.ftn.sbnz.web.websocket.dto;

import java.util.Date;

public class MessageDTO {

    private String event;
    private Date date;
    private String message;
    private int patientId;

    public MessageDTO() {}

    public MessageDTO(String event, Date date, String message, int patientId) {
        this.event = event;
        this.date = date;
        this.message = message;
        this.patientId = patientId;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
